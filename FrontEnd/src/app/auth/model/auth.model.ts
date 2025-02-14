export interface Login {
  email: string;
  password: string;
}

export interface CurrentUser {
  email: string;
  role: string;
}

export interface AuthenticationResponse {
  user: boolean;
  accessToken: string;
  expiresIn: string;
  refreshToken: string;
  isEnabled: boolean;
}

export interface Address {
  street: string;
  streetNumber: string;
  city: string;
  country: string;
  postalCode: string;
}

export interface User {
  id?: number,
  email: string;
  firstName: string;
  lastName: string;
  address: Address;
  phoneNumber: string;
}

export interface Client {
  user: User;
  penaltyPoints: number;
  isEnabled: boolean;
}

export interface Seller {
  user: User;
}

export interface Deliverer {
  user: User;
}

export interface Admin {
  user: User;
}

export interface UserRegistration {
  email: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  password: string;
  street: string;
  streetNumber: string;
  city: string;
  country: string;
  postalCode: string;
  isSenior: boolean;
  userType: string;
}

export interface Role {
  id?: number,
  name: string;

}

export interface UserWRole {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  address: {
    street: string;
    streetNumber: string;
    postalCode: string;
    city: string;
    country: string;
  };
  role: {
    name: string;
  };
  isEnabled: boolean;
  penaltyPoints?: number;
}
