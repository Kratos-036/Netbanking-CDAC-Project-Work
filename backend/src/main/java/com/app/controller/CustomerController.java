package com.app.controller;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.app.dto.AccountTransactionsDTO;
import com.app.dto.customer.CreateNewCustomerDTO;
import com.app.dto.customer.CustomerAddressDTO;
import com.app.dto.customer.CustomerDetailsDTO;
import com.app.dto.customer.CustomerEssentialDataDTO;
import com.app.dto.customer.CustomerNomineeDetailsDTO;
import com.app.entities.*;
import com.app.service.AccountTransactionsService;
import com.app.service.CustomerAddressService;
//import com.app.service.BeneficiaryService;
import com.app.service.CustomerSavingsAccountService;
import com.app.service.CustomerService;
import com.app.service.ImageHandlingService;
import com.app.service.OffersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountTransactionsService accountTransactionsService;
	
	@Autowired
	private OffersService offersService;
	
	@Autowired
	private CustomerAddressService customerAddressService;
//	
//	@Autowired 
//	private BeneficiaryService beneficiaryService;
	
	@Autowired 
	private CustomerSavingsAccountService customerSavingsAccountService;
	
	@Autowired
	@Qualifier("image_db")
	private ImageHandlingService imgService;
	
	public CustomerController() {
		System.out.println("in ctor of " + getClass());
	}
	
//  register a new customer
	@PostMapping("/CreateNewAccount")
	public CustomerDetails registerNewCustomer(@RequestBody CreateNewCustomerDTO customerDTO) {
		System.out.println("in register new customer " + customerDTO);
		return customerService.registerNewCustomer(customerDTO);
	}
	
//	Page 4 => /registerSuccess

//	Get all transactions of a customer
	@GetMapping("/Account/getAllTransactions/{customerId}")
	List<AccountTransactionsDTO> getAllTransaction(@PathVariable Long customerId){
		System.out.println("in get all transaction by customer id in customer controller");
		return accountTransactionsService.getAllTransactionDetails(customerId);
	}
	
