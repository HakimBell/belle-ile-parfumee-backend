import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import AdminProducts from './pages/admin/AdminProducts';
import ProductDetail from "./pages/ProductDetail.tsx";
import ParfumsPage from './pages/ParfumsPage';
import './App.css';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/product/:productCode" element={<ProductDetail />} />
                <Route path="/parfums/:category" element={<ParfumsPage />} />
                <Route path="/admin/products" element={<AdminProducts />} />
            </Routes>
        </Router>
    );
};

export default App;