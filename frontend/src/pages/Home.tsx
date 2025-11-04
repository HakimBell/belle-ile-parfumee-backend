import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import ProductCard from '../components/ProductCard';
import { productService} from "../services/ProductService.ts";
import type { Product } from '../types/Product';
import './Home.css';

const Home: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                setLoading(true);
                const data = await productService.getAllProducts();
                setProducts(data);
            } catch (err) {
                setError('Erreur lors du chargement des produits');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts().catch(console.error);
    }, []);

    return (
        <div>
            <Header />
            <div className="home-container">
                <div className="page-header">
                    <h1>Collection Parfums</h1>
                    <p>Découvrez notre sélection de parfums pour hommes et femmes</p>
                </div>

                {loading && <p>Chargement des produits...</p>}
                {error && <p style={{ color: 'red' }}>{error}</p>}

                <div className="product-grid">
                    {products.map((product) => (
                        <ProductCard key={product.productCode} product={product} />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Home;