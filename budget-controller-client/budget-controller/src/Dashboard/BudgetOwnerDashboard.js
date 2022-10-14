import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";
import CategoriesPieChart from "../Budget/CategoriesPieChart";
import Example from "../Budget/CategoriesPieChart";
import TransactionsPieChart from "../Transaction/TransactionsPieChart";


function BudgetOwnerDashboard(){
    return(
        <div>
            <h1>Budget Owner Dashboard</h1>

            <div>
           
            <Link to = "/editbudget" className="budgetPieContainer" >
                <CategoriesPieChart/>
            </Link>
            <Link to = "/budgetownerview" className="spendingPieContainer" >
                <TransactionsPieChart/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ="/budgetownermanageauto"> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetOwnerDashboard