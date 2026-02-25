import React from 'react'
import { Link } from 'react-router-dom'
import { logout, isAdmin, isAuthenticated } from '../service/ApiService'
const logUserOut = () => {
    logout();
}
const isAuth = await isAuthenticated();
const isAdm = await isAdmin();

const Sidebar =  () => {
    return (
        <div className='sidebar'>
            <h1 className='ims'> IMS </h1>
            <ul className='nav-links'>
                {isAdm && (
                    <>
                        <li><Link to="/category">Category</Link></li>
                        <li><Link to="/product">Product</Link></li>
                        <li><Link to="/supplier">Supplier</Link></li>
                    </>
                )}
                {isAuth && (
                    <>
                        <li><Link to="/dashboard">Dashboard</Link></li>
                        <li><Link to="/transaction">Transactions</Link></li>
                        <li><Link to="/purchase">Purchase</Link></li>
                        <li><Link to="/sell">Sell</Link></li>
                        <li><Link to="/profile">Profile</Link></li>
                        <li><Link onClick={logUserOut} to="/login">Logout</Link></li>
                    </>
                )}

                
            </ul>
        </div>
    )
}

export default Sidebar
