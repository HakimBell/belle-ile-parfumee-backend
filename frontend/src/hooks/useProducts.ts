import { useState, useEffect, useCallback } from 'react';
import { productService} from "../services/ProductService.ts";
import type { Product } from '../types/Product';

export const useProducts = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchProducts = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await productService.getAllProducts();
            setProducts(data);
        } catch (err) {
            setError('Erreur lors du chargement des produits');
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchProducts().catch(console.error);
    }, [fetchProducts]);

    // Fonction pour rafraîchir les données
    const refetch = useCallback(() => {
        return fetchProducts();
    }, [fetchProducts]);

    return { products, loading, error, refetch };
};