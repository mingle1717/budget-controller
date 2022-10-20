
import shibe from "./Images/Ownerview.PNG"
import shibe2 from "./Images/ownerDashboard.PNG"
import shibe3 from "./Images/createBudget.PNG"
import './Home.css'

function Home(){
    return(
        <div>
            
            <h2 className="homeHeader">Check out these features!</h2>
            <div id="carouselExampleControls" className="carousel slide" data-bs-ride="carousel">
  <div className="carousel-inner">
    <div className="carousel-item active">
      <img src={shibe} className="myCarouselImage" alt="..."/>
    </div>
    <div className="carousel-item">
      <img src={shibe2} className="carouselImage" alt="..."/>
    </div>
    <div className="carousel-item">
      <img src={shibe3} className="carouselImage" alt="..."/>
    </div>
  </div>
  <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
    <span className="visually-hidden">Previous</span>
  </button>
  <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
    <span className="carousel-control-next-icon" aria-hidden="true"></span>
    <span className="visually-hidden">Next</span>
  </button>
</div>
        </div>
        );
    }
export default Home;