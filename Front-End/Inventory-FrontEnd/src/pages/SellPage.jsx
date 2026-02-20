import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout';
import { getAllProducts, sellProducts } from '../service/ApiService';

const SellPage = () => {
    const [message, setMessage] = useState("");
    const [productId, setProductId] = useState("");
    const [products, setProducts] = useState([]);
    const [description, setDescription] = useState("");
    const [note, setNote] = useState("");
    const [quantity, setQuantity] = useState("");

    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await getAllProducts();
                if (response.status === 200) {
                    setProducts(response.products)
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);
            }
        }
        fetchProducts();
    }, [])

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (productId && quantity) {

            try {
                const purchaseData = {
                    productId: productId,
                    note: note,
                    description: description,
                    quantity: parseInt(quantity)
                }
                const response = await sellProducts(purchaseData)
                if (response.status === 200) {
                    showMessage(response.message)
                    eraseFormData();
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);

            }
        } else {
            setMessage("Please fill in all required fields")
        }
    }
    const eraseFormData = () => {
        setDescription("");
        setProductId("");
        setNote("");
        setQuantity("");

    }

    return (
        <Layout>
            {message && <div className="message">{message}</div>}
            <div className="purchase-form-page">
                <h1>Sell Inventory</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label> Select product</label>
                        <select value={productId}
                            onChange={(e) => setProductId(e.target.value)}
                            required>
                            <option value="">Select a product</option>
                            {products.map(prod => (
                                <option key={prod.id} value={prod.id}>{prod.name}</option>

                            ))}

                        </select>
                    </div>
                    <div className="form-group">
                        <label> Description</label>
                        <input type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Note</label>
                        <input type="text"
                            value={note}
                            onChange={(e) => setNote(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Quantity</label>
                        <input type="number"
                            value={quantity}
                            onChange={(e) => setQuantity(e.target.value)}
                            required />
                    </div>
                    <button type='submin'> Confirm</button>
                </form>
            </div>
        </Layout>
    )
}

export default SellPage;