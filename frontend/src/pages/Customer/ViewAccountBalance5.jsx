import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import CustomerSideNavigationMenu from "../../components/CustomerSideNavigationMenu";
import CustomerTopNavigationBar from "../../components/CustomerTopNavigationBar";
import backgroundImage from "../../resources/12.avif"; 

const ViewAccountBalance5 = () => {
  const [customerData, setCustomerData] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [accountDetails, setAccountDetails] = useState({});

  const BASE_URL = "http://65.2.82.68:8080";

  // setting a default authorization header for Axios requests
  axios.defaults.headers.common[
    "Authorization"
  ] = `Bearer ${sessionStorage.getItem("jwt")}`;
  axios.defaults.headers.post["Content-Type"] = "application/json";

  const [pageNumber, setPageNumber] = useState(0); // Initial page number
  const pageSize = 7; // Number of transactions per page
  const [disablePreviousButton, setDisablePreviousButton] = useState(true); // Disable the previous button initially
  const [disableNextButton, setDisableNextButton] = useState(false); // Disable the next button initially

  const loadPreviousTransactions = () => {
    if (pageNumber > 0) {
      setPageNumber(pageNumber - 1);
    }
  };

  const loadNextTransactions = () => {
    setPageNumber(pageNumber + 1);
  };

  useEffect(() => {
    const fetchCustomerData = async () => {
      try {
        const response = await axios.get(
          BASE_URL + `/Customer/User/GetMyDetails`
        );
        setCustomerData(response.data);
      } catch (error) {
        console.error("Error fetching customer data:", error);
      }
    };

    fetchCustomerData();
  }, []);

  useEffect(() => {
    if (customerData) {
      const customerId = customerData.customerId;
      const fetchData = async () => {
        try {
          const response = await axios.get(
            BASE_URL +
              `/Customer/Account/getPaginated&SortedAllTransactions/${customerId}?pageNumber=${pageNumber}&pageSize=${pageSize}`
          );
          setTransactions(response.data); // Assuming response.data contains the list of transactions

          // Disable the previous button if on the first page
          setDisablePreviousButton(pageNumber === 0);

          // Disable the next button if the length of transactions is less than the page size
          setDisableNextButton(response.data.length < pageSize);
        } catch (error) {
          console.error("Error fetching data:", error);
        }
      };

      fetchData();
    }
  }, [customerData, pageNumber]);

  useEffect(() => {
    if (customerData) {
      const customerId = customerData.customerId;
      const fetchbalanceAndAccountNumberData = async () => {
        try {
          const response = await axios.get(
            BASE_URL +
              `/Customer/Account/balanceAndAccountNumber/${customerId}`
          );

          // Assuming the API response contains an array with two elements: [balance, accountNumber]
          const [balance, accountNumber] = response.data;

          // Set the state with the fetched account details
          setAccountDetails({ balance, accountNumber });
        } catch (error) {
          console.error("Error fetching account details:", error);
        }
      };

      fetchbalanceAndAccountNumberData();
    }
  }, [customerData]);

  const backgroundStyle = {
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    minHeight: "100vh", // Ensure the background covers the entire viewport height
  };

  return (
    <div>
      <CustomerTopNavigationBar />
      <div style={{ display: "flex" }}>
        <CustomerSideNavigationMenu />
        <div
          className="container"
          style={{
            display: "block",
            backgroundColor: "lightcyan",
            ...backgroundStyle,
          }}
        
          >
          <br />
          <h2>
            <strong>Net Balance : ₹ {accountDetails.balance}</strong>
          </h2>
          <br />
          <div
            className="container"
            style={{
              display: "block",
              backgroundColor: "rgba(255, 255, 255, 0.5)",
              backdropFilter: "blur(5px)",
            }}
          >
            <h3>Primary Savings Account No. : {accountDetails.accountNumber}</h3>
            <br />
            <h4>Last 7 transactions</h4>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>Date & Time</th>
                  <th>Transaction ID</th>
                  <th>Transaction By</th>
                  <th>To</th>
                  <th>Credit Amount (+)</th>
                  <th>Debit Amount (-)</th>
                  <th>Account Balance</th>
                </tr>
              </thead>
              <tbody>
                {Array.isArray(transactions) &&
                  transactions.map((transaction, index) => (
                    <tr key={index}>
                      <td>{transaction["transactionTimestamp"]}</td>
                      <td>{transaction["transactionId"]}</td>
                      <td>{transaction["transactionById"]}</td>
                      <td>{transaction["recipientId"]}</td>
                      <td>
                        {transaction["transactionType"] === "+"
                          ? transaction["transactionAmount"]
                          : null}
                      </td>
                      <td>
                        {transaction["transactionType"] === "-"
                          ? transaction["transactionAmount"]
                          : null}
                      </td>
                      <td>{transaction["currentBalance"]}</td>
                    </tr>
                  ))}
              </tbody>
            </table>
            <br />
          </div>
          <br />
          <div
            style={{
              textAlign: "center",
              display: "flex",
              gap: "20px",
              justifyContent: "center",
            }}
          >
            {/* Pagination buttons */}
            <div>
              <button
                onClick={loadPreviousTransactions}
                className="btn btn-primary"
                disabled={disablePreviousButton}
              >
                Previous 7 transactions
              </button>
            </div>
            <div>
              <button
                onClick={loadNextTransactions}
                className="btn btn-info"
                disabled={disableNextButton}
              >
                Next 7 transactions
              </button>
            </div>
          </div>
          <hr />
          <div
            style={{
              textAlign: "center",
              backgroundColor: "rgba(255, 255, 255, 0.5)",
              backdropFilter: "blur(5px)",
            }}
          >
            <br />
            <Link
              to="/Customer/FundTransfer/WithdrawMoney6"
              className="btn btn-warning"
            >
              Withdraw Money
            </Link>
            <br />
            <br />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAccountBalance5;
