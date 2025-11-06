import { useState, useEffect } from 'react';
import { productService} from "../services/ProductService.ts";
import type { Product } from '../types/Product';

export const useProduct = (productCode: string) => {
    const [product, setProduct] = useState<Product | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchProduct = async () => {
            if (!productCode) {
                setLoading(false);
                return;
            }

            try {
                setLoading(true);
                setError(null);
                const data = await productService.getProductByCode(productCode);
                setProduct(data);
            } catch (err) {
                setError('Produit non trouvé');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProduct().catch(console.error);
    }, [productCode]);

    return { product, loading, error };
};