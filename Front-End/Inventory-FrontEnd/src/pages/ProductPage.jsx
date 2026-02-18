import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout'
import { useNavigate } from 'react-router-dom';
import { deleteProduct, getAllProducts } from '../service/ApiService';
import Pagination from '../components/Pagination.jsx'

const ProductPage = () => {
    const [message, setMessage] = useState("");
    const [products, setProducts] = useState([]);

    const navigate = useNavigate();

    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 1;


    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }

    useEffect(()=>{
        const fetchProducts= async ()=>{
            try {
                const response = await getAllProducts();
                if(response.status ===200){
                    // for pagination
                    setTotalPages(Math.ceil(response.products.length/itemsPerPage));

                    setProducts(response.products.slice(
                        (currentPage - 1) * itemsPerPage,
                        currentPage * itemsPerPage
                    ));
                    console.log(response.products);
                    
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);
            }
        }
        fetchProducts()
    }, [currentPage])

    const handleDeleteProduct = async (productId)=>{
        if (window.confirm("Are you sure you want to delete this Product?")) 
        try {
            const response = await deleteProduct(productId);
            if (response.status === 200) {
                setProducts(response.products);
                console.log(response.products);
                window.location.reload(); //relode page
            }
        } catch (error) {
            showMessage(error.response?.data?.message || "Error : " + error);
        }
    }

   
  return (
    <Layout>
        {message && <div className='message'>{message}</div>}
        <div className="product-page">
            <div className="product-header">
                <h1>Products</h1>
                <button className='add-product-btn'
                onClick={()=> navigate("/add-product")}>
                    Add Product
                </button>
            </div>
           {products && (
            <div className="product-list">
                {products.map((product)=>(
                    <div key={product.id} className="product-item">
                        <img className="product-image" 
                        src={product.imgUrl} 
                        alt={product.name} />

                        <div className="product-info">
                            <h3 className="name">{product.name}</h3>
                            <p className="sku">Sku: {product.sku}</p>
                            <p className="price">Price: {product.price}</p>
                            <p className="quantity">Quantity: {product.stockQuantity}</p>
                        </div>
                        <div className='product-actions'>
                            <button className="delete-btn" onClick={()=>handleDeleteProduct(product.id)}>Delete</button>
                            <button className="edit-btn" onClick={()=> navigate(`/update-product/${product.id}`)}>Update</button>
                        </div>
                    </div>


                ))}
            </div>
           )}
        </div>
        
        <Pagination
            currentPage = {currentPage}
            totalPages ={totalPages}
            onPageChange ={setCurrentPage}
        />
    </Layout>
  );
}

export default ProductPage