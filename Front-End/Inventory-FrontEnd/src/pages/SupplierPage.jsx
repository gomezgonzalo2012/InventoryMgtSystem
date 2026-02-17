import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout'
import { useNavigate } from 'react-router-dom'
import { deleteSupplier, getAllSuppliers } from '../service/ApiService'

const SupplierPage = () => {
    const [message, setMessage] = useState("");
    const [suppliers, setSuppliers] = useState([]);
    const navigate = useNavigate();

    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }
    useEffect(()=>{
        const getSuppliers = async ()=>{
            try {
                const response = await getAllSuppliers();
                if(response.status === 200){
                    setSuppliers(response.suppliers);
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Login error : " + error);
            }
        }
        getSuppliers();
    }, [])
    
    const handleDeleteSupplier = async (id)=>{
         if(window.confirm("Are you sure you want to delete this Supplier?")){
                    try {
                        const response = await deleteSupplier(id);
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
        {message && <p className='message'> {message} </p>}
        <div className="supplier-page">
            <div className="supplier-header">
                <h1>Suppliers</h1>
                <div className="add-sup">
                    <button onClick={()=> navigate("/add-supplier")}> Add Supplier</button>
                </div>
            </div>
        </div>
        {suppliers && (
            <ul className='supplier-list'>
                {suppliers.map(sup=>(
                    <li className='supplier-item' key={sup.id}> 
                        <span>{sup.name}</span>
                        <div className='supplier-actions'>
                            <button onClick={()=> navigate(`/edit-supplier/${sup.id}`)}>Edit</button>
                            <button onClick={()=> handleDeleteSupplier(sup.id)}>Delete</button>
                        </div>
                    </li>
                ))}
                
            </ul>
        )}
    </Layout>
  )
}

export default SupplierPage