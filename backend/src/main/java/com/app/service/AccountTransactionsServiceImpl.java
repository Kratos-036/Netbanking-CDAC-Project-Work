package com.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.app.dao.AccountTransactionsDao;
import com.app.dao.CustomerDao;
import com.app.entities.AccountTransactions;

@Service
@Transactional
public class AccountTransactionsServiceImpl implements AccountTransactionsService {

	@Autowired
	CustomerDao customerDao;
	
	@Autowired 
	AccountTransactionsDao accountTransactionsDao;
	
	@Override
	public List<AccountTransactions> getAllTransactionDetails(Long customerId) {
		return accountTransactionsDao.findAllTransactionsWithCustomerIdInCustomerDetails(customerId);
	}

	@Override
	public Page<AccountTransactions> getTransactionsByCustomerId(Long customerId, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Page<AccountTransactions> getAllTransactionDetailsPaginated(Long customerId, int pageNumber, int pageSize) {
        return accountTransactionsDao.findByCustomerId(customerId, PageRequest.of(pageNumber, pageSize));
    }
}
