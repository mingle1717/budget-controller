
import {useState} from 'react';
import CategoryForm from './CategoryForm';
import "./Budget.css"

function AddCategoryContainer({onCategoriesChange}){
    const startingCategories = [{categoryId : 0, categoryName : "Savings", categoryPercent : 100, higherLimit : 1000, lowerLimit : 500, goal : false }];
    const [categories, setCategories] = useState([startingCategories]);

    function categoryChangeHandler(category){

        const categoriesCopy = categories.map(c => c.categoryId === category.categoryId ? category : c );

        setCategories(categoriesCopy);
        onCategoriesChange(categories);

    }

    function addCategory(){
        const categoriesCopy = [...categories];
        categoriesCopy.push({categoryId : categoriesCopy.length , categoryName : "" , categoryPercent : 0, higherLimit : 100, lowerLimit : 0, goal : false});
        setCategories(categoriesCopy);
    }


    function removeCategory(){
        if(categories.length > 1){
            const categoriesCopy = [...categories];
            categoriesCopy.pop();
            setCategories(categoriesCopy);
        }
    }



    return(
        <div className='categoryContainer' key='editBudget'>
            {categories.map( c => <CategoryForm onCategoryChange={categoryChangeHandler} category={c}  key={c.categoryId}  />)}

            <div className='budgetButtons'>
                <button  type="button" className="budgetSubmitButton" onClick={addCategory}  > + </button>
                <button  type="button" className="budgetCancelButton" onClick={removeCategory} > - </button>
            </div>
        </div>
        )
    }
export default AddCategoryContainer;