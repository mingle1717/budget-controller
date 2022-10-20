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
    const [budgetCategoriesTotals, setBudgetCategoriesTotals] = useState([]);
    const [budgetTransactionTotals, setBudgetTransactionTotals] = useState([]);
    const [errors, setErrors] = useState([]);
    const [budgetId, setBudgetId] = useState();
    const [limitMessage, setLimitMessage] = useState();
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
            console.log(budget)
            const categories = budget.categories;
            const budgetCategoriesTotalsCopy = [...budgetCategoriesTotals];
            for(let i = 0; i < categories.length; i++){
                const currentTotal = Math.ceil(budget.balance * (categories[i].categoryPercent/100))
                
                budgetCategoriesTotalsCopy.push({categoryName : categories[i].categoryName, total : currentTotal, higherLimit: 0, lowerLimit: 0 });
                
            }
            setBudgetCategoriesTotals(budgetCategoriesTotalsCopy);
            
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
            console.log(result)
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
        
    }, [])
    // function displayLimitMessage(){
    //     console.log("click")
    //     console.log(budgetCategoriesTotals)
    //     console.log(budgetTransactionTotals)
    //     for(let i =0; i < budgetCategoriesTotals.length; i++){
    //         for(let k=0; k<budgetTransactionTotals.length;k++){
    //             if(budgetTransactionTotals[k].name === budgetCategoriesTotals[i].categoryName){
    //                 if(budgetCategoriesTotals[i].total - budgetTransactionTotals[k].value > budgetCategoriesTotals[i].higherLimit){
    //                     setLimitMessage("You still have more to spend on " + budgetCategoriesTotals[i].name)
    //                     console.log("You still have more to spend on " + budgetCategoriesTotals[i].name)
    //                 }
    //                 else if(budgetCategoriesTotals[i].total - budgetTransactionTotals[k].value < budgetCategoriesTotals[i].lowerLimit){
    //                     setLimitMessage("You have overspent on " + budgetCategoriesTotals[i].name)
    //                     console.log("You have overspent on " + budgetCategoriesTotals[i].name)
    //                 }
    //                 else{
    //                     console.log("rip")
    //                 }
    //             }
    //         }
    //     }
    // }
    return(
        <div className="container">
            
            <h1 className="dashboardHeader">Click on a chart to view details!</h1>
                  
            
                    <div className="budgetPieChart">
                    <h2 >My Budget</h2>
                    
                    { budgetCategories ? 
                    <Link to={Directories.EDITBUDGET}>
                        <PieChart width={1010} height={410} >
                        <Pie data={budgetCategoriesTotals} cx={120} cy={200} innerRadius={60} outerRadius={80} fill="#8884d8" paddingAngle={5} dataKey="total"
                            nameKey="categoryName" label>
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
           
                    
              
                <div className="transactionPieChart">
                 <h2  >My Transactions</h2>
                 
                 
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
                </Link> 
                </div>

          
     

         

           <Link to={Directories.ADDTRANSACTION}className="dashSubmitButton"> Add Transaction </Link>
            
        </div>
        )
}
export default BudgetOwnerDashboard