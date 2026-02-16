import React, { useState } from 'react'
import Layout from '../components/Layout';

const CategoryPage = () => {
    const [message, setMessage] = useState("");
    const [CategoryName, setCategoryName] = useState("");
    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }
    
  return (
    <Layout>
        {message && <p className='message'>{message}</p>}
    </Layout>
  )
}

export default CategoryPage