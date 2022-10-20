
import FormInput from "../FormInput"

import {useState,useContext, useEffect} from 'react';
import AuthContext from "../AuthContext";




function TransactionForm({onTransactionChange, id, editedTransaction}){
    const auth = useContext(AuthContext);
    const [transaction, setTransaction] = useState({transactionId : id, });
    const [budgetCategories, setBudgetCategories] = useState();
    const [categoryId, setCategoryId] = useState({categoryId : 1});
    const [errors, setErrors] = useState([])

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const transactionCopy = {...transaction};
    
        transactionCopy[propertyName] = newValue;
        transactionCopy["category"] = {categoryId};

        
        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);
        console.log(editedTransaction)
        console.log(transactionCopy)
        console.log(categoryId)
    }
    
    useEffect(() => {
        console.log(editedTransaction)
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
                currVal={editedTransaction ? editedTransaction.transactionName : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"number"} 
                identifier={"transactionAmount"} 
                labelText={"Money Spent"} 
                currVal={editedTransaction ? editedTransaction.transactionAmount : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <label> </label>
            <select
                name="categoryId"
                id="categoryId"
                onChange={(event) => setCategoryId(event.target.value)}
                className="form-control"
                
                > 
                
                {budgetCategories ? budgetCategories.map(b => <option key={b.categoryId} value={b.categoryId}> {b.categoryName}</option>) : null}</select>
            <FormInput 
                inputType={"text"} 
                identifier={"transacationDescription"} 
                labelText={"Description of Transaction"} 
                currVal={editedTransaction ? editedTransaction.transacationDescription : null}
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
        </div>
        )
}
export default TransactionForm