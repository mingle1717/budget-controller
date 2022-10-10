import { BrowserRouter, Switch, Route } from 'react-router-dom';
import {useState} from 'react';
import Navbar from './Navbar';
import Home from './Home';
import Contact from './Contact';
import Login from './Authentication/Login';
import Signup from './Authentication/Signup';
import AddTransaction from './Transaction/AddTransaction';
import CreateBudget from './Budget/CreateBudget';
import jwtDecode from "jwt-decode";
import AuthContext from "./AuthContext";

import './App.css';

function App() {

  const [user, setUser] = useState(null);

  const login = (token) => {
    const { sub: username, authorities: authoritiesString} = jwtDecode(token);

    const roles = authoritiesString.split(',');

    const user ={
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };

    console.log(user);

    setUser(user);

    return user;
  };

  const logout = () => {
    setUser(null);
  };

  const auth ={
    user: user ? {...user} : null,
    login,
    logout
  };

  return (
    <div className="App">
      <AuthContext.Provider value={auth}>
      <BrowserRouter>
        <Navbar/>
          <Switch>
            <Route path="/home">
              <Home/>
            </Route>
            <Route path="/contact">
              <Contact/>
            </Route>
            <Route path="/login">
              <Login/>
            </Route>
            <Route path="/signup">
              <Signup/>
            </Route>
            <Route path="/addtransaction">
              <AddTransaction/>
            </Route>
            <Route path="/createbudget">
              <CreateBudget/>
            </Route>
          


          </Switch>
      
      </BrowserRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
