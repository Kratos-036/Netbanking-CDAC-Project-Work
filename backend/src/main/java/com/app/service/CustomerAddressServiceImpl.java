package com.app.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CustomerAddressDao;
import com.app.dao.CustomerDao;
import com.app.dto.customer.CustomerAddressDTO;
import com.app.entities.CustomerAddress;
import com.app.entities.CustomerDetails;

@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {
	
	@Autowired
    private ModelMapper mapper;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private CustomerAddressDao customerAddressDao;

	@Override
	public Optional<CustomerAddressDTO> getAddressDetails(Long customerId) {    
	    CustomerAddress address = customerAddressDao.findByCustomerId(customerId);
	    
	    // Check if address is not null before mapping
	    if (address != null) {
	        // Map CustomerAddress entity to CustomerAddressDTO
	        CustomerAddressDTO addressDTO = mapper.map(address, CustomerAddressDTO.class);
	        return Optional.of(addressDTO);
	    } else {
	        throw new EntityNotFoundException("Customer not found with id: " + customerId);
	    }
	}

	@Override
	public void putCustomerAddress(Long customerId, CustomerAddressDTO addressDTO) {
		 CustomerDetails customer = customerDao.findById(customerId)
	                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
		 
		 CustomerAddress customerAddress = new CustomerAddress();
		 customerAddress.setCustomerId(customerId);
		 customerAddress.setCustomerDetails(customer);
		 customerAddress.setAddress(addressDTO.getAddress());
		 customerAddress.setCity(addressDTO.getCity());
		 customerAddress.setState(addressDTO.getState());
		 customerAddress.setNationality(addressDTO.getNationality());
		 customerAddress.setPinCode(addressDTO.getPinCode());
	     
		 // Save the updated address
	     customerAddressDao.save(customerAddress);
	}

}
