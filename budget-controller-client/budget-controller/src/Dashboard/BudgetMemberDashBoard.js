import { Link } from "react-router-dom";
import Directories from "../Directories";
import { useState } from "react";
import { useEffect } from "react";
import { useContext } from "react";
import AuthContext from "../AuthContext";
import { PieChart, Pie, Legend, Tooltip } from 'recharts';

function BudgetMemberDashboard() {
    // make fetch request for category pie chart
    // make fetch request for transactions pie chart
   
    const auth = useContext(AuthContext);
    const [hasBudget, setHasBudget] = useState(false);
    const [budgetCategories, setBudgetCategories] = useState();
    const [budgetTransactionTotals,setBudgetTransactionTotals] = useState();
    const [errors, setErrors] = useState([]);


    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal" , {
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
            console.log(budget)
            if(budget !== undefined){
            setHasBudget(true);
            }
            const categories = budget.categories;
            setBudgetCategories(categories);
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
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
            
            console.log(result);
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    }, [])

    return (
        <div className="container">
            
            {hasBudget === false ?
            <div>
                <div className="createBudgetLink">
                    <Link to={Directories.CREATEBUDGET}>Create Budget</Link>
                </div>
                <div className="tipLink">
                    <><a href="https://n26.com/en-eu/blog/50-30-20-rule#:~:text=The%2050%2F30%2F20%20rule%20is%20an%20easy%20budgeting%20method,savings%20or%20paying%20off%20debt." target={"_blank"}> Click here for some advice! </a></>
                </div>
            </div>
            :
            <div>
                <div className="container">
                    <h2 >Budget Pie Chart</h2>
                    <PieChart width={1000} height={400}>
                        <Pie data={budgetCategories} dataKey="categoryPercent" nameKey="categoryName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                        <Tooltip />
                    </PieChart>
                </div> 
                <div>
                    <h2>Transaction Pie Chart</h2>
                    <Link to={Directories.ADDTRANSACTION}className="dashSubmitButton"> Add Transaction </Link>
                <Link to={Directories.MEMBERVIEW}>
                    <PieChart  width={1010} height={410} >
                        <Pie data={budgetTransactionTotals} dataKey="value" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#000000" label />
                        <Tooltip /> 
                     </PieChart>
                </Link> :
                
                    
                    
            </div>
          
        </div>
}
        </div>
    )
}    
export default BudgetMemberDashboard;