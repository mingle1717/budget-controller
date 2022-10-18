
import FormInput from "../FormInput";
import { useState } from "react";
import "./Contact.css"
function Contact(){


    const [contact, setContact] = useState();

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const contactCopy = {...contact};
    
        contactCopy[propertyName] = newValue;
    
        setContact(contactCopy);
    
    }

    return(
    <div className="container">
       <h1 className="contactHeader"> Let us know what we can do to help you! Ryan is a great guy and would love to fix any problem you may have or even lend you any money you may need!!</h1>
            <form className="contactForm">
        
                <FormInput 
                            inputType={"text"} 
                            identifier={"username"} 
                            labelText={"Username"} 
                            currVal={""} 
                            labelClass={"usernameLabel"}
                            onChangeHandler={inputChangeHandler}  
                            className={"form-control"}/>
                <FormInput 
                            inputType={"text"} 
                            identifier={"email"} 
                            labelText={"Email"} 
                            currVal={""} 
                            labelClass={"emailLabel"}
                            onChangeHandler={inputChangeHandler}  
                            className={"form-control"}/>
                <FormInput 
                            inputType={"text"} 
                            identifier={"issue"} 
                            labelText={"Description of issue"} 
                            currVal={""} 
                            labelClass={"issueLabel"}
                            onChangeHandler={inputChangeHandler}  
                            className={"form-control"}/>
                <button type="submit" className=" submitButton contactButton">Submit</button>
            </form>
    </div>
    )
} 
export default Contact;