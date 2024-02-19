//package com.app.service;
//import java.io.IOException;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.app.custom_exceptions.ResourceNotFoundException;
//import com.app.dao.CustomerDao;
//import com.app.dto.ApiResponse;
//import com.app.entities.CustomerDetails;
//
//@Service("image_db")
//@Transactional
//public class ImageHandlingServiceImplDB implements ImageHandlingService {
//    @Autowired
//    private CustomerDao customerDao;
//
//    @Override
//    public ApiResponse uploadCustomerPhoto(Long customerId, MultipartFile image) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//        CustomerDetails customer = customerDao.findByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Set the image data to the customer entity
//        customer.setCustomerPhoto(image.getBytes());
//
//        // Save the updated customer entity
//        customerDao.save(customer);
//
//        return new ApiResponse("Photo uploaded successfully for customer ID: " + customerId);
//    }
//
//    @Override
//    public byte[] downloadCustomerPhoto(Long customerId) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//    	CustomerDetails customer = customerDao.findByCustomerId(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Return the image data from the customer entity
//        return customer.getCustomerPhoto();
//    }
//    
//    
//    @Override
//    public ApiResponse uploadCustomerPAN(Long customerId, MultipartFile image) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//        CustomerDetails customer = customerDao.findByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Set the image data to the customer entity
//        customer.setPanCardPhoto(image.getBytes());
//
//        // Save the updated customer entity
//        customerDao.save(customer);
//
//        return new ApiResponse("Pan card uploaded successfully for customer ID: " + customerId);
//    }
//
//    @Override
//    public byte[] downloadCustomerPAN(Long customerId) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//    	CustomerDetails customer = customerDao.findByCustomerId(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Return the image data from the customer entity
//        return customer.getPanCardPhoto();
//    }
//    
//    
//    @Override
//    public ApiResponse uploadCustomerAadhar(Long customerId, MultipartFile image) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//        CustomerDetails customer = customerDao.findByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Set the image data to the customer entity
//        customer.setAadharCardPhoto(image.getBytes());
//
//        // Save the updated customer entity
//        customerDao.save(customer);
//
//        return new ApiResponse("Aadhar card uploaded successfully for customer ID: " + customerId);
//    }
//
//    @Override
//    public byte[] downloadCustomerAadhar(Long customerId) throws IOException {
//        // Retrieve the customer from the database using the customer ID
//    	CustomerDetails customer = customerDao.findByCustomerId(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer ID"));
//
//        // Return the image data from the customer entity
//        return customer.getAadharCardPhoto();
//    }
//}
//
