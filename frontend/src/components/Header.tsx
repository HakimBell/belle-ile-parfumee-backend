import React from 'react';
import './Header.css';

const Header: React.FC = () => {
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
                    <button className="icon-btn">♡</button>
                    <button className="icon-btn">
                        🛒
                        <span className="cart-badge">0</span>
                    </button>
                </div>
            </div>
        </header>
    );
};

export default Header;