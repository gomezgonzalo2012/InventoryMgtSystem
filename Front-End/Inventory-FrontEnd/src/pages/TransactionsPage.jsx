import React, { useEffect, useState } from 'react'
import Layout from '../components/Layout'
import { getAllTransactions } from '../service/ApiService';
import Pagination from '../components/Pagination';
import { useNavigate } from 'react-router-dom';

const TransactionsPage = () => {
    const [message, setMessage] = useState("");
    const [transactions, setTransactions] = useState([]);
    const [filter, setFilter] = useState("");
    const [filterTerm, setFilterTerm] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const itemsPerPage = 5;

    const navigate = useNavigate();
    const showMessage = (msg) => {
        setMessage(msg)
        setTimeout(() => {
            setMessage("")
        }, 4000)
    }

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const response = await getAllTransactions(currentPage-1, itemsPerPage, filterTerm);
                if (response.status === 200) {

                    // for pagination
                    setTotalPages(response.totalPages);
                    console.log(response)
                    setTransactions(response.transactions);
                }
            } catch (error) {
                showMessage(error.response?.data?.message || "Error : " + error);
            }
        }
        fetchTransactions();
    }, [currentPage, filterTerm])
    const handleSearch = () => {
        setCurrentPage(1);
        setFilterTerm(filter);
    }

    return (
        <Layout>
            {message && <div className='message'>{message}</div>}
            <div className="transactions-page">
                <div className="transactions header">
                    <h1>transactions</h1>
                    <div className='transaction-search'>
                        <input type="text"
                            placeholder='Search transaction'
                            value={filter}
                            onChange={(e) => setFilter(e.target.value)} />
                        <button onClick={handleSearch}> Search</button>
                    </div>
                    
                </div>
            </div>
            {transactions &&
                <table className='transactions-table'>
                    <thead>
                        <tr>
                            <th>TYPE</th>
                            <th>STATUS</th>
                            <th>TOTAL PRICE</th>
                            <th>TOTAL PRODUCTS</th>
                            <th>DATE</th>
                            <th>ACTIONS</th>
                        </tr>
                    </thead>
                    <tbody>
                        {transactions.map((transaction) => (
                            <tr key={transaction.id}>
                                <td>{transaction.transactionType}</td>
                                <td>{transaction.status}</td>
                                <td>{transaction.totalPrice}</td>
                                <td>{transaction.totalProducts}</td>
                                <td>{new Date(transaction.createdAt).toLocaleString()}</td>
                                <td>
                                    <button onClick={() => {navigate(`/transaction/${transaction.id}`) }}>
                                        View details
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>}
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={setCurrentPage}
            />
        </Layout>
    )
}

export default TransactionsPage;