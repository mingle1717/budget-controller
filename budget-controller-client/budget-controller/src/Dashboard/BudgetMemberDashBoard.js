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
            console.log(budgetCategories);
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
                console.log(budgetTransactions);
            })
            .catch(error => {
                console.log(error);
            });
    }, [])

    return (
        <div className="container">
            {hasBudget === false ?
            <div>
                <div className="createBudgetLink">
                    <Link to={Directories.CREATEBUDGET}>Create Budget</Link>
                </div>
            <div className="tipLink">
                <Link to="google.com"> Click here for some advice! </Link>
            </div>
            </div>
            :
            <div>
            <div className="container budgetPieContainer">
            <h2 className="budgetPieHeader">Budget Pie Chart</h2>
            <PieChart className="budgetPieChart" width={1000} height={400}>
                    <Pie data={budgetCategories} dataKey="categoryPercent" nameKey="categoryName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip />
                </PieChart>
                </div> 
                <div className="spendingPieContainer">
                <h2 className="transactionPieHeader" >Transaction Pie Chart</h2>
                <Link to={Directories.MEMBERVIEW}  >
                <PieChart className="spendingPieChart" width={1000} height={400} >
                    <Pie data={budgetTransactions} dataKey="transactionAmount" nameKey="transactionName"   cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip /> 
                </PieChart>
                </Link>
            </div>
            <div className="manageTransactions">
                <Link to={Directories.MEMBERMANAGEAUTO} className="dashSubmitButton"> Manage auto transactions </Link>
            </div>
            </div>
            }
        </div>
    )
}
export default BudgetMemberDashboard;