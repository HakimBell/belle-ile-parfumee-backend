import type { Product } from '../types/Product';

export const mockProducts: Product[] = [
    {
        productCode: 'CHANEL-001',
        name: 'Noir Intense',
        brand: 'CHANEL',
        price: 89.90,
        stock: 15,
        description: 'Un parfum masculin intense aux notes boisées et épicées.',
        imageUrl: '',
        createdAt: '2025-11-03',
        concentrationType: 'Eau de Parfum',
        gender: 'Homme',
        size: 100
    },
    {
        productCode: 'DIOR-002',
        name: 'Royal Cologne',
        brand: 'DIOR',
        price: 125.00,
        stock: 8,
        description: 'Une eau de cologne raffinée aux agrumes et lavande.',
        imageUrl: '',
        createdAt: '2025-11-03',
        concentrationType: 'Eau de Cologne',
        gender: 'Homme',
        size: 150
    },
    {
        productCode: 'YSL-003',
        name: 'Fleur de Luxe',
        brand: 'YVES SAINT LAURENT',
        price: 156.50,
        stock: 12,
        description: 'Un parfum floral délicat aux notes de rose et jasmin.',
        imageUrl: '',
        createdAt: '2025-11-03',
        concentrationType: 'Eau de Parfum',
        gender: 'Femme',
        size: 100
    },
    {
        productCode: 'GUCCI-004',
        name: 'Essence Pure',
        brand: 'GUCCI',
        price: 98.75,
        stock: 20,
        description: 'Un parfum frais et naturel aux notes d\'agrumes.',
        imageUrl: '',
        createdAt: '2025-11-03',
        concentrationType: 'Eau de Toilette',
        gender: 'Femme',
        size: 75
    }
];