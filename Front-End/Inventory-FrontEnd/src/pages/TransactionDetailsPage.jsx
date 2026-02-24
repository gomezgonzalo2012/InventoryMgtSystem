import React, { useEffect, useState } from 'react';
import Layout from '../components/Layout'
import { useParams } from 'react-router-dom';
import { getTransactionId, updateTransactionStatus } from '../service/ApiService';

const TransactionDetailsPage = () => {
  const [message, setMessage] = useState("");
  const [transaction, setTransaction] = useState("");
  const [status, setStatus] = useState("");
  const {transactionId} = useParams();


  //Method to show message or errors
  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 4000);
  };

  useEffect(()=>{
    const fetchTransaction = async ()=>{
      console.log(transactionId);
      if(transactionId){
        try {
          const response = await getTransactionId(transactionId);
          console.log(response);
          
          if(response.status == 200 ){
            setTransaction(response.transaction);
            setStatus(response.transaction.status); // so it is shown in <select>
          }
        } catch (error) {
          showMessage(error.response?.data?.message || "Error : " + error);
        }
      }
    }
    fetchTransaction();
  }, [transactionId]);

  const handleUpdateStatus = async ()=>{
    try {
      const response = await updateTransactionStatus(transactionId, status);
      console.log(response);
      
      if (response.status === 200) {
        showMessage(response.message);
      }
    } catch (error) {
      showMessage(error.response?.data?.message || "Error : " + error);
    }
  }

  return (
    <Layout>
      {message &&<p className='message'>{message} </p>}
      <div className="transaction-details-page">
        {transaction && (
        <>
            <div className="section-card">
              <h2>Transaction Information</h2>
              <p>Type: {transaction.transactionType}</p>
              <p>Status: {transaction.status}</p>
              <p>Description: {transaction.description}</p>
              <p>Note: {transaction.note}</p>
              <p>Total Products: {transaction.totalProducts}</p>
              <p>Total Price: {transaction.totalPrice.toFixed(2)}</p>
              <p>Create At: {new Date(transaction.createdAt).toLocaleString()}</p>

              {transaction.updatedAt && (
                <p>Updated At: {new Date(transaction.updatedAt).toLocaleString()}</p>
              )}
            </div>
            <div className="section-card">
              <h2>Product Information</h2>
              <p>Name: {transaction.product.name}</p>
              <p>Description: {transaction.product.description}</p>
              <p>Sku: {transaction.product.sku}</p>
              <p>Total Price: {transaction.product.price.toFixed(2)}</p>
              <p>Stock quantity: {transaction.product.stockQuantity}</p>
              {transaction.product.imgUrl && (
                <img src={transaction.product.imgUrl}></img>
              )}
            </div>

            <div className="section-card">
              <h2>User Information</h2>
              <p>Name: {transaction.user.name}</p>
              <p>Email: {transaction.user.email}</p>
              <p>Phone number: {transaction.user.phoneNumber}</p>
              <p>Role: {transaction.user.role}</p>     
              <p>Create AT: {new Date(transaction.createdAt).toLocaleString()}</p>
            </div>

            {transaction.supplier && (
              <div className="section-card">
                <h2>Supplier Information</h2>
                <p>Name: {transaction.supplier.name}</p>
                <p>Contact Address: {transaction.supplier.contactInfo}</p>
                <p>Address: {transaction.supplier.address}</p>
              </div>
            )}
            <div className="section-card transaction-status-update">
              <label>Status</label>
              <select 
              value={status}
              onChange={(e=> setStatus(e.target.value))}>
                <option value="PENDING">PENDING</option>
                <option value="PROCESSING">PROCESSING</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="CANCELED">CANCELLED</option>
              </select>
              <button onClick={handleUpdateStatus}>Update status</button>
            </div>
        </>  
        )}
      </div>
      
      </Layout>
  )
}
export default TransactionDetailsPage