import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import LoginPage from './pages/Loginpage'
import Dashboard from './pages/Dashboard'
import RegisterPage from './pages/RegisterPage'
import CategoryPage from './pages/CategoryPage'
import { AdminRoute, ProtectedRoute} from './service/Guard.jsx'
import SupplierPage from './pages/SupplierPage.jsx'
import EditSupplierPage from './pages/AddEditSupplierPage.jsx'
import ProductPage from './pages/ProductPage.jsx'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/register' element={<RegisterPage />}></Route>
        <Route path='/login' element={<LoginPage />}></Route>
        {/* Admin routes */}
        <Route path='/category' element={<AdminRoute><CategoryPage /></AdminRoute>}></Route>
        <Route path='/supplier' element={<AdminRoute><SupplierPage /></AdminRoute>}></Route>
        <Route path='/edit-supplier/:supplierId' element={<AdminRoute><EditSupplierPage /></AdminRoute>}></Route>
        <Route path='/add-supplier' element={<AdminRoute><EditSupplierPage /></AdminRoute>}></Route>
        
        {/* Auth Routes */}
        <Route path='/dashboard' element={<Dashboard />}></Route>
        <Route path='/product' element={<ProtectedRoute><ProductPage/></ProtectedRoute>}></Route>


        <Route path='*' element={<LoginPage />}></Route>
      </Routes>
    </BrowserRouter>

  )
}

export default App
