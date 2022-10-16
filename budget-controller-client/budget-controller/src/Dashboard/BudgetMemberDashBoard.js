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
    const [categoryData, setCategoryData] = useState([]);
    const [transactionData, setTransactionData] = useState([]);
    const auth = useContext(AuthContext);
    const [hasBudget, setHasBudget] = useState(false);

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
            setCategoryData(budget);
            console.log(categoryData);
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
                setTransactionData(transactions);
                console.log(transactionData);
            })
            .catch(error => {
                console.log(error);
            });
    }, [])

    return (
        <div>

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
            <div>
                <PieChart width={400} height={400} >
                    <Pie dataKey="value" data={categoryData} cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip />
                </PieChart>
                <Link to={Directories.MEMBERVIEW} className="spendingPieContainer" >
                    <PieChart data={transactionData} />
                </Link>
            </div>
     

            <div className="manageTransactions">
                <Link to={Directories.MEMBERMANAGEAUTO}> Manage auto transactions </Link>
            </div>

        </div>  
}
        </div>
        
    ) } export default BudgetMemberDashboard;

