import shibe from "../Images/shibe.jpg"
import {Link} from "react-router-dom";


function BudgetOwnerDashboard(){
    return(
        <div>
            <h1>Budget Owner Dashboard</h1>
            <Link to ="/budgetownermanageauto"> Manage auto transactions </Link>
            <div className="budgetPieContainer" >
                <img src={shibe} className="card-img-top" alt="..."/>
                <div className="card-body">
                    <h5 className="card-title">Card title</h5>
                    <p className="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    <a href="#" className="btn btn-primary">Go somewhere</a>
            </div>
</div>
        </div>
        )
}
export default BudgetOwnerDashboard