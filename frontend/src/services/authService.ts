import { api } from './api';
import type { LoginRequest, LoginResponse, RegisterRequest } from '../types/Account';

export const authService = {
    // Login (client ou admin)
    login: async (credentials: LoginRequest): Promise<LoginResponse> => {
        const response = await api.post<LoginResponse>('/accounts/login', credentials);
        return response.data;
    },

    // Register (inscription client)
    register: async (data: RegisterRequest): Promise<LoginResponse> => {
        const response = await api.post<LoginResponse>('/accounts/register', data);
        return response.data;
    },

    // Vérifier si email existe
    emailExists: async (email: string): Promise<boolean> => {
        const response = await api.get<boolean>(`/accounts/exists/${email}`);
        return response.data;
    },

    // Sauvegarder le token dans localStorage
    saveToken: (token: string) => {
        localStorage.setItem('authToken', token);
    },

    // Récupérer le token
    getToken: (): string | null => {
        return localStorage.getItem('authToken');
    },

    // Supprimer le token (logout)
    removeToken: () => {
        localStorage.removeItem('authToken');
    },

    // Vérifier si l'utilisateur est connecté
    isAuthenticated: (): boolean => {
        return !!authService.getToken();
    },
};