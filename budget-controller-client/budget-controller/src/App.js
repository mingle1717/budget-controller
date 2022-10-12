import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import {useEffect, useState} from 'react';
import Navbar from './Navbar';
import Home from './Home';
import Contact from './Contact';
import Login from './Authentication/Login';
import Signup from './Authentication/Signup';
import AddTransaction from './Transaction/AddTransaction';
import CreateBudget from './Budget/CreateBudget';
import jwtDecode from "jwt-decode";
import AuthContext from "./AuthContext";
import EditBudget from "./Budget/EditBudget";
import BudgetMemberDashboard from './Dashboard/BudgetMemberDashBoard';
import BudgetMemberView from "./Transaction/BudgetMemberView";
import BudgetOwnerView from "./Transaction/BudgetOwnerView";
import BudgetOwnerDashboard from './Dashboard/BudgetOwnerDashboard';
import EditTransaction from './Transaction/EditTransaction';
import './App.css';
import BudgetMemberManageAuto from './AutoTransaction/BudgetMemberManageAuto';
import BudgetOwnerManageAuto from './AutoTransaction/BudgetOwnerManageAuto';

const LOCAL_STORAGE_TOKEN_KEY = "budgetCalcToken";


function App() {

   const [user, setUser] = useState(null);

   const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  
   useEffect( () => {
     const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
     if(token){
       login(token);
     }
     setRestoreLoginAttemptCompleted(true);
   },[]);



  const login = (token) => {


    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    const {sub: username,  roles:rolesList} = jwtDecode(token);

    const userRoles = rolesList;

    const user = {
      username,
      userRoles,
      token,
      hasRole(role){
        return this.roles.includes(role);
      }
    };

    console.log(user);

    setUser(user);

    return user;
  }



   const logout = () => {
     setUser(null);

     localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
   };


     const auth ={
     user: user ? {...user} : null,
     login,
     logout
   };


   if (!restoreLoginAttemptCompleted) {
     return null;
   }


  return (
    <div className="App">
      <AuthContext.Provider value={auth}>
      <BrowserRouter>
        <Navbar setUser={setUser}/>
          <Switch>
            <Route path="/home">
              {!user ? <Home/> : (user.userRoles[0].roleName === "Admin" ? <Redirect to = "/budgetownerdashboard"/> : <Redirect to ="budgetmemberdashboard"/>)}
            </Route>
            <Route path="/contact">
              <Contact/>
            </Route>
            <Route path="/login">
              {!user ? <Login setUser={setUser}/> : (user.userRoles[0].roleName === "Admin" ? <Redirect to="/budgetownerdashboard"/> : <Redirect to="/budgetmemberdashboard"/> )}
            </Route>
            <Route path="/signup">
              <Signup/>
            </Route>
            <Route path="/createbudget">
              {user ? (user.userRoles[0].roleName === "Member" ? <CreateBudget/>:<Redirect to="/budgetownerdashboard"/> ) : <Redirect to="/login"/>}
            </Route>
            <Route path="/editbudget">
              {user ?(user.userRoles[0].roleName === "Admin" ? <EditBudget/>:<Redirect to="/budgetmemberdashboard"/> ): <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetmemberview">
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberView/>:<Redirect to="/budgetownerview"/> ) : <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetownerview">
              {user ?(user.userRoles[0].roleName === "Admin" ? <BudgetOwnerView/>:<Redirect to="/budgetmemberview"/> ) : <Redirect to="/login"/>}
            </Route>
            <Route path="/addtransaction">
              {user ? <AddTransaction/> : <Redirect to="/login"/>}
            </Route>
            <Route path="/editTransaction">
              {user ? <EditTransaction/> : <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetmemberdashboard">
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberDashboard/>:<Redirect to="/budgetownerdashboard"/> ): <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetownerdashboard">
              {user ? (user.userRoles[0].roleName === "Admin" ? <BudgetOwnerDashboard/>:<Redirect to="/budgetmemberdashboard"/> ) : <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetmembermanageauto">
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberManageAuto/>:<Redirect to="/budgetownermanageauto"/> ) : <Redirect to="/login"/>}
            </Route>
            <Route path="/budgetownermanageauto">
              {user ? (user.userRoles[0].roleName === "Admin" ? <BudgetOwnerManageAuto/>:<Redirect to="/budgetmembermanageauto"/> ) : <Redirect to="/login"/>}
            </Route>


          </Switch>
      
      </BrowserRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
