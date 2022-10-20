
import {Link, useHistory} from 'react-router-dom';
import React, {useState, useContext} from 'react';
import jwtDecode from 'jwt-decode';
import './Authentication.css'
import AuthContext from '../AuthContext';
import Directories from '../Directories';

function Login(props){
    const [username,setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors,setErrors] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext);

    function handleSubmit(evt){

        evt.preventDefault();

        fetch("http://localhost:8080/api/security", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                username,
                password
            })
        })
        .then (async response => {
            if( response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(await response.json())
            }
        })
        .then( jwtContainer => {
            const jwt = jwtContainer.jwt_token;
            const claimsObject = jwtDecode(jwt);

            console.log( jwt );
            console.log(claimsObject);

               
            auth.login(jwt);
            history.push(Directories.MEMBERDASHBOARD);
        
        })
        .catch( error => {
            console.log( error );
        });
    }


    return(
    <div className="container">
        {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
      

        <form className="loginForm"onSubmit={handleSubmit}>
            <div className="form-group fieldContainer">

                <label htmlFor="username" className='usernameLabel'> Username</label>

                <input 
                onChange={(event) => setUsername(event.target.value)} 
                id="username" name="username" className="form-control usernameField"
                />

            </div>

            <div className="form-group fieldContainer">
                <label htmlFor="password" className='passwordLabel'> Password</label>
                <input 
                type ="password"
                onChange={(event) => setPassword(event.target.value)} 
                id="password" name="password" className="form-control passwordField"
                />
            </div>

            <div className='buttons'>
                <button type="button " className="submitButton">Submit</button>
                <Link to = {Directories.HOME} type="button " className="cancelButton">Cancel</Link>
            </div>

        </form>
        <div className='linkContainer'>
            <div>Not Signed Up?</div>
            <Link to={Directories.SIGNUP} className='container'>Click here!</Link>
        </div> 
        
    </div>
        )
}
export default Login