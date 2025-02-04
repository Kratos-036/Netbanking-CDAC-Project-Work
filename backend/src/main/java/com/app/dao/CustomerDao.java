package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.entities.CustomerDetails;

public interface CustomerDao extends JpaRepository<CustomerDetails, Long>{
	// add a finder : JPQL : select e from Employee e where e.dept.id=:id
		Optional<CustomerDetails> findByCustomerId(Long customerId);
		
		Optional<CustomerDetails> findByAccountNumber(String accountNumber);

		@Query("SELECT c FROM CustomerDetails c WHERE c.accountHolderFirstName = :firstName")
	    List<CustomerDetails> findByAccountHolderFirstName(@Param("firstName") String firstName);

		@Query("SELECT c FROM CustomerDetails c WHERE c.accountHolderLastName = :lastName")
	    List<CustomerDetails> findByAccountHolderLastName(@Param("lastName") String lastName);

		@Query(value = "SELECT * FROM customer_details", nativeQuery = true)
		List<CustomerDetails> getAllCustomerDetails();

		CustomerDetails findByUsername(String username);		
}
