import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";

function BudgetOwnerDashboard(){
    return(
        <div>
            <h1>Budget Owner Dashboard</h1>

            <div>
           
            <Link to = "/editbudget" className="budgetPieContainer" >
                <img src={shibe} className="budgetPieChart" alt="..."/>
            </Link>
            <Link to = "/budgetownerview" className="spendingPieContainer" >
                <img src={shibe} className="spendingPieChart" alt="..."/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ="/budgetownermanageauto"> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetOwnerDashboard