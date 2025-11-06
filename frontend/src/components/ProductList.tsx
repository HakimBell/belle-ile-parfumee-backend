import React from 'react';
import ProductCard from './ProductCard';
import type { Product } from '../types/Product';
import './ProductList.css';

interface ProductListProps {
    products: Product[];
    loading: boolean;
    error: string | null;
    title: string;
    description: string;
}

const ProductList: React.FC<ProductListProps> = ({ products, loading, error, title, description }) => {
    if (loading) return <div className="loading-message">Chargement des produits...</div>;
    if (error) return <div className="error-message">{error}</div>;

    return (
        <div className="product-list-container">
            <div className="page-header">
                <h1>{title}</h1>
                <p>{description}</p>
            </div>

            {products.length === 0 ? (
                <div className="no-products">Aucun produit disponible</div>
            ) : (
                <div className="product-grid">
                    {products.map((product) => (
                        <ProductCard key={product.productCode} product={product} />
                    ))}
                </div>
            )}
        </div>
    );
};

export default ProductList;