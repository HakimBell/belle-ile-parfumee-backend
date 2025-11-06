import React from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Header';
import ProductList from '../components/ProductList';
import { useProductsByGender } from '../hooks/useProductsByGender';

type GenderType = 'Homme' | 'Femme' | 'Mixte';

const genderConfig: Record<string, { title: string; description: string; gender: GenderType }> = {
    'hommes': {
        title: 'Parfums Hommes',
        description: 'Découvrez notre collection de parfums masculins',
        gender: 'Homme'
    },
    'femmes': {
        title: 'Parfums Femmes',
        description: 'Découvrez notre collection de parfums féminins',
        gender: 'Femme'
    },
    'mixtes': {
        title: 'Parfums Mixtes',
        description: 'Découvrez notre collection de parfums unisexes',
        gender: 'Mixte'
    }
};

const ParfumsPage: React.FC = () => {
    const { category } = useParams<{ category: string }>();
    const config = category ? genderConfig[category] : null;

    const { products, loading, error } = useProductsByGender(config?.gender || 'Homme');

    if (!config) {
        return <div>Catégorie non trouvée</div>;
    }

    return (
        <div>
            <Header />
            <ProductList
                products={products}
                loading={loading}
                error={error}
                title={config.title}
                description={config.description}
            />
        </div>
    );
};

export default ParfumsPage;