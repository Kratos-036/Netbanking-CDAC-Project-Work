import "../../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { Link } from "react-router-dom";
import "./EmployeeSideNavigationMenu.css"; // Import your custom CSS file

function EmployeeSideNavigationMenu() {
  return (
    <div className="outerEmpSide">
      <div className="accordion" id="accordionExample">
        <div className="accordion-item">
          <h2 className="accordion-header" id="headingOne">
            <button
              className="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseOne"
              aria-expanded="true"
              aria-controls="collapseOne"
            >
              Accounts
            </button>
          </h2>
          <div
            id="collapseOne"
            className="accordion-collapse collapse show"
            aria-labelledby="headingOne"
            data-bs-parent="#accordionExample"
          >
            <div className="accordion-body">
              <div className="list-group">
                <Link
                  to="/Employee/Accounts/SearchCustomer47"
                  className="list-group-item list-group-item-action list-group-item-primary"
                >
                  Search Customer
                </Link>
                <Link
                  to="/Employee/Accounts/VerifyApproveKYC50"
                  className="list-group-item list-group-item-action list-group-item-success"
                >
                  Verify / Approve KYC
                </Link>
                <Link
                  to="/Employee/Accounts/FreezeAccount"
                  className="list-group-item list-group-item-action list-group-item-danger"
                >
                  Freeze Account
                </Link>
              </div>
            </div>
          </div>
        </div>

        <div className="accordion-item">
          <h2 className="accordion-header" id="headingTwo">
            <button
              className="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseTwo"
              aria-expanded="false"
              aria-controls="collapseTwo"
            >
              Funds Transfer
            </button>
          </h2>
          <div
            id="collapseTwo"
            className="accordion-collapse collapse"
            aria-labelledby="headingTwo"
            data-bs-parent="#accordionExample"
          >
            <div className="accordion-body">
              <div className="list-group">
                <Link
                  to="/Employee/FundTransfer/DepositMoney43"
                  className="list-group-item list-group-item-action list-group-item-warning"
                >
                  Deposit Money
                </Link>
              </div>
            </div>
          </div>
        </div>

        <div className="accordion-item">
          <h2 className="accordion-header" id="headingFour">
            <button
              className="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseFour"
              aria-expanded="false"
              aria-controls="collapseFour"
            >
              Logout
            </button>
          </h2>
          <div
            id="collapseFour"
            className="accordion-collapse collapse"
            aria-labelledby="headingFour"
            data-bs-parent="#accordionExample"
          >
            <div className="accordion-body">
              <div className="list-group">
                  <Link
                    to="/logout"
                    className="list-group-item list-group-item-action list-group-item-danger"
                  >
                    Log Out
                  </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default EmployeeSideNavigationMenu;
