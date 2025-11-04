import { api } from './api';
import type { Product,CreateProductDto } from '../types/Product';

export const productService = {
    getAllProducts: async (): Promise<Product[]> => {
        const response = await api.get<Product[]>('/products');
        return response.data;
    },

    getProductByCode: async (productCode: string): Promise<Product> => {
        const response = await api.get<Product>(`/products/${productCode}`);
        return response.data;
    },

    getProductsByBrand: async (brand: string): Promise<Product[]> => {
        const response = await api.get<Product[]>(`/products/brand/${brand}`);
        return response.data;
    },

    getProductsByGender: async (gender: string): Promise<Product[]> => {
        const response = await api.get<Product[]>(`/products/gender/${gender}`);
        return response.data;
    },
    // POST /api/products - Créer un nouveau produit
    createProduct: async (product: CreateProductDto): Promise<Product> => {
        const response = await api.post<Product>('/products', product);
        return response.data;
    },
    // PUT /api/products/{productCode} - Mettre à jour un produit
    updateProduct: async (productCode: string, product: Partial<CreateProductDto>): Promise<Product> => {
        const response = await api.put<Product>(`/products/${productCode}`, product);
        return response.data;
    },
    // DELETE /api/products/{productCode} - Supprimer un produit
    deleteProduct: async (productCode: string): Promise<void> => {
        await api.delete(`/products/${productCode}`);
    },
};