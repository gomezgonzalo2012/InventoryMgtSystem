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
import AddEditProductPage from './pages/AddEditProductPage.jsx'
import PurchasePage from './pages/PurchasePage.jsx'
import SellPage from './pages/SellPage.jsx'
import TransactionsPage from './pages/TransactionsPage.jsx'
import TransactionDetailsPage from './pages/TransactionDetailsPage.jsx'
import { ProfilePage } from './pages/ProfilePage.jsx'

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
        <Route path='/add-product' element={<AdminRoute><AddEditProductPage /></AdminRoute>}></Route>
        <Route path='/update-product/:productId' element={<AdminRoute><AddEditProductPage /></AdminRoute>}></Route>
        <Route path='/product' element={<AdminRoute><ProductPage /></AdminRoute>}></Route>
        <Route path='/transaction' element={<AdminRoute><TransactionsPage /></AdminRoute>}></Route>
        <Route path='/transaction/:transactionId' element={<AdminRoute><TransactionDetailsPage /></AdminRoute>}></Route>

        {/* Auth Routes */}
        <Route path='/dashboard' element={<Dashboard />}></Route>
        <Route path='/purchase' element={<ProtectedRoute><PurchasePage /></ProtectedRoute>}></Route>
        <Route path='/sell' element={<ProtectedRoute><SellPage /></ProtectedRoute>}></Route>
        <Route path='/profile' element={<ProtectedRoute><ProfilePage /></ProtectedRoute>}></Route>



        <Route path='*' element={<LoginPage />}></Route>
      </Routes>
    </BrowserRouter>

  )
}

export default App
