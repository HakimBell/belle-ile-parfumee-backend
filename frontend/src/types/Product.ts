export interface Product {
    productCode: string;
    name: string;
    brand: string;
    price: number;
    stock: number;
    description?: string;
    imageUrl?: string;
    createdAt: string;
    concentrationType: string;
    gender: string;
    size: number;
}

export interface CreateProductDto {
    productCode?: string;
    name: string;
    brand: string;
    price: number;
    stock: number;
    description?: string;
    imageUrl?: string;
    concentrationType: string;
    gender: string;
    size: number;
}