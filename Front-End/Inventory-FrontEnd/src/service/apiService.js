// usar Web Crypto API para cifrado de token en local storage en lugar de Crypto js
import axios from "axios";
import { encryptData, decryptData } from "./CryptoService";
// import CryptoJS from "crypto-js";

const BASE_URL = "http://localhost:8080/api";

export const saveToken = async (token) => {
  const encryptedToken = await encryptData(token);  
  localStorage.setItem("token", encryptedToken);
};

export const getToken = async () => {
  const encryptedToken = localStorage.getItem("token");
  if (!encryptedToken) return null;
  return await decryptData(encryptedToken);
};

export const saveRole = async (role) => {
  const encryptedRole = await encryptData(role);
  localStorage.setItem("role", encryptedRole);
};

export const getRole = async () => {
  const encryptedRole = localStorage.getItem("role");
  if (!encryptedRole) return null;
  return await decryptData(encryptedRole);
};

// for logout

export const clearAuth = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
};

export const getHeader = async () => {
  const token = await getToken();
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
    headers: await getHeader(),
  });
  return response.data;
};

export const getLoggedInUser = async () => {
  const response = await axios.get(`${BASE_URL}/users/current`, {
    headers: await getHeader(),
  });
  return response.data;
};

export const getUserById = async (userId) => {
  const response = await axios.get(`${BASE_URL}/users/${userId}`, {
    headers: await getHeader(),
  });
  return response.data;
};

export const updateUser = async (userId, userData) => {
  const response = await axios.put(
    `${BASE_URL}/users/update/${userId}`,
    userData,
    {
      headers: await getHeader(),
    },
  );
  return response.data;
};

export const deleteUser = async (userId) => {
  const response = await axios.delete(`${BASE_URL}/users/update/${userId}`, {
    headers: await getHeader(),
  });
  return response.data;
}

// categories

export const createCategory = async (categoryData) => {
  const response = await axios.post(
    `${BASE_URL}/categories/add`, categoryData,
    {
      headers: await getHeader(),
    },
  );
  return response.data;
};

export const getAllCategories =async ()=>{
  const response = await axios.get(`${BASE_URL}/categories/all`, {
    headers: await getHeader()
  });
  return response.data;
}

export const getCategorybyId = (id) => {
  const response = axios.get(`${BASE_URL}/categories/${id}`);
  return response.data;
};
export const updateCategory = async (id, categoryData) => {
  const response = await axios.put(`${BASE_URL}/categories/update/${id}`, categoryData, {
    headers : await getHeader()
  });
  return response.data;
};
export const deleteCategory = async (id) => {
  const response = await axios.delete(
    `${BASE_URL}/categories/delete/${id}`,
    {
      headers: await getHeader(),
    },
  );
  return response.data;
};

// Suppliers

export const createSupplier = async (supplierData) => {
  const response = await axios.post(`${BASE_URL}/suppliers/add`, supplierData, {
    headers: await getHeader(),
  });
  return response.data;
};

export const getAllSuppliers =async ()=>{
  const response = await axios.get(`${BASE_URL}/suppliers/all`, {
    headers: await getHeader(),
  });
  return response.data;
}

export const getSupplierbyId = async (id) => {
  const response = await axios.get(`${BASE_URL}/suppliers/${id}`, {
     headers: await getHeader(),
  });  
  return response.data;
};

export const updateSupplier = async (id, supplierData) => {
  const response = await axios.put(
    `${BASE_URL}/suppliers/update/${id}`,
    supplierData,
    {
      headers: await getHeader(),
    },
  );
  return response.data;
};
export const deleteSupplier = async (id) => {
  const response = await axios.delete(
    `${BASE_URL}/suppliers/delete/${id}`,
    {
      headers: await getHeader(),
    },
  );
  return response.data;
};

// logout

export const logout = () => {
  clearAuth();
}

export const isAuthenticated = async () => {
  const token = await getToken();
  // return !!token;
  // lo mismo que:
  if (token) {
    return true;
  } else {
    return false;
  }
}

export const isAdmin = async () => {
  const role = await getRole();
  return role === "ADMIN";
}