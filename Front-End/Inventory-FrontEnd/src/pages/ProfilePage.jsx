import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout'
import { getLoggedInUser } from '../service/ApiService';

export const ProfilePage = () => {
    const [user, setUser] = useState(null);
    const [message, setMessage] = useState("");

    const showMessage = (msg)=>{
        setMessage(msg);
        setTimeout(() => {
            setMessage("");
        }, 4000);
    }
    useEffect(()=>{
        const fetchUser = async ()=>{
            try {
                const userData = await getLoggedInUser();
                
                setUser(userData);
            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);
            }
        }
        fetchUser();
    }, [])
  return (
    <Layout>
        {message && <div className='message'>{message}</div>}
        <div className="profile-page">
            {user && (
                <div className="profile-card">
                    <h1>Hello, {user.name}</h1>
                    <div className="profile-info">
                        <div className="profile-item">
                            <label> Name</label>
                            <span>{user.name}</span>
                        </div>
                          <div className="profile-item">
                              <label> Email</label>
                              <span>{user.email}</span>
                          </div>
                          <div className="profile-item">
                              <label> Phone Number</label>
                              <span>{user.phoneNumber}</span>
                          </div>
                          <div className="profile-item">
                              <label> Role</label>
                              <span>{user.role}</span>
                          </div>
                    </div>
                </div>
            )}
        </div>
    </Layout>
  )
}
