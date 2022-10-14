
import {useState, useContext} from 'react';
import {Link} from 'react-router-dom';
import AuthContext from '../AuthContext';


function Transaction(props){

    const auth = useContext(AuthContext);

    function deleteTransaction(){}


    return(

        <tr>
            <th scope="row"> {props.transactionAmount} </th>
            <td> {props.transactionCategory} </td>
            <td> {props.transactionDescription} </td>
            {auth.user.userRoles[0].roleName === "Admin" ? <td>{props.username}</td>: null}
            <td><button className='btn btn primary' > Edit </button>
            <button className='btn btn danger' > Delete </button></td>
        </tr>


    )



} export default Transaction;