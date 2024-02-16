package com.aarna.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Account_Transactions")
public class AccountTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Transaction_ID")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "Account_Number", nullable = false)
    private CustomerDetails customer;

    @Column(name = "Transaction_Amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "Transaction_Type", nullable = false)
    private String transactionType;

    @Column(name = "Transaction_Timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionTimestamp;

    @Column(name = "Recipient_ID")
    private String recipientId;

    @Column(name = "Transaction_Remarks")
    private String transactionRemarks;

    @Column(name = "Transaction_By_ID", nullable = false)
    private Long transactionById;

    // Add getters and setters here

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public CustomerDetails getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDetails customer) {
        this.customer = customer;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Date transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getTransactionRemarks() {
        return transactionRemarks;
    }

    public void setTransactionRemarks(String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }

    public Long getTransactionById() {
        return transactionById;
    }

    public void setTransactionById(Long transactionById) {
        this.transactionById = transactionById;
    }
    

}
