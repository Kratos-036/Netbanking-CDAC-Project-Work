// Import necessary libraries and components
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { FaFileExport, FaPrint } from "react-icons/fa";
import { Modal, Button } from "react-bootstrap";
import EmployeeTopNavigationBar from "../../components/EmployeeTopNavigationBar";
import EmployeeSideNavigationMenu from "../../components/EmployeeSideNavigationMenu";
import styles from "./SearchCustomer48.module.css";

const SearchCustomer48 = () => {
  // Use the useParams hook to get the customer ID from the URL parameter
  const { customerId } = useParams();

  // State variables to manage modal visibility and selected document
  const [showModal, setShowModal] = useState(false);
  const [selectedDocument, setSelectedDocument] = useState(null);

  // State variables to store customer details and uploaded documents
  const [customerDetails, setCustomerDetails] = useState(null);
  const [uploadedDocuments, setUploadedDocuments] = useState([]);

  // Fetch customer details and uploaded documents based on the customer ID
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Make an API call to get all customer details
        const response = await axios.get(
          "http://localhost:8080/Employee/Accounts/GetAllCustomerDetails"
        );

        // Extract the data from the response
        const data = response.data;

        // Map the data to the required format
        const mappedData = data.map((customer) => ({
          id: customer.customerId,
          accountNumber: customer.accountNumber,
          customerName: `${customer.accountHolderFirstName} ${customer.accountHolderLastName}`,
          balance: customer.balance,
          occupation: customer.occupation,
          annualIncome: customer.annualIncome,
          gender: customer.gender,
          birthDate: customer.dateOfBirth,
          mobileNumber: customer.mobileNumber,
          emailID: customer.emailId,
          address: customer.address,
          city: customer.city,
          state: customer.state,
          pincode: customer.pincode,
          nationality: customer.nationality,
        }));

        // Find the customer details based on the customer ID
        const foundCustomer = mappedData.find(
          (customer) => customer.id === Number(customerId)
        );

        // Set the customer details and uploaded documents in the state
        setCustomerDetails(foundCustomer);
        setUploadedDocuments([
          { id: 1, name: "AADHAR CARD", link: "/path/to/document1.pdf" },
          { id: 2, name: "PAN CARD", link: "/path/to/document2.pdf" },
          { id: 3, name: "PROFILE PHOTO", link: "/path/to/document3.pdf" },
          // Add more documents as needed
        ]);
      } catch (error) {
        console.error("Error fetching customer data:", error);
      }
    };

    // Call the fetchData function when the component mounts or the customerId changes
    fetchData();
  }, [customerId]);

  // Handle the export button click
  const handleExport = () => {
    // Construct the CSV content based on customer details
    const csvContent =
      "Customer ID,Account Number,Name,Balance,Occupation,Annual Income,Gender,Birth Date,Mobile Number,Email ID,Address,City,State,Pincode,Nationality\n" +
      [customerDetails] // Wrap customerDetails in an array
        .map(
          (customer) =>
            `${customer.id},${customer.accountNumber},"${customer.customerName}",${customer.balance},"${customer.occupation}",${customer.annualIncome},"${customer.gender}",${customer.birthDate},${customer.mobileNumber},${customer.emailID},"${customer.address}","${customer.city}","${customer.state}",${customer.pincode},"${customer.nationality}"`
        )
        .join("\n");

    // Create a Blob from the CSV content
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });

    // Create a link element to trigger the download
    const link = document.createElement("a");

    // Check if the browser supports the download attribute
    if (link.download !== undefined) {
      // Create a URL for the Blob
      const url = URL.createObjectURL(blob);

      // Set the link attributes for download
      link.setAttribute("href", url);
      link.setAttribute("download", "customer_list.csv");

      // Append the link to the document body
      document.body.appendChild(link);

      // Trigger a click on the link to start the download
      link.click();

      // Remove the link from the document body
      document.body.removeChild(link);
    }
  };

  // Handle the download button click
  const handleDownload = () => {
    // Trigger the browser's print functionality
    window.print();
  };

  // Handle the click on a document to view
  const handleDocumentClick = (document) => {
    // Set the selected document and show the modal
    setSelectedDocument(document);
    setShowModal(true);
  };

  // Handle the modal close
  const handleCloseModal = () => {
    // Close the modal
    setShowModal(false);
  };

  // Handle the download button click inside the modal
  const handleDownloadDocument = () => {
    // Implement logic to download the document
    if (selectedDocument) {
      console.log("Downloading document:", selectedDocument.name);
    }
  };

  // Render the component
  return (
    <div>
      <EmployeeTopNavigationBar />
      <div className="d-flex">
        <EmployeeSideNavigationMenu />
        <div className={`${styles.customerDetailsContainer} mt-4 ml-4 p-4`}>
          <h2 className={`mb-3 ${styles.heading}`}>Customer Details</h2>
          <div className="row">
            <div className="col-md-6">
              <div className="mb-3">
                <strong>ID:</strong> {customerDetails?.id}
              </div>
              <div className="mb-3">
                <strong>Account Number:</strong>{" "}
                {customerDetails?.accountNumber}
              </div>
              <div className="mb-3">
                <strong>Name :</strong> {customerDetails?.customerName}
              </div>
              <div className="mb-3">
                <strong>Balance :</strong> {customerDetails?.balance}
              </div>

              <div className="mb-3">
                <strong>Annual Income :</strong> {customerDetails?.annualIncome}
              </div>
            </div>

            <div className="col-md-6">
              <div className="mb-3">
                <strong>Gender :</strong> {customerDetails?.gender}
              </div>
              <div className="mb-3">
                <strong>Birth Date :</strong> {customerDetails?.birthDate}
              </div>
              <div className="mb-3">
                <strong>Mobile Number :</strong> {customerDetails?.mobileNumber}
              </div>
              <div className="mb-3">
                <strong>Email Id :</strong> {customerDetails?.emailID}
              </div>
            </div>
            <div className="mb-3">
              <strong>Address :</strong> {customerDetails?.address}
            </div>
            <div className="mb-3">
              <strong>City :</strong> {customerDetails?.city}
            </div>
            <div className="mb-3">
              <strong>State :</strong> {customerDetails?.state}
            </div>
            <div className="mb-3">
              <strong>Pincode :</strong> {customerDetails?.pincode}
            </div>
            <div className="mb-3">
              <strong>Nationality :</strong> {customerDetails?.nationality}
            </div>
          </div>

          {/* Uploaded Documents */}
          <h3 className="mt-4 mb-3">Uploaded Documents</h3>
          <ul className={`list-group ${styles.listGroup}`}>
            {uploadedDocuments.map((document) => (
              <li
                key={document.id}
                className={`${styles.listGroupItem} list-group-item d-flex justify-content-between align-items-center`}
              >
                <span>{document.name}</span>
                <div>
                  <button
                    className={`btn btn-primary btn-sm mr-2 ${styles.btnPrimary}`}
                    onClick={() => handleDocumentClick(document)}
                  >
                    View Document
                  </button>
                </div>
              </li>
            ))}
          </ul>

          {/* Document Modal */}
          <Modal show={showModal} onHide={handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>
                {selectedDocument && selectedDocument.name}
              </Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {/* Embed a PDF viewer or an iframe to display the document */}
              {/* Example: */}
              {/* <iframe src={selectedDocument && selectedDocument.link} title="Document Viewer" width="100%" height="500px"></iframe> */}
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleCloseModal}>
                Close
              </Button>
              <Button variant="primary" onClick={handleDownloadDocument}>
                Download
              </Button>
            </Modal.Footer>
          </Modal>

          {/* Export and Download buttons */}
          <div>
            <button
              className={`btn btn-success mr-3 ${styles.exportButton}`}
              onClick={handleExport}
            >
              <FaFileExport />
              Export as CSV
            </button>
            <button
              className={`btn btn-info ml-3 ${styles.downloadButton}`}
              onClick={handleDownload}
            >
              <FaPrint />
              Download Page
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchCustomer48;
