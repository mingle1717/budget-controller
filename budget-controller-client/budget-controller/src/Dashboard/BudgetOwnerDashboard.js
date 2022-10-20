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
    const [errors, setErrors] = useState([]);
    const [budgetId, setBudgetId] = useState();
    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

    
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal", {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(async response => {
            if (response.status === 200) {
                return response.json();
            } else {
                
                return Promise.reject(await response.json());
            }
        })
        .then(budget => {
            const categories = budget.categories;
            setBudgetCategories(categories);
            setBudgetId(budget.budgetId);
            
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
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
        .then(async response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(await response.json());
            }
        })
        .then(result => {
            setBudgetTransactionTotals(result);
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
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
                    <Pie data={budgetTransactionTotals} cx={120} cy={200} innerRadius={60} outerRadius={80} fill="#8884d8" paddingAngle={5} dataKey="value"
                            nameKey="name">
                            {budgetTransactionTotals.map((entry, index) => (
                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                        </Pie>
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