import React from 'react';
import Header from '../components/Header';
import './Home.css';

const Home: React.FC = () => {
    return (
        <div>
            <Header />
            <div className="home-container">
                <div className="page-header">
                    <h1>Collection Parfums</h1>
                    <p>Découvrez notre sélection de parfums pour hommes et femmes</p>
                </div>
            </div>
        </div>
    );
};

export default Home;