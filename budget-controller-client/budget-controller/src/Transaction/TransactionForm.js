
import FormInput from "../FormInput"

import {useState,useContext, useEffect} from 'react';
import AuthContext from "../AuthContext";




function TransactionForm({onTransactionChange}){
    const auth = useContext(AuthContext);
    const [transaction, setTransaction] = useState({username: auth.user.username,  transactionName: "", transactionAmount: 0, categoryId: 1, transacationDescription : ""});
    const [budgetCategories, setBudgetCategories] = useState();
    

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const transactionCopy = {...transaction};
    
        transactionCopy[propertyName] = newValue;
        

        
        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);
        console.log(transaction)
    }
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal/" + auth.user.username, {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(response => {
            if (response.status === 200) {
             
                return response.json();
            } else {
                
                console.log(response);
            }
        })
        .then(budget => {
            const categories = budget.categories;
            setBudgetCategories(categories);
           
            
            
        })
        .catch(error => {
            console.log(error);
        });
    }, [])

    


    return(
        <div className="container">
            <FormInput
                inputType={"text"} 
                identifier={"transactionName"} 
                labelText={"Name"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"number"} 
                identifier={"transactionAmount"} 
                labelText={"Money Spent"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <select
                name="categoryId"
                id="categoryId"
                onChange={inputChangeHandler}
                className="form-control"
                defaultValue={budgetCategories ? budgetCategories[0].categoryId : ""}
                > 
                
                {budgetCategories ? budgetCategories.map(b => <option key={b.categoryId} value={b.categoryId}> {b.categoryName}</option>) : null}</select>
            <FormInput 
                inputType={"text"} 
                identifier={"transacationDescription"} 
                labelText={"Description of Transaction"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
        </div>
        )
}
export default TransactionForm