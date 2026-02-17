import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout';
import {getAllCategories, createCategory, updateCategory, deleteCategory} from '../service/ApiService';

const CategoryPage = () => {
    const [categories, setCategories] = useState([])
    const [message, setMessage] = useState("");
    const [categoryName, setCategoryName] = useState("");
    const [isEditing, setIsEditing] = useState(false);
    const [editingCatId, setEditingCatId] = useState("")

    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }
    useEffect(()=>{
        const getCategories = async ()=>{
            try{
                const response = await getAllCategories();
                console.log(response);
                if(response.status === 200){
                    setCategories(response.categories)
                }

            }catch(error){
                showMessage(error.response?.data?.message || "Login error : " + error);
            }
        }
        getCategories();
    },[])

    const addCategory =async ()=>{
        if(!categoryName){
            showMessage("Category name is required")
            return;
        }
        try {
            const response = await createCategory({name:categoryName});
            if (response.status === 200 || response.status === 204) {
                showMessage(response.message)
                setCategoryName("");
                window.location.reload();
            }
        } catch (error) {
            showMessage(error.response?.data?.message || "Login error : " + error);
        }
    }
    // Edit related methods
    const editCategory = async () => {

        try {
            const response = await updateCategory(editingCatId, { name: categoryName });
            if (response.status === 200) {
                showMessage(response.message)
                setIsEditing(false);
                setCategoryName("");
                window.location.reload();
            }
        } catch (error) {
            showMessage(error.response?.data?.message || "Login error : " + error);
        }
    }
    const handleEditCategory =(category) => {
        setIsEditing(true);
        setCategoryName(category.name);
        setEditingCatId(category.id);
    }
    const handleDeleteCategory = async(id) => {
        if(window.confirm("Are you sure you want to delete this category?")){
            try {
                const response = await deleteCategory(id);
                if (response.status === 200) {
                    showMessage(response.message)
                    window.location.reload();
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Login error : " + error);
            }
        }
    }
  return (
    <Layout>
        {message && <p className='message'>{message}</p>}
        <div className="category-page">
            <div className="category-header">
                <h1>Categories</h1>
                <div className="add-cat">
                <input 
                type="text" 
                placeholder="Category name" 
                value={categoryName}
                onChange={(e)=>{setCategoryName(e.target.value)}}
                />
                {!isEditing ? (
                    <button onClick={addCategory}>Add Category</button>
                ):(
                    <button onClick={editCategory}>Edit Category</button>
                )}
                </div>
            </div>
                {categories && (
                    <ul className='category-list'>
                        {categories.map((category)=>(
                            <li className="category-item" key={category.id}>
                                <span>{category.name}</span>
                                <div className='category-actions'>
                                    <button onClick={()=>handleEditCategory(category)} >
                                        Edit
                                    </button>
                                    <button onClick={()=>handleDeleteCategory(category.id)} >
                                        Delete
                                    </button>
                                </div>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        
    </Layout>
  )
}

export default CategoryPage