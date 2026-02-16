import React, { useState } from 'react'
import { registerUser} from '../service/ApiService';
import { useNavigate } from 'react-router-dom';

const RegisterPage = () => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("")
    const [message, setMessage] = useState("")

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const registerData = { email, password, phoneNumber, name };
            const response = await registerUser(registerData);
            console.log(response);
            if (response.status === 200 || response.status === 204) {
                showMessage(response.message)
                setTimeout(()=>{
                    navigate("/login")
                }, 4000)
            }

        } catch (error) {
            showMessage(error.response?.data?.message || "Register error : " + error);
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

            <h2>Register</h2>

            {message && <p className='message'>{message}</p>}
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Phone number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                />
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
                <button type='submit'> Register </button>
            </form>
            <p>Already have an account? <a href="/login">Login</a></p>
        </div>
    )
}

export default RegisterPage;