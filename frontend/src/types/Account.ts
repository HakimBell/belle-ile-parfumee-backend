export interface Account {
    email: string;
    role: 'CLIENT' | 'ADMIN';
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    email: string;
    role: 'CLIENT' | 'ADMIN';
    token: string;
}

export interface RegisterRequest {
    email: string;
    password: string;
}