import React from 'react';
import Header from '../components/Header';
import ProductCard from '../components/ProductCard';
import { mockProducts } from '../utils/mockData';
import './Home.css';

const Home: React.FC = () => {
    return (
        <div>
            <Header />
            <div className="home-container">
                <div className="page-header">
                    <h1>Collection Parfums</h1>
                    <p>Découvrez notre sélection de parfums pour hommes et femmes</p>
                </div>

                <div className="product-grid">
                    {mockProducts.map((product) => (
                        <ProductCard key={product.productCode} product={product} />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Home;