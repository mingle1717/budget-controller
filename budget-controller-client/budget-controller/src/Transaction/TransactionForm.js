
import FormInput from "../FormInput"

import {useState,useContext, useEffect} from 'react';
import AuthContext from "../AuthContext";




function TransactionForm({onTransactionChange, id, editedTransaction}){
    const auth = useContext(AuthContext);
    const [transaction, setTransaction] = useState();
    const [budgetCategories, setBudgetCategories] = useState();
    const [categoryId, setCategoryId] = useState()
    const [errors, setErrors] = useState([])

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
       
        if(editedTransaction !== undefined){
        const transactionCopy = {...editedTransaction};
      
        transactionCopy[propertyName] = newValue;
        

        
        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);
        
        }
        else{
            const transactionCopy = {...transaction};
        
        transactionCopy[propertyName] = newValue;
    
    

        
        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);

        }
        
    }
    function selectChangeHandler(event) {
        const newValue = event.target.value;
        if(editedTransaction !== undefined){
            const transactionCopy = {...editedTransaction};
          
            transactionCopy["category"] = {categoryId: newValue};
            
    
            
            setTransaction(transactionCopy);
            onTransactionChange(transactionCopy);
            
            }
        else{
        const transactionCopy = {...transaction};
        
        
        transactionCopy["category"] = {categoryId: newValue};
    
        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);

        }
    }
    
    useEffect(() => {
        
        fetch("http://localhost:8080/api/budget/personal" , {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(async response => {
            if (response.status === 200) {
             
                return response.json();
            } else {
                
                return Promise.reject(await response.json())
            }
        })
        .then(budget => {
            const categories = budget.categories;
            setBudgetCategories([...categories]);
            
           
            
            
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        });
    }, [])

    


    return(
        <div className="container">
            {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
                
            <FormInput
                inputType={"text"} 
                identifier={"transactionName"} 
                labelText={"Name"} 
                currVal={editedTransaction !== undefined? editedTransaction.transactionName : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"number"} 
                identifier={"transactionAmount"} 
                labelText={"Money Spent"} 
                currVal={editedTransaction !== undefined ? editedTransaction.transactionAmount : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <label htmlFor="categoryId"> Category </label>
            <select
                name="category.categoryId"
                id="category.categoryId"
                onChange={selectChangeHandler}
                className="form-control"
        
                > 
                <option value="none" selected disabled hidden>Select an Option</option>
                {budgetCategories ? budgetCategories.map(b => <option key={b.categoryId} value={b.categoryId}> {b.categoryName}</option>) : null}</select>
            <FormInput 
                inputType={"text"} 
                identifier={"transacationDescription"} 
                labelText={"Description of Transaction"} 
                currVal={editedTransaction !== undefined ? editedTransaction.transacationDescription : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
        </div>
        )
}
export default TransactionForm