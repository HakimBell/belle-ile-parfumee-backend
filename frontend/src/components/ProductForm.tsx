import React, { useState } from 'react';
import type { CreateProductDto } from '../types/Product';
import './ProductForm.css';

interface ProductFormProps {
    onSubmit: (product: CreateProductDto) => Promise<void>;
    onCancel: () => void;
    initialData?: CreateProductDto;
    isEdit?: boolean;
}

const ProductForm: React.FC<ProductFormProps> = ({ onSubmit, onCancel, initialData, isEdit = false }) => {
    const [formData, setFormData] = useState<CreateProductDto>(initialData || {
        name: '',
        brand: '',
        price: 0,
        stock: 0,
        description: '',
        imageUrl: '',
        concentrationType: 'Eau de Parfum',
        gender: 'Homme',
        size: 100,
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === 'price' || name === 'stock' || name === 'size' ? Number(value) : value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        try {
            await onSubmit(formData);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="product-form">
            <div className="form-row">
                <div className="form-group">
                    <label htmlFor="name">Nom *</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="brand">Marque *</label>
                    <input
                        type="text"
                        id="brand"
                        name="brand"
                        value={formData.brand}
                        onChange={handleChange}
                        required
                    />
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label htmlFor="price">Prix (€) *</label>
                    <input
                        type="number"
                        id="price"
                        name="price"
                        value={formData.price}
                        onChange={handleChange}
                        step="0.01"
                        min="0"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="stock">Stock *</label>
                    <input
                        type="number"
                        id="stock"
                        name="stock"
                        value={formData.stock}
                        onChange={handleChange}
                        min="0"
                        required
                    />
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label htmlFor="size">Taille (ml) *</label>
                    <input
                        type="number"
                        id="size"
                        name="size"
                        value={formData.size}
                        onChange={handleChange}
                        min="0"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="concentrationType">Type *</label>
                    <select
                        id="concentrationType"
                        name="concentrationType"
                        value={formData.concentrationType}
                        onChange={handleChange}
                        required
                    >
                        <option value="Eau de Parfum">Eau de Parfum</option>
                        <option value="Eau de Toilette">Eau de Toilette</option>
                        <option value="Eau de Cologne">Eau de Cologne</option>
                    </select>
                </div>
            </div>

            <div className="form-group">
                <label htmlFor="gender">Genre *</label>
                <select
                    id="gender"
                    name="gender"
                    value={formData.gender}
                    onChange={handleChange}
                    required
                >
                    <option value="Homme">Homme</option>
                    <option value="Femme">Femme</option>
                    <option value="Mixte">Mixte</option>
                </select>
            </div>

            <div className="form-group">
                <label htmlFor="description">Description</label>
                <textarea
                    id="description"
                    name="description"
                    value={formData.description}
                    onChange={handleChange}
                    rows={3}
                />
            </div>

            <div className="form-group">
                <label htmlFor="imageUrl">URL Image</label>
                <input
                    type="url"
                    id="imageUrl"
                    name="imageUrl"
                    value={formData.imageUrl}
                    onChange={handleChange}
                />
            </div>

            <div className="form-actions">
                <button type="button" onClick={onCancel} className="btn-cancel" disabled={loading}>
                    Annuler
                </button>
                <button type="submit" className="btn-submit" disabled={loading}>
                    {loading ? (isEdit ? 'Modification...' : 'Ajout en cours...') : (isEdit ? 'Modifier le produit' : 'Ajouter le produit')}
                </button>
            </div>
        </form>
    );
};

export default ProductForm;