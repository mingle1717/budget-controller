
import {Link, useHistory} from 'react-router-dom';
import React, {useState} from 'react';
import jwtDecode from 'jwt-decode';
import './Authentication.css'


function Login(props){
    const [username,setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors,setErrors] = useState("");

    const history = useHistory();

    function handleSubmit(e){
   


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
    .then (response => {
        if( response.status === 200) {
            return response.json();
        } else {
            console.log(response);
        }
    })
        .then( jwtContainer => {
            const jwt = jwtContainer.jwt_token;
            const claimsObject = jwtDecode(jwt);

            console.log( jwt );
            console.log(claimsObject);

            props.setLoginInfo({jwt, claims:claimsObject});
            history.push("/home");
        })
        .catch( error => {
            console.log( error );
        });
    }


    return(
    <div className="container">
        <h1 className="authHeader"> Login </h1>
        <form onSubmit={handleSubmit}>
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
            <div>
                <button type="button " className="btn btn-primary submitButton">Submit</button>
                <button type="button " className="btn btn-danger cancelButton">Cancel</button>
            </div>
            </form>
            <div className='linkContainer'>
            <div>Not Signed Up?</div>
            <Link to="/signup" className='container'>Click here!</Link>
            </div>


       
        
    </div>
        )
}
export default Login