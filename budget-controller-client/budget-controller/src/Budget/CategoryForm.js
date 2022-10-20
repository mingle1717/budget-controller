import {useState} from "react";
import FormInput from "../FormInput";
import "./Budget.css"
function CategoryForm({ onCategoryChange , category}){

    
    function inputChangeHandler(inputChangeEvent){

        const propertyName = inputChangeEvent.target.name;
        let newValue = inputChangeEvent.target.value;

        if(inputChangeEvent.target.type === "checkbox"){
            newValue = inputChangeEvent.target.checked;
        }

        const categoryCopy = {...category};

        categoryCopy[propertyName] = newValue;

        onCategoryChange(categoryCopy)
        console.log(categoryCopy);
    }

  

    
    return(
        <div className="container ">
      
                <FormInput 
                    inputType={"text"} 
                    identifier={"categoryName"} 
                    labelText={"Category"} 
                    currVal={category.categoryName} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    <div id="categoryHelp" className="form-text">What would you like the category to be called?</div>
            
                
                <FormInput 
                    inputType={"number"} 
                    identifier={"categoryPercent"} 
                    labelText={"Category Percentage"} 
                    currVal={category.categoryPercent} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    <div id="categoryHelp" className="form-text">How much of your budget do you want this category to use?</div>
              
                 <FormInput 
                    inputType={"number"} 
                    identifier={"higherLimit"} 
                    labelText={"Upper Limit"} 
                    currVal={category.higherLimit} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler} 
                    className={"form-control"}/>
                    
             
                <FormInput 
                    inputType={"number"} 
                    identifier={"lowerLimit"} 
                    labelText={"Lower Limit"} 
                    currVal={category.lowerLimit} 
                    labelClass={"inputLabel"}
                    onChangeHandler={inputChangeHandler}
                    className={"form-control"}/>
                    
               
                {/* <FormInput 
                    inputType={"checkbox"} 
                    identifier={"goal"} 
                    labelText={"Financial Goal?"} 
                    currVal={category.goal} 
                    labelClass={"financialCheckboxLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"financialCheckbox"}/> */}
               
        
            </div>
    )
} export default CategoryForm;