import { useState } from 'react';
import {Link, useHistory} from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import './Authentication.css'
function Signup(){

    const [username,setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [errors,setErrors] = useState("");

    const history = useHistory();

    const handleSubmit = async (event) =>{
        event.preventDefualt();
        const response = await fetch("http://localhost:8080/authenticate", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
            },
            body: JSON.stringify({
                username,
                password,
                email
            }),
        });

        if(response.status ===200) {
            const {jwt_token} = await response.json();
            console.log(jwt_token);
            history.push("/home");
        } else if (response.status === 403) {
            setErrors(["Login failed."]);
        } else {
            setErrors(["Unknown error."]);
        }
    };

    return(
        <div className="container">
            <h1 className="authHeader">Signup</h1>
            <form>
            <div className="form-group fieldContainer">
                <label htmlFor="username" className="usernameLabel"> Username</label>
                <input 
                onChange={(event) => setUsername(event.target.value)} 
                id="username" name="username" className="form-control usernameField"
                />
                
            </div>
            <div className="form-group fieldContainer">
                <label className="passwordLabel" htmlFor="password"> Password</label>
                <input 
                type ="password"
                onChange={(event) => setPassword(event.target.value)} 
                id="password" name="password" className="form-control passwordField"/>
            </div>
            <div className="form-group fieldContainer">
                <label className="emailLabel" htmlFor="email"> Email</label>
                <input 
                onChange={(event) => setEmail(event.target.value)} 
                id="email" name="email" className="form-control emailField"/>
            </div>

            <div>
                <button type="button" className="btn btn-primary submitButton">Submit</button>
                <button type="button" className="btn btn-danger cancelButton">Cancel</button>
            </div>
            </form>
            <div className="linkContainer">
            <div>Already signed in?</div>
            <Link to="/login" className='container'>Click here!</Link>
            </div>

        
        </div>
        )
} 
export default Signup;