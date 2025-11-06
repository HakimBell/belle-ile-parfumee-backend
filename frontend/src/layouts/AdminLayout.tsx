import React from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import './AdminLayout.css';

interface AdminLayoutProps {
    children: React.ReactNode;
}

const AdminLayout: React.FC<AdminLayoutProps> = ({ children }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        authService.removeToken();
        navigate('/admin/login');
    };

    return (
        <div className="admin-layout">
            <aside className="admin-sidebar">
                <div className="sidebar-header">
                    <h1>Belle Île Parfumée</h1>
                    <p>Administration</p>
                </div>

                <nav className="sidebar-nav">
                    <a href="/" className="nav-item">
                         Voir le site
                    </a>
                    <a href="/admin/products" className="nav-item">
                         Produits
                    </a>
                    <a href="/admin/orders" className="nav-item">
                         Commandes
                    </a>
                    <a href="/admin/clients" className="nav-item">
                         Clients
                    </a>
                </nav>

                <button onClick={handleLogout} className="logout-btn">
                     Déconnexion
                </button>
            </aside>

            <main className="admin-main">
                {children}
            </main>
        </div>
    );
};

export default AdminLayout;