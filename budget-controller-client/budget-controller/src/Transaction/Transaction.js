
import {useState, useContext} from 'react';
import {Link} from 'react-router-dom';
import AuthContext from '../AuthContext';
import Directories from '../Directories';

function Transaction(props){

    const auth = useContext(AuthContext);

    function deleteTransaction(){
        console.log(props);
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