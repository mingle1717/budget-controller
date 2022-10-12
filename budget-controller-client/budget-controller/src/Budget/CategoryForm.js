import {useState} from "react";
import FormInput from "../FormInput";
import "./Budget.css"
function CategoryForm(){

    const [category, setCategory] = useState({categoryName : "" , categoryPercent : 0, higherLimit : 0 , lowerLimit : 0, goal : false})
    const [categories, setCategories] = useState([]);
    const [addedForm,setAddedForm] = useState(false);
    const [addButton, setAddButton] = useState(true);
    const [removeButton, setRemoveButton] = useState(true);

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const categoryCopy = {...category};

        categoryCopy[propertyName] = newValue;

        setCategory(categoryCopy);
    }




    function showForm(){
        setAddedForm(true);
        setAddButton(false);
        setRemoveButton(true);
       
    }

    function hideForm(){
        setAddedForm(false);
        setAddButton(true);
        setRemoveButton(false);
    }
    return(
        <div className="container">
      
                <FormInput 
                    inputType={"text"} 
                    identifier={"category"} 
                    labelText={"Category"} 
                    currVal={""} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    <div id="categoryHelp" className="form-text">What balance do you want to start with?</div>
            
                
                <FormInput 
                    inputType={"text"} 
                    identifier={"categoryPercentage"} 
                    labelText={"Category Percentage"} 
                    currVal={""} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    <div id="categoryHelp" className="form-text">How much of your budget do you want this category to use?</div>
              
                <FormInput 
                    inputType={"text"} 
                    identifier={"higherLimit"} 
                    labelText={"Higher Limit"} 
                    currVal={""} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler} 
                    className={"form-control"}/>
                    <div id="higherLimitHelp" className="form-text">We will alert you if you have more money than you expected!</div>
             
                <FormInput 
                    inputType={"text"} 
                    identifier={"lowerLimit"} 
                    labelText={"Lower Limit"} 
                    currVal={""} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    <div id="lowerLimitHelp" className="form-text">We will alert you if you go lower than this!</div>
               
                <FormInput 
                    inputType={"checkbox"} 
                    identifier={"financialGoal"} 
                    labelText={"Financial Goal?"} 
                    currVal={""} 
                    labelClass={"financialCheckbox"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-check"}/>
               {
               addedForm === true ? 
               <div>
                <CategoryForm/>
                {removeButton === true ? <button onClick = {hideForm} type="button" className="btn btn-primary addButton">-</button> : null} 
                </div> : 
                null
                }
                
                {addButton === true ? <button onClick = {showForm} type="button" className="btn btn-primary addButton">+</button> : null}
                
        
            </div>
    )
} export default CategoryForm;