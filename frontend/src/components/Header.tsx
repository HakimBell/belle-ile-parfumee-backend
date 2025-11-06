import React from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import './Header.css';

const Header: React.FC = () => {
    const navigate = useNavigate();
    const isAuthenticated = authService.isAuthenticated();

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleLogout = () => {
        authService.removeToken();
        window.location.href = '/';
    };

    return (
        <header className="header">
            <div className="header-container">
                <a href="/" className="logo">Belle Île Parfumée</a>

                <nav className="nav">
                    <a href="/">Accueil</a>
                    <a href="/hommes">Parfums Hommes</a>
                    <a href="/femmes">Parfums Femmes</a>
                    <a href="/mixtes">Parfums Mixtes</a>
                    <a href="/nouveautes">Nouveautés</a>
                </nav>

                <div className="header-icons">
                    {isAuthenticated ? (
                        <>
                            <button className="icon-btn">♡</button>
                            <button className="icon-btn">
                                🛒
                                <span className="cart-badge">0</span>
                            </button>
                            <button className="login-btn" onClick={handleLogout}>
                                Déconnexion
                            </button>
                        </>
                    ) : (
                        <>
                            <button className="icon-btn">♡</button>
                            <button className="icon-btn">
                                🛒
                                <span className="cart-badge">0</span>
                            </button>
                            <button className="login-btn" onClick={handleLoginClick}>
                                Se connecter
                            </button>
                        </>
                    )}
                </div>
            </div>
        </header>
    );
};

export default Header;