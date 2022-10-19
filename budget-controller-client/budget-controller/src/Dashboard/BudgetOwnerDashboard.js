import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";
import Directories from "../Directories";
import { PieChart, Pie, Legend, Tooltip, Cell } from 'recharts';
import { useEffect } from "react";
import { useContext, useState } from "react";
import AuthContext from "../AuthContext";
function BudgetOwnerDashboard(){

    const auth = useContext(AuthContext);
   
    const [budgetCategories, setBudgetCategories] = useState();
    const [budgetTransactionTotals, setBudgetTransactionTotals] = useState([]);
    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

    
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal", {
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
<<<<<<< HEAD
            console.log(categories);
=======
            setBudgetId(budget.budgetId);
            console.log(budget.budgetId);
            console.log(budgetTransactionTotals);
>>>>>>> 1ccf61ce8b7e8527d57e12e3f76e1359c32013a6
        })
        .catch(error => {
            console.log(error);
        });
    }, [])

    useEffect(() => {
        fetch("http://localhost:8080/api/transaction/transactionbalance", {
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
        .then(result => {
            setBudgetTransactionTotals(result);
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
                        <PieChart width={1010} height={410} >
                        <Pie data={budgetCategories} cx={120} cy={200} innerRadius={60} outerRadius={80} fill="#8884d8" paddingAngle={5} dataKey="categoryPercent"
                            nameKey="categoryName">
                            {budgetTransactionTotals.map((entry, index) => (
                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                        </Pie>
                            <Tooltip />
                        </PieChart> 
                    </Link>
: null
                    }
               </div> 
                    
               <div>
                 <h2  >Transaction Pie Chart</h2>

                 <Link to={Directories.ADDTRANSACTION}className="dashSubmitButton"> Add Transaction </Link>
                <Link to={Directories.OWNERVIEW + "/" + budgetId}>
                    <PieChart  width={1010} height={410} >
                        <Pie data={budgetTransactionTotals} dataKey="value" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#000000" label />
                        <Tooltip /> 
                     </PieChart>
                </Link> :
                

           </div>
     

            <div className="manageTransactions">
                <Link to={Directories.OWNERMANAGEAUTO} className="dashSubmitButton"> Manage auto transactions </Link>
            </div>

      
            
        </div>
        )
}
export default BudgetOwnerDashboard