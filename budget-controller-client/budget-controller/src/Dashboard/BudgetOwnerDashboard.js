import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";
import Directories from "../Directories";
import { PieChart, Pie, Legend, Tooltip } from 'recharts';
import { useEffect } from "react";
import { useContext, useState } from "react";
import AuthContext from "../AuthContext";
function BudgetOwnerDashboard(){

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

    return(
        <div>

            <div>  
                <div>
                <PieChart width={400} height={400} >
                    <Pie dataKey="value" data={categoryData} cx={500} cy={200} innerRadius={40} outerRadius={80} fill="#82ca9d" />
                    <Tooltip />
                </PieChart>
                <Link to={Directories.OWNERVIEW} className="spendingPieContainer" >
                    <PieChart data={transactionData} />
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