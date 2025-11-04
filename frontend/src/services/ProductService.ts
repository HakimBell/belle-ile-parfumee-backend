import { api } from './api';
import type { Product } from '../types/Product';

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
};