import { useState } from 'react';
import { productService} from "../services/ProductService.ts";
import type { Product, CreateProductDto } from '../types/Product';

export const useProductActions = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const deleteProduct = async (productCode: string): Promise<boolean> => {
        try {
            setLoading(true);
            setError(null);
            await productService.deleteProduct(productCode);
            return true;
        } catch (err) {
            setError('Erreur lors de la suppression');
            console.error(err);
            return false;
        } finally {
            setLoading(false);
        }
    };

    const createProduct = async (product: CreateProductDto): Promise<Product | null> => {
        try {
            setLoading(true);
            setError(null);
            const newProduct = await productService.createProduct(product);
            return newProduct;
        } catch (err) {
            setError('Erreur lors de la création');
            console.error(err);
            return null;
        } finally {
            setLoading(false);
        }
    };

    const updateProduct = async (productCode: string, product: Partial<CreateProductDto>): Promise<Product | null> => {
        try {
            setLoading(true);
            setError(null);
            const updatedProduct = await productService.updateProduct(productCode, product);
            return updatedProduct;
        } catch (err) {
            setError('Erreur lors de la modification');
            console.error(err);
            return null;
        } finally {
            setLoading(false);
        }
    };

    return {
        deleteProduct,
        createProduct,
        updateProduct,
        loading,
        error,
    };
};