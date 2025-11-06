import { useState, useEffect } from 'react';
import { productService} from "../services/ProductService.ts";
import type { Product } from '../types/Product';

export const useProductsByGender = (gender: string) => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                setLoading(true);
                setError(null);
                const data = await productService.getProductsByGender(gender);
                setProducts(data);
            } catch (err) {
                setError('Erreur lors du chargement des produits');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts().catch(console.error);
    }, [gender]);

    return { products, loading, error };
};