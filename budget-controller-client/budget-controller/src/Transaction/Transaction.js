
import { useContext, useState} from 'react';
import {Link} from 'react-router-dom';
import AuthContext from '../AuthContext';
import Directories from '../Directories';

function Transaction(props){

    const auth = useContext(AuthContext);
    const [errors, setErrors] = useState([]);

    function deleteTransaction(){
        if( window.confirm("Are you sure you want to delete transaction" + props.transactionName + "?")){
            fetch("http://localhost:8080/api/transaction/" + props.transactionId, {
                headers: {
                    Authorization: `Bearer ` + auth.user.token,
                },
                method: "DELETE"
            })
            .then(async response => {
                if( response.status ===204){
                    props.onTransactionDelete();
                } else {
                    return Promise.reject(await response.json())
                }
            })
            .catch (errorList => {
                if( errorList instanceof TypeError){
                    setErrors(["Could not connect to api"]);
                } else {
                    setErrors( errorList + " ");
                }
            })
        }
    }


    return(
       

        <tr>
            
            <th scope="row" > {props.transactionName}</th>
                <td> ${props.transactionAmount}  </td>
                <td> {props.transactionCategory} </td>
                <td> {props.transacationDescription} </td>
                {auth.user.userRoles[0].roleName === "Admin" ? <td>{props.username}</td>: null}
                <td><Link to={Directories.EDITTRANSACTION + "/" + props.transactionId} className='tranSubmitButton' > Edit </Link>
                <button onClick={deleteTransaction}className='tranCancelButton' > Delete </button></td>
        </tr>
    )
} export default Transaction;