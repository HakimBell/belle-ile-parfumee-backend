import React from 'react';
import { useProducts } from '../../hooks/useProducts';
import { useProductActions} from "../../hooks/UseProductActions.ts";
import './AdminProducts.css';

const AdminProducts: React.FC = () => {
    const { products, loading, error, refetch } = useProducts();
    const { deleteProduct, loading: actionLoading } = useProductActions();

    const handleDelete = async (productCode: string, productName: string) => {
        const confirmed = window.confirm(
            `Êtes-vous sûr de vouloir supprimer "${productName}" ?`
        );

        if (!confirmed) return;

        const success = await deleteProduct(productCode);

        if (success) {
            await refetch(); // Recharge la liste sans recharger la page
        }
    };

    if (loading) return <div>Chargement...</div>;
    if (error) return <div>Erreur: {error}</div>;

    return (
        <div className="admin-products-container">
            <header className="products-header">
                <h1>Gestion des Produits</h1>
                <button className="btn-primary">+ Ajouter un produit</button>
            </header>

            <div className="products-table-container">
                <table className="products-table">
                    <thead>
                    <tr>
                        <th>Code</th>
                        <th>Nom</th>
                        <th>Marque</th>
                        <th>Prix</th>
                        <th>Stock</th>
                        <th>Genre</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {products.map((product) => (
                        <tr key={product.productCode}>
                            <td>{product.productCode}</td>
                            <td>{product.name}</td>
                            <td>{product.brand}</td>
                            <td>{product.price.toFixed(2)} €</td>
                            <td>{product.stock}</td>
                            <td>{product.gender}</td>
                            <td>
                                <button className="btn-edit">✏️</button>
                                <button
                                    className="btn-delete"
                                    onClick={() => handleDelete(product.productCode, product.name)}
                                    disabled={actionLoading}
                                >
                                    {actionLoading ? '⏳' : '🗑️'}
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AdminProducts;