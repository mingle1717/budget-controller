import {Link} from "react-router-dom"
import shibe from "../Images/PieChart.jpg"

function BudgetMemberDashboard(){
    return(
        <div>
            <h1>Budget Member Dashboard</h1>

            <div>
            <Link to="/createbudget">Create Budget</Link>
            </div>
            <Link> Click here for some advice! </Link>
            <div>
                <img src={shibe} className="budgetPieChart" alt="..."/>
            <Link to = "/budgetmemberview" className="spendingPieContainer" >
                <img src={shibe} className="spendingPieChart" alt="..."/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ="/budgetmembermanageauto"> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetMemberDashboard