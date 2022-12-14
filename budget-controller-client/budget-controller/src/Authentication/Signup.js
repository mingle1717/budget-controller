import { useContext, useState } from 'react';
import {Link, useHistory} from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import AuthContext from '../AuthContext';
import './Authentication.css'
import Directories from '../Directories';
function Signup(){

    const [username,setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [errors,setErrors] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext);


    function handleSubmit (event){
        event.preventDefault();
        console.log(username, password, email);
        fetch("http://localhost:8080/api/security/register", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                
            },
            body: JSON.stringify({
                username,
                password,
                email
            }),
        })
        .then(async response => {


            if(response.status ===201) {
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
                .then( loginResponse => {
                    if( loginResponse.status === 200){
                        return loginResponse.json();
                    } else {
                        console.log( "failed at login" + loginResponse);
                    }
                })
                .then(jwtContainer => {
                    const jwt = jwtContainer.jwt_token;
                    const claimsObject = jwtDecode(jwt);

                    console.log( jwt );
                    console.log(claimsObject);

                    auth.login(jwt);
                    history.push(Directories.MEMBERDASHBOARD);
                })
            .catch(loginError => {
                console.log( "failed at login " + loginError );
            });
                history.push(Directories.MEMBERDASHBOARD)
             return response.json();
        }
            
         return Promise.reject(await response.json());
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    };

    return(
        <div className="container">
            {errors.length > 0?
            <div className=" myText signUpError" id="messages"> Attention: {errors}</div>
                : null}
            <form className = "loginForm" onSubmit={handleSubmit}>
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

            <div className='buttons'>
                <button type="submit"  className="btn btn-primary submitButton">Submit</button>
                <Link to = {Directories.HOME} type="button " className="btn btn-danger cancelButton">Cancel</Link>
            </div>
            </form>
            <div className="linkContainer">
            <div>Already signed in?</div>
            <Link to={Directories.LOGIN} className='container'>Click here!</Link>
            </div>

        
        </div>
        )
} 
export default Signup;