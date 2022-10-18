import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";
import Directories from "../Directories";
import { PieChart, Pie, Legend, Tooltip } from 'recharts';
import { useEffect } from "react";
import { useContext, useState } from "react";
import AuthContext from "../AuthContext";
function BudgetOwnerDashboard(){

    const auth = useContext(AuthContext);
   
    const [budgetCategories, setBudgetCategories] = useState();
    const [budgetTransactions,setBudgetTransactions] = useState();
    

    
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal/" + auth.user.username, {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                
                console.log(response);
            }
        })
        .then(budget => {
            const categories = budget.categories;
            setBudgetCategories(categories);
            console.log(categories);
        })
        .catch(error => {
            console.log(error);
        });
    }, [])

    useEffect(() => {
        fetch("http://localhost:8080/api/transaction/" + auth.user.username, {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                console.log(response);
            }
        })
        .then(transactions => {
            setBudgetTransactions(transactions);
            console.log(transactions)
        })
        .catch(error => {
            console.log(error);
        });
    }, [])

    return(
        <div className="container">
            <h1 className="dashboardHeader">Click on a chart to view details!</h1>
   
                <div> 
                    <h2 >Budget Pie Chart</h2>
                    { budgetCategories ?
                    <Link to={Directories.EDITBUDGET}>
                        <PieChart  width={1000} height={400}>
                            <Pie data={budgetCategories} dataKey="categoryPercent" nameKey="categoryName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                            <Tooltip />
                        </PieChart> 
                    </Link>
: null
                    }
               </div> 
                    
               <div>
                 <h2  >Transaction Pie Chart</h2>
                <Link to={Directories.OWNERVIEW}>
                    <PieChart  width={1010} height={410} >
                        <Pie data={budgetTransactions} dataKey="transactionAmount" nameKey="transactionName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                        <Pie data={budgetTransactions} dataKey="categoryId" nameKey="categoryName" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#000000" label />
                        <Tooltip /> 
                     </PieChart>
                </Link>
           </div>
     

            <div className="manageTransactions">
                <Link to={Directories.OWNERMANAGEAUTO} className="dashSubmitButton"> Manage auto transactions </Link>
            </div>

      
            
        </div>
        )
}
export default BudgetOwnerDashboard