import shibe from "../Images/PieChart.jpg"
import {Link} from "react-router-dom";
import "./Dashboard.css";
import Directories from "../Directories";

function BudgetOwnerDashboard(){
    return(
        <div>

            <div>
           
            <Link to = {Directories.EDITBUDGET} className="budgetPieContainer" >
                <img src={shibe} className="budgetPieChart" alt="..."/>
            </Link>
            <Link to = {Directories.OWNERVIEW} className="spendingPieContainer" >
                <img src={shibe} className="spendingPieChart" alt="..."/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ={Directories.OWNERMANAGEAUTO}> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetOwnerDashboard