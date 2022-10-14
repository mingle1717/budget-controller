import {Link} from "react-router-dom"
import shibe from "../Images/PieChart.jpg"
import Directories from "../Directories"
function BudgetMemberDashboard(){
    return(
        <div>

            <div>
            <Link to={Directories.CREATEBUDGET}>Create Budget</Link>
            </div>
            <Link> Click here for some advice! </Link>
            <div>
                <img src={shibe} className="budgetPieChart" alt="..."/>
            <Link to ={Directories.MEMBERVIEW} className="spendingPieContainer" >
                <img src={shibe} className="spendingPieChart" alt="..."/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ={Directories.MEMBERMANAGEAUTO}> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetMemberDashboard