
import {useState} from 'react';
import CategoryForm from './CategoryForm';

function AddCategoryContainer(){
    const startingCategories = [{categoryId : 0, categoryName : "Savings", categoryPercent : 100, higherLimit : 1000, lowerLimit : 500, goal : false }];
    const [categories, setCategories] = useState([startingCategories]);

    function categoryChangeHandler(category){


        const categoriesCopy = categories.map(c => c.categoryId === category.categoryId ? category : c );

        setCategories(categoriesCopy);

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
        <div className='categoryContainer'>
            {categories.map( c => <CategoryForm category={c}  onCategoryChange={categoryChangeHandler} />)}
            <button className="btn btn-primary" onClick={addCategory}  > + </button>
            {}
            <button className="btn btn-danger" onClick={removeCategory} > - </button>
        </div>

    )

    }
export default AddCategoryContainer;