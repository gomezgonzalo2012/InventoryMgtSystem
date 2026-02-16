import React, { useState } from 'react'
import { loginUser, saveRole, saveToken } from '../service/ApiService';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const loginData = { email, password };
            const response = await loginUser(loginData);
            console.log(response);
            if(response.status === 200){
                saveToken(response.token);
                saveRole(response.role);
                showMessage(response.message)
                setTimeout(() => {
                    navigate("/dashboard")
                }, 4000)
            }

        } catch (error) {
            showMessage(error.response?.data?.message || "Login error : " + error);
        }
    }

    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }
    return (
        <div className='auth-container'>

            <h2>Login</h2>

            {message && <p className='message'>{message}</p>}
            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type='submit'> Login </button>
            </form>
            <p>Don't have an account? <a href="/register">Register</a></p>
        </div>
    )
}

export default LoginPage;