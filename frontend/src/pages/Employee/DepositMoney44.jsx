import EmployeeSideNavigationMenu from '../../components/EmployeeSideNavigationMenu';
import EmployeeTopNavigationBar from '../../components/EmployeeTopNavigationBar';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom'; 

const BASE_URL = "http://65.2.82.68:8080";

axios.defaults.headers.common["Authorization"] = `Bearer ${sessionStorage.getItem("jwt")}`;
axios.defaults.headers.post["Content-Type"] = "application/json";

const DepositMoney44 = () => {
    const accountNumberRedux = useSelector(state => state.depositMoney.customerAccountNumber);
    const depositMoneyRedux = useSelector(state => state.depositMoney.amount);
    const remarksRedux = useSelector(state => state.depositMoney.remarks);
    const navigate = useNavigate(); 
    const [empId, setEmpId] = useState();

    toast.info("🦄 Processing... Please wait", {
        position: "top-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored"
    });

    const empIdFunction = async () => {
        try {
            const response = await axios.get(
                BASE_URL + `/Employee/User`,
                {
                    headers: {
                        Authorization: `Bearer ${sessionStorage.getItem("jwt")}`
                    }
                }
            );
            setEmpId(response.data);
        } catch (error) {
            console.error("Error fetching employee data:", error);
        }
    };

    useEffect(() => {
        empIdFunction(); 
    }, []);

    const depositMoneyFunction = async () => {
        try {
            const url = BASE_URL + `/Employee/FundTransfer/DepositMoney/${empId}/${accountNumberRedux}?amountToDeposit=${depositMoneyRedux}&remarks=${remarksRedux}`;
            console.log("Request URL:", url);

            const response = await axios.post(url);
            console.log(response.data);
            toast.success("🦄 ₹"+depositMoneyRedux+" deposit successfull!", {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored"
            });
            
            setTimeout(() => {
                navigate("/Employee/FundTransfer/DepositMoney45");
            }, 3000);
        } catch (error) {
            console.log("Failed to deposit money:", error);
            toast.error("🦄 An error occurred while depositing money. Please try again!", {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            setTimeout(() => {
                navigate("/Employee/FundTransfer/DepositMoney43"); // Navigate to "/Employee/Accounts/SearchCustomer47" after 3 seconds
            }, 3000);
        }
    };

    useEffect(() => {
        if (empId) {
            depositMoneyFunction();
        }
    }, [empId]);

    return (  
        <div>
             <EmployeeTopNavigationBar/>
             <div style={{"display" : 'flex'}}>
                 <EmployeeSideNavigationMenu />
                 <div style={{ display: 'block', flexDirection: 'column', width: '100%', justifyContent: 'center', textAlign: 'center'}}>          
                     <br/>
                     <br/>
                     <br/>
                     <br/>
                     <br/>
                     <br/>
                     <br/>
                     <div style={{ justifyContent: 'center', alignItems: 'center', width:'80%'}}>
                         <h1> Processing your transaction ... 
                         </h1>
                     </div>
                     <hr/>
                     <div style={{ textAlign: 'center', display : 'flex', gap:'20px', justifyContent: 'center'}}>
                         {/* Pagination and Sorting*/}
                         <div>
                            <Link to="/Employee/Accounts/SearchCustomer47" className="btn btn-primary">
                                Go to Home
                             </Link>
                         </div>
 
                         <div>
                            <Link to="/Employee/FundTransfer/DepositMoney43" className="btn btn-warning">
                                Deposit Again
                             </Link>
                         </div>
                     </div>
                 </div>
             </div>
        </div> 
     );
}

export default DepositMoney44;
