function FormInput(props){

    return (
        <div >
            <label className={props.labelClass} htmlFor={props.identifier}>{props.labelText}</label>
            <input 
                type={props.inputType} 
                id={props.identifier} 
                name={props.identifier} 
                defaultValue={props.currVal}
                onChange={props.onChangeHandler}
                className={props.className}
                />
        </div>
    );
}

export default FormInput;