import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import { useProduct } from '../hooks/useProduct';
import './ProductDetail.css';

const ProductDetail: React.FC = () => {
    const { productCode } = useParams<{ productCode: string }>();
    const navigate = useNavigate();
    const { product, loading, error } = useProduct(productCode || '');
    const [quantity, setQuantity] = useState(1);

    const handleIncrement = () => {
        if (product && quantity < product.stock) {
            setQuantity(quantity + 1);
        }
    };

    const handleDecrement = () => {
        if (quantity > 1) {
            setQuantity(quantity - 1);
        }
    };

    if (loading) return <div className="loading-container">Chargement...</div>;
    if (error || !product) return <div className="error-container">Produit non trouvé</div>;

    return (
        <div>
            <Header />
            <div className="product-detail-container">
                <button onClick={() => navigate(-1)} className="back-btn">
                    ← Retour
                </button>

                <div className="product-detail-content">
                    <div className="product-detail-image">
                        {product.imageUrl ? (
                            <img src={product.imageUrl} alt={product.name} />
                        ) : (
                            <div className="product-placeholder-large">🧴</div>
                        )}
                    </div>

                    <div className="product-detail-info">
                        <div className="product-badge-detail">{product.gender}</div>
                        <p className="product-brand-detail">{product.brand}</p>
                        <h1 className="product-name-detail">{product.name}</h1>

                        <div className="product-price-detail">{product.price.toFixed(2)} €</div>

                        <div className="product-stock-detail">
                            {product.stock > 0 ? (
                                <span className="stock-available">✓ En stock</span>
                            ) : (
                                <span className="stock-unavailable">✗ Rupture de stock</span>
                            )}
                        </div>

                        <div className="product-description-detail">
                            <h3>Description</h3>
                            <p>{product.description}</p>
                        </div>

                        <div className="product-details-grid">
                            <div className="detail-item">
                                <span className="detail-label">Type :</span>
                                <span className="detail-value">{product.concentrationType}</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">Taille :</span>
                                <span className="detail-value">{product.size} ml</span>
                            </div>
                            <div className="detail-item">
                                <span className="detail-label">Genre :</span>
                                <span className="detail-value">{product.gender}</span>
                            </div>
                        </div>

                        <div className="product-actions">
                            <div className="quantity-selector">
                                <button className="qty-btn" onClick={handleDecrement}>-</button>
                                <input type="number" value={quantity} readOnly />
                                <button className="qty-btn" onClick={handleIncrement}>+</button>
                            </div>
                            <button className="add-to-cart-btn" disabled={product.stock === 0}>
                                🛒 Ajouter au panier
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetail;