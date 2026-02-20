import React, { useEffect, useState, } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { createProduct, getAllCategories, getProductbyId, updateProduct } from '../service/ApiService';
import Layout from '../components/Layout';

const AddProductPage = () => {
    const [message, setMessage] = useState("");
    const [imageFile, setimageFile] = useState(null);
    const [name, setName] = useState("");
    const [sku, setSku] = useState("");
    const [stockQuantity, setStockQuantity] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    const [categories, setCategories] = useState([]);
    const [categoryId, setCategoryId] = useState("");
    const [imgUrl, setImgUrl] = useState("");
    // edit
    const { productId } = useParams("");

    const [isEditing, setIsEditing] = useState(false);

    const navigate = useNavigate();
    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }

    useEffect(() => {
        if (productId) {
            const fetchProduct = async () => {

                setIsEditing(true);
                try {
                    const response = await getProductbyId(productId);
                    if (response.status === 200) {                        
                        const {product} = response;
                        console.log(product);

                        setName(product.name);
                        setDescription(product.description);
                        setPrice(product.price);
                        setStockQuantity(product.stockQuantity);
                        setSku(product.sku);
                        setCategoryId(product.categoryId);
                        setImgUrl(product.imgUrl)
                    }
                } catch (error) {
                    showMessage(error.response?.data?.message || "Error : " + error);
                }
            }
            fetchProduct();
        }
        const fetchCategories = async () => {            
            try {
                const response = await getAllCategories();
                console.log(response);
                if (response.status === 200) {
                    setCategories(response.categories)
                }

            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);
            }
        }
        fetchCategories();
    }, [productId])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("name", name);
        formData.append("sku", sku);
        formData.append("price", price);
        formData.append("stockQuantity", stockQuantity);
        formData.append("description", description);
        formData.append("categoryId", categoryId);
        
        if (imageFile) {
            formData.append("imageFile", imageFile);
        }

        try {
            if (isEditing) {
                formData.append("productId", productId);
                // requires the product id
                const response = await updateProduct(formData);
                console.log(response);

                if (response.status === 200 || response.status === 204) {
                    showMessage(response.message);
                    setTimeout(() => {
                        navigate("/product")
                    }, 2000)
                }
            } else {
                const response = await createProduct(formData);
                console.log(response);

                if (response.status === 200 || response.status === 204) {
                    showMessage(response.message);
                    setTimeout(() => {
                        navigate("/product")
                    }, 2000)
                }
            }

        } catch (error) {
            showMessage(error.response?.data?.message || "Error : " + error);
            console.log(error);
        }
    }

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setimageFile(file);
        const reader = new FileReader();
        // to preview the image
        reader.onloadend = () => setImgUrl(reader.result);
        reader.readAsDataURL(file);
    }

    return (
        <Layout>
            {message && <p className='message'>{message}</p>}
            <div className="product-form-page">
                <h1>{isEditing ? "Edit Product" : "Add product"}</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label> Name</label>
                        <input type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Sku</label>
                        <input type="text"
                            value={sku}
                            onChange={(e) => setSku(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Stock </label>
                        <input type="number"
                            value={stockQuantity}
                            min={1}
                            onChange={(e) => setStockQuantity(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Price</label>
                        <input type="number"
                            value={price}
                            min={1}
                            onChange={(e) => setPrice(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}></textarea>
                    </div>

                    <div className='form-group'>
                        <label > Categorias</label>
                        <select value={categoryId}
                            onChange={(e) => setCategoryId(e.target.value)}
                            required>
                            <option value="">Select a Category</option>

                            {categories.map(cat => (
                                <option key={cat.id} value={cat.id}>
                                    {cat.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group">
                        <label> Image file</label>
                        <input type="file" onChange={handleImageChange} />
                        {imgUrl && (
                            <img src={imgUrl} alt="preview" className='image-previes' />
                        )}
                    </div>

                    <button type='submit'>{isEditing ? "Edit Product" : "Add Product"}</button>
                </form>
            </div>
        </Layout>
    )
}

export default AddProductPage