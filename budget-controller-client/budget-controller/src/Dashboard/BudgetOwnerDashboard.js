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
    const [hasBudget, setHasBudget] = useState(false);
    const [budgetCategories, setBudgetCategories] = useState();
    const [budgetTransactions,setBudgetTransactions] = useState();
    let budgetCategoriesName = "";
    let budgetCategoriesPercent = "";
    
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
                setHasBudget(true);
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
                const transactionsCopy = transactions;
                setBudgetTransactions(transactionsCopy);
                console.log(budgetTransactions)
            })
            .catch(error => {
                console.log(error);
            });
    }, [])

    return(
        <div>

            <div>  
           
                <div className="budgetPieContainer"> 
                <h2 className="budgetPieHeader">Budget Pie Chart</h2>
                <PieChart width={1000} height={400}>
                    <Pie data={budgetCategories} dataKey="categoryPercent" nameKey="categoryName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip />
                </PieChart>
                
               </div> 
             
               <div className="spendingPieContainer" >
                 <h2 className="transactionPieHeader" >Transaction Pie Chart</h2>
                <Link to={Directories.OWNERVIEW} c>
                    <PieChart width={1000} height={400} >
                    <Pie data={budgetTransactions} dataKey="transactionAmount" nameKey="transactionName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip /> 
                     </PieChart>
                </Link>
           </div>
     

            <div className="manageTransactions">
                <Link to={Directories.OWNERMANAGEAUTO}> Manage auto transactions </Link>
            </div>

        </div>  
            
        </div>
        )
}
export default BudgetOwnerDashboard