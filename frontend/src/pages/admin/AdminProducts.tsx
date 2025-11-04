import React, { useState } from 'react';
import { useProducts } from '../../hooks/useProducts';
import { useProductActions} from "../../hooks/UseProductActions.ts";
import Modal from '../../components/Modal';
import ProductForm from '../../components/ProductForm';
import AdminLayout from "../../layouts/AdminLayout.tsx";
import type { Product, CreateProductDto } from '../../types/Product';
import './AdminProducts.css';

const AdminProducts: React.FC = () => {
    const { products, loading, error, refetch } = useProducts();
    const { deleteProduct, createProduct, updateProduct, loading: actionLoading } = useProductActions();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingProduct, setEditingProduct] = useState<Product | null>(null);

    const handleDelete = async (productCode: string, productName: string) => {
        const confirmed = window.confirm(
            `Êtes-vous sûr de vouloir supprimer "${productName}" ?`
        );

        if (!confirmed) return;

        const success = await deleteProduct(productCode);

        if (success) {
            await refetch();
        }
    };

    const handleCreate = async (product: CreateProductDto) => {
        const newProduct = await createProduct(product);

        if (newProduct) {
            setIsModalOpen(false);
            await refetch();
        }
    };

    const handleEdit = async (product: CreateProductDto) => {
        if (!editingProduct) return;

        const updated = await updateProduct(editingProduct.productCode, product);

        if (updated) {
            setIsModalOpen(false);
            setEditingProduct(null);
            await refetch();
        }
    };

    const openEditModal = (product: Product) => {
        setEditingProduct(product);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setEditingProduct(null);
    };

    if (loading) return <div>Chargement...</div>;
    if (error) return <div>Erreur: {error}</div>;

    return (
        <AdminLayout>
        <div className="admin-products-container">
            <header className="products-header">
                <h1>Gestion des Produits</h1>
                <button className="btn-primary" onClick={() => setIsModalOpen(true)}>
                    + Ajouter un produit
                </button>
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
                                <button
                                    className="btn-edit"
                                    onClick={() => openEditModal(product)}
                                >
                                    ✏️
                                </button>
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

            <Modal
                isOpen={isModalOpen}
                onClose={closeModal}
                title={editingProduct ? 'Modifier le produit' : 'Ajouter un produit'}
            >
                <ProductForm
                    onSubmit={editingProduct ? handleEdit : handleCreate}
                    onCancel={closeModal}
                    initialData={editingProduct || undefined}
                    isEdit={!!editingProduct}
                />
            </Modal>
        </div>
        </AdminLayout>
    );
};

export default AdminProducts;