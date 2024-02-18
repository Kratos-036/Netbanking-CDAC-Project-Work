package com.app.controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.CustomerDetails;
import com.app.service.AccountTransactionsService;
import com.app.service.CustomerService;
import com.app.service.EmailService;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	private AccountTransactionsService accountTransactionsService;
	
	@Autowired
	private EmailService emailService;
	
	public EmployeeController() {
		System.out.println("in ctor of " + getClass());
	}
	
	@GetMapping("/Accounts/ViewCustomerDetails48/{customerId}")
	public Optional<CustomerDetails> getCustomerDetailsByAccountNumber(@PathVariable Long customerId) {
		System.out.println("in get Customer Details by Manager");
		return customerService.getCustomerDetailsByCustomerId(customerId);
	}
	
	//Reject KYC
	@PutMapping("/Accounts/RejectKYC/{customerId}")
    public ResponseEntity<String> rejectKYC(@PathVariable Long customerId) {
		try {
		customerService.changeKYCstatusReject(customerId);
		Optional<CustomerDetails> customer = customerService.getCustomerDetailsByCustomerId(customerId);
		System.out.println(customer);
		
		emailService.sendKYCRejectionMail(customer.get().getEmailId(), 
				customer.get().getAccountHolderFirstName(),
				customer.get().getAccountHolderLastName());
		
		return ResponseEntity.ok("Customer KYC rejected!");
		} catch (EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (RuntimeException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing KYC status...");
		}
	}
	
	
	//Approve KYC
	@PutMapping("/Accounts/ApproveKYC/{customerId}")
    public ResponseEntity<String> approveKYC(@PathVariable Long customerId) {
		try {
		customerService.changeKYCstatusApproved(customerId);
		Optional<CustomerDetails> customer = customerService.getCustomerDetailsByCustomerId(customerId);
		
		
		emailService.sendKYCApprovedMail(customer.get().getEmailId(), 
				customer.get().getAccountHolderFirstName(),
				customer.get().getAccountHolderLastName());
		
		return ResponseEntity.ok("Customer KYC approved");
		} catch (EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (RuntimeException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing KYC status...");
		}
	}
	
//	@PostMapping("/FundTransfer/DepositMoney44")
//	public Optional<CustomerDetails> (@PathVariable Long customerId) {

//	}
	
	//Get all list of pending customer KYC
//	@GetMapping("/Accounts/VerfiyOrApproveKYCPending50")
//	public Optional<CustomerDetails> getCustomerDetailsByAccountNumber(@PathVariable Long customerId) {
//		System.out.println("in get Customer Details by Manager");
//		return customerService.getCustomerDetailsByCustomerId(customerId);
//	}

	//Change the verification status
//	@PutMapping("/Accounts/VerfiyOrApproveKYC51/{customerId}")
//	public Optional<CustomerDetails> getCustomerDetailsByAccountNumber(@PathVariable Long customerId) {
//		System.out.println("in get Customer Details by Manager");
//		return customerService.getCustomerDetailsByCustomerId(customerId);
//	}
	

	//To get OTP on mobile/email and verify it
//	@PostMapping("/FundTransfer/DepositMoney44")
//	public Optional<CustomerDetails> (@PathVariable Long customerId) {

//	}
	
	//Deposit transaction after getting amount and remarks from request
	//to //by
	@PostMapping("/FundTransfer/DepositMoney45/{customerId}/{employeeId}")
	public ResponseEntity<String> depositMoneyByEmployee (@PathVariable Long customerId, Long employeeId,
			@RequestBody Double amountToDepoosit, String remarks) {
		try {
//			customerService.changeKYCstatusApproved(customerId);
			Optional<CustomerDetails> customer = customerService.getCustomerDetailsByCustomerId(customerId);
			
			accountTransactionsService.depositMoney(amountToDepoosit, customerId, employeeId, remarks);
			
			emailService.sendMoneyDepositMail(customer.get().getEmailId(), 
					customer.get().getAccountHolderFirstName(),
					customer.get().getAccountHolderLastName(),
					amountToDepoosit,
					employeeId
					);
			
			return ResponseEntity.ok("Successfully deposited " + amountToDepoosit + " in account of customer id : "+ customerId);
			} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in depositing money...");
			}
	}
}