//	Pagination to get last 3 transactions set - INTERNAL SERVER ERROR 500
	@GetMapping("/Account/getPaginated&SortedAllTransactions/{customerId}")
	public ResponseEntity<?> getAllEmpsPaginated(@PathVariable Long customerId,  @RequestParam(defaultValue = "0", required = false) int pageNumber,
			@RequestParam(defaultValue = "3", required = false) int pageSize) {
		System.out.println("in get paged transactions of employee id " + customerId + " " + pageNumber + " " + pageSize);
		List<AccountTransactionsDTO> list = accountTransactionsService.getAllTransactionDetailsByCustomer(customerId, pageNumber, pageSize);
		if (list.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		// customer transactions found
		return ResponseEntity.ok(list);
	}

	//Get account balance and account number
	@GetMapping("/Account/balanceAndAccountNumber/{customerId}")
	public Object[] getAccountBalance(@PathVariable Long customerId){
		System.out.println("in get account balance and account number of customer id "+customerId);
		return customerSavingsAccountService.getAccountBalanceAndAccountNumberByCustomerId(customerId);
	}

	//Get customer details from table customer_details
	@GetMapping("/Account/{customerId}")
	public Optional<CustomerDetailsDTO> getCustomerDetails(@PathVariable Long customerId){
		System.out.println("in get customer details with customer id "+customerId);
		return customerService.getCustomerDetailsByCustomerId(customerId);
	}

	
	//Deposit transaction after getting amount and remarks from request
	@PostMapping("/FundTransfer/WithdrawMoney/{customerId}")
	public ResponseEntity<String> withdrawMoneyByCustomer (@PathVariable Long customerId,
				@RequestBody String remarks, Double amountToWithdraw) {
		try {
				
			accountTransactionsService.withdrawMoney(customerId, amountToWithdraw, remarks);
				
			return ResponseEntity.ok("Successfully withdrawn " + amountToWithdraw + " from account of customer id : "+ customerId);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in withdrawing money...");
		}
	}

	//KYC
	//upload image from clnt n saving it either on db or in server side folder
	// http://host:port/customer/documents/photo/{customerId} ,
	// method=POST , req param :
	// multipart file(image data)
	@PutMapping(value = "/documents/photo/{customerId}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadCustomerPhoto(@PathVariable Long customerId, @RequestParam MultipartFile imageFile)
			throws IOException, RuntimeException {
		System.out.println("in upload customer photo " + customerId);
		return ResponseEntity.status(HttpStatus.CREATED).body(imgService.uploadCustomerPhoto(customerId, imageFile));
	}

	// serve(download image) of specific customer
	@GetMapping(value =  "/documents/photo/{customerId}", produces = { IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
	public ResponseEntity<?> serveCustomerPhoto(@PathVariable Long customerId) throws IOException {
		System.out.println("in download customer photo " + customerId);
		return ResponseEntity.ok(imgService.downloadCustomerPhoto(customerId));
	}
	
	@PutMapping(value = "/documents/pan/{customerId}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadCustomerPAN(@PathVariable Long customerId, @RequestParam MultipartFile imageFile)
			throws IOException, RuntimeException {
		System.out.println("in upload customer pan " + customerId);
		return ResponseEntity.status(HttpStatus.CREATED).body(imgService.uploadCustomerPAN(customerId, imageFile));
	}

	// serve(download image) of specific customer
	@GetMapping(value =  "/documents/pan/{customerId}", produces = { IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
	public ResponseEntity<?> serveCustomerPAN(@PathVariable Long customerId) throws IOException {
		System.out.println("in download customer pan " + customerId);
		return ResponseEntity.ok(imgService.downloadCustomerPAN(customerId));
	}
	
	@PutMapping(value = "/documents/aadhar/{customerId}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadCustomerAadhaar(@PathVariable Long customerId, @RequestParam MultipartFile imageFile)
			throws IOException, RuntimeException {
		System.out.println("in upload customer aadhar " + customerId);
		return ResponseEntity.status(HttpStatus.CREATED).body(imgService.uploadCustomerAadhar(customerId, imageFile));
	}

	// serve(download image) of specific customer
	@GetMapping(value =  "/documents/aadhar/{customerId}", produces = { IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
	public ResponseEntity<?> serveCustomerAadhar(@PathVariable Long customerId) throws IOException {
		System.out.println("in download customer aadhar " + customerId);
		return ResponseEntity.ok(imgService.downloadCustomerAadhar(customerId));
	}
	
	//Change Password
	@PutMapping("/OtherServices/ChangePassword30/{customerId}")
    public ResponseEntity<String> changePassword(@PathVariable Long customerId,
    											@RequestParam String currentPassword,
    											@RequestParam String newPassword) {
        try {
        	customerService.changePassword(customerId, currentPassword, newPassword);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing password.");
        }
	}
	
	//send money after getting amount and remarks from request
	//by to
	@PostMapping("/FundTransfer/SendMoney/{customerId}/{receiverAccountNumber}")
	public ResponseEntity<String> sendMoneyToBeneficiary(@PathVariable Long customerId, String receiverAccountNumber,
			@RequestBody String remarks, Double amountToSend) {
		try {
					
			accountTransactionsService.sendMoney(customerId, receiverAccountNumber, amountToSend, remarks);
					
			return ResponseEntity.ok("Successfully sent " + amountToSend + " from account of customer id : "+ customerId +
					" to another person with account number :" + receiverAccountNumber);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in sending money...");
		}
	}
	
	//Get All available offers
	@GetMapping("/OtherServices/OffersAvailableForMe/{customerId}")
	List<Offers> getAllOffersAvailableForMe(@PathVariable Long customerId){
		System.out.println("in get all offers for customer " + customerId);
		return offersService.getAllOffersAvailableForMe(customerId);
	}
	
	//KYC - Add address
	@PutMapping("/KYC/address/{customerId}")
    public ResponseEntity<String> addAddress(@PathVariable Long customerId,
    											@RequestBody CustomerAddressDTO addressDTO ) {
        try {
        	customerAddressService.putCustomerAddress(customerId, addressDTO);
            return ResponseEntity.ok("Address updated successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in updating address...");
        }
	}
	
	//KYC - Add nominee details
	@PutMapping("/KYC/NomineeDetails/{customerId}")
    public ResponseEntity<String> addNomineeDetails(@PathVariable Long customerId,
    											@RequestBody CustomerNomineeDetailsDTO customerNomineeDetails ) {
        try {
        	customerSavingsAccountService.addNomineeDetails(customerId, customerNomineeDetails);
            return ResponseEntity.ok("Account Nominee Details updated successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in updating Account Related Details...");
        }
	}
	
	//KYC - Add Customer Essentials Details
	@PutMapping("/KYC/CustomerEssentialData/{customerId}")
    public ResponseEntity<String> addCustomerEssentialData(@PathVariable Long customerId,
    											@RequestBody CustomerEssentialDataDTO customerEssentialData ) {
        try {
        	customerService.addCustomerEssentialData(customerId, customerEssentialData);
            return ResponseEntity.ok("Customer Essentials Details updated successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in updating Customer Essentials Details...");
        }
	}
	
//	//Get account balance
//	@GetMapping("/FundTransfer/SendMoney21/{customerId}")
//	public ResponseEntity<String> sendOtpToCustomer(@PathVariable Long customerId) {
//        Optional<CustomerDetails> customerDetails = customerService.getCustomerDetailsByCustomerId(customerId);
//        if(!customerDetails.isEmpty()){
//        	String email = customerDetails.get().getEmailId();
//            System.out.println("Sending OTP as email to "+email);
//            String otpSentToCustomer = emailService.sendOtp(email); 		//OTP generated : otpSentToCustomer 
//            System.out.println("OTP sent to customer : "+ otpSentToCustomer);
//            return ResponseEntity.ok("OTP sent successfully to " + email);
//        }
//        return ResponseEntity.ok("Customer not found!");
//	}
//	
	//To get OTP on mobile/email and verify it
//	@PostMapping("/FundTransfer/SendMoney22")

	

//	//Add Beneficiary
//	@PostMapping("/FundTransfer/AddBenificiary24/{customerId}")
//	public ResponseEntity<String> addEmpDetails(@RequestBody AddBeneficiaryDTO beneficiaryDTO, Long customerId) {
//		System.out.println("in add beneficiary customer controller : " + beneficiaryDTO +" for customer id :"+ customerId);
//		try {
//			Beneficiary beneficiary =  beneficiaryService.addBeneficiaryDetails(beneficiaryDTO,customerId);
//			Optional<CustomerDetails> customer = customerService.getCustomerDetailsByCustomerId(customerId);
//			
//			
//			emailService.sendMoneyMail(customer.get().getEmailId(), 
//					customer.get().getAccountHolderFirstName(),
//					customer.get().getAccountHolderLastName(),
//					beneficiary.getBeneficiaryFirstName(),
//					beneficiary.getBeneficiaryLastName(),
//					beneficiary.getBeneficiaryAccountNumber()
//			);
//			return ResponseEntity.ok("Successfully added " + beneficiary + " to account of customer id : "+ customerId);
//		}
//		catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in adding beneficiary...");
//		}
//	}
//
//	//Get All Beneficiaries
//	@GetMapping("/FundTransfer/ViewAllBeneficiaries/{customerId}")
//	List<Beneficiary> getAllBenificiaries(@PathVariable Long customerId){
//		System.out.println("in get all beinifiaries by customer id in customer controller");
//		return beneficiaryService.getAllBenificiariesDetails(customerId);
//	}
//	
////	Get Beneficiary
////	@GetMapping("/FundTransfer/ViewBeneficiaryDetails/{benId}")
//
//
//	//Delete Beneficiary
//	@DeleteMapping("/FundTransfer/DeleteBeneficiary/{benId}")
//	public String deleteBenDetails(@PathVariable Long benId)
//	{
//		System.out.println("in del beneficiary "+benId);
//		return beneficiaryService.deleteBenificiary(benId);
//	}
//
////Page 29 =>  Customer/OtherServices/MessageAndEmailAlerts29 -> NEED TO IMPLEMENT AND REFACTOR DB
//	

	//Get Contact Details 
//	@GetMapping("/OtherServices/ContactUs37")  -> NEED TO IMPLEMENT AND REFACTOR DB
	
	//Get Tutorials Video links 
//	@GetMapping("/OtherServices/ContactUs37")  -> NEED TO IMPLEMENT AND REFACTOR DB

	
//			Page 39 =>  Customer/Logout39
//			Page 40 =>  Customer/LogoutSuccess40

}
