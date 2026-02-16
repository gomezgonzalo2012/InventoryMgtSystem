import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/Loginpage'
import Dashboard from './pages/Dashboard'
import RegisterPage from './pages/RegisterPage'
import CategoryPage from './pages/CategoryPage'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/register' element={<RegisterPage />}></Route>
        <Route path='/login' element={<LoginPage />}></Route>
        <Route path='/dashboard' element={<Dashboard />}></Route>
        <Route path='/category' element={<CategoryPage />}></Route>

        <Route path='*' element={<LoginPage />}></Route>
      </Routes>
    </BrowserRouter>

  )
}

export default App
