// usar Web Crypto API para cifrado de token en local storage en lugar de Crypto js
import axios from "axios";
import { encryptData, decryptData } from "./CryptoService";
// import CryptoJS from "crypto-js";

const BASE_URL = "http://localhost:8080/api";

export const saveToken = async (token) => {
  const encryptedToken = await encryptData(token);  
  localStorage.setItem("token", encryptedToken);
};

export const getToken = () => {
  const encryptedToken = localStorage.getItem("token");
  if (!encryptedToken) return null;
  return decryptData(encryptedToken);
};

export const saveRole = async (role) => {
  const encryptedRole = await encryptData(role);
  localStorage.setItem("role", encryptedRole);
};

export const getRole = () => {
  const encryptedRole = localStorage.getItem("role");
  if (!encryptedRole) return null;
  return decryptData(encryptedRole);
};

// for logout

export const clearAuth = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
};

export const getHeader = () => {
  const token = getToken();
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

// auth & user

export const registerUser = async (registerData) => {
  const response = await axios.post(`${BASE_URL}/auth/register`, registerData);
  return response.data;
};

export const loginUser = async (loginData) => {
  const response = await axios.post(`${BASE_URL}/auth/login`, loginData);
  return response.data;
};

export const getAllUsers = async () => {
  const response = await axios.get(`${BASE_URL}/users/all`, {
    headers: getHeader(),
  });
  return response.data;
};

export const getLoggedInUser = async () => {
  const response = await axios.get(`${BASE_URL}/users/current`, {
    headers: getHeader(),
  });
  return response.data;
};

export const getUserById = async (userId) => {
  const response = await axios.get(`${BASE_URL}/users/${userId}`, {
    headers: getHeader(),
  });
  return response.data;
};

export const updateUser = async (userId, userData) => {
  const response = await axios.put(
    `${BASE_URL}/users/update/${userId}`,
    userData,
    {
      headers: getHeader(),
    },
  );
  return response.data;
};

export const deleteUser = async (userId) => {
  const response = await axios.delete(`${BASE_URL}/users/update/${userId}`, {
    headers: getHeader(),
  });
  return response.data;
}

// logout

export const logout = () => {
  clearAuth();
}

export const isAuthenticated = () => {
  const token = getToken();
  return !!token;
  // lo mismo que:
  // if (token) {
  //   return true;
  // } else {
  //   return false;
  // }
}

export const isAdmin = () => {
  const role = getRole();
  return role === "ADMIN";
}