import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import type { LoginRequest } from '../types/Account';
import './Login.css';

const Login: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const credentials: LoginRequest = { email, password };
            const response = await authService.login(credentials);

            // Sauvegarder le token
            authService.saveToken(response.token);

            // Rediriger selon le rôle
            if (response.role === 'ADMIN') {
                navigate('/admin/products');
            } else {
                navigate('/'); // Client vers accueil
            }
        } catch (err) {
            setError('Email ou mot de passe incorrect');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page-container">
            <div className="login-page-card">
                <h1>Belle Île Parfumée</h1>
                <h2>Connexion</h2>

                <form onSubmit={handleSubmit} className="login-page-form">
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="votre@email.com"
                            required
                            disabled={loading}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Mot de passe</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                            required
                            disabled={loading}
                        />
                    </div>

                    <button type="submit" className="login-page-btn" disabled={loading}>
                        {loading ? 'Connexion...' : 'Se connecter'}
                    </button>

                    {error && <p className="error-message">{error}</p>}
                </form>

                <div className="login-footer">
                    <a href="/">← Retour à l'accueil</a>
                </div>
            </div>
        </div>
    );
};

export default Login;