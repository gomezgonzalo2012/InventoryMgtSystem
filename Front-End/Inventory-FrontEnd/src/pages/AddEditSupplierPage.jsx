import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout'
import { useNavigate, useParams } from 'react-router-dom';
import { createSupplier, getSupplierbyId, updateSupplier } from '../service/ApiService';

const EditSupplierPage = () => {
    const [message, setMessage] = useState("");
    const [name, setName] = useState("");
    const [contactInfo, setContactInfo] = useState("");
    const [address, setAddress] = useState("");
    const [isEditing, setIsEditing] = useState(false);

    const navigate = useNavigate();

    const { supplierId } = useParams("");

    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }
    useEffect(() => {
        if (supplierId) {

            const fetchSupplier = async () => {
                setIsEditing(true);
                try {
                    const response = await getSupplierbyId(supplierId);
                    console.log(response)
                    if (response.status === 200) {
                        const { supplier } = response;
                        console.log(supplier)
                        setAddress(supplier.address);
                        setContactInfo(supplier.contactInfo);
                        setName(supplier.name);
                    }
                } catch (error) {
                    showMessage(error.response?.data?.message || "Error : " + error);
                }
            }
            fetchSupplier();

        }
    }, [supplierId])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const supplierData = { name, contactInfo, address }
        try {
            if (isEditing) {
                const response = await updateSupplier(supplierId, supplierData);
                showMessage(response.message);
                setTimeout(() => {
                    navigate("/supplier")
                }, 2000)
            } else {
                const response = await createSupplier(supplierData);
                showMessage(response.message);
                setTimeout(() => {
                    navigate("/supplier")
                }, 2000)
            }
        } catch (error) {
            showMessage(error.response?.data?.message || "Error : " + error);
            console.log(error);

        }
    }

    return (
        <Layout>
            {message && <p className='message'>{message}</p>}
            <div className="supplier-form-page">
                <h1>{isEditing ? "Edit Supplier" : "Add Supplier"}</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label> Name</label>
                        <input type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Contact Info</label>
                        <input type="text"
                            value={contactInfo}
                            onChange={(e) => setContactInfo(e.target.value)}
                            required />
                    </div>
                    <div className="form-group">
                        <label> Address</label>
                        <input type="text"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            required />
                    </div>
                    <button type='submit'>{isEditing ? "Edit Supplier" : "Add Supplier"}</button>
                </form>
            </div>

        </Layout>
    )
}

export default EditSupplierPage