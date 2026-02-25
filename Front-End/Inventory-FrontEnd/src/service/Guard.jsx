import React, { useEffect, useState } from "react";
import { isAdmin, isAuthenticated } from "./ApiService";
import { Navigate, useLocation } from "react-router-dom";

export const ProtectedRoute = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [isAuth, setIsAuth] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const check = async () => {
      const auth = await isAuthenticated();
      setIsAuth(auth);
      setLoading(false);
    };
    check();
  }, []);

  if (loading) return null; 

  // Si NO está autenticado, fuera. 
  // Si SÍ está, no importa si es admin o no, lo dejamos pasar.
  return isAuth ? children : <Navigate to="/login" replace state={{ from: location }} />;
};

export const AdminRoute = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [isAdm, setIsAdm] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const check = async () => {
      const admin = await isAdmin();
      setIsAdm(admin);
      setLoading(false);
    };
    check();
  }, []);

  if (loading) return null;

  // Si no es admin, lo mandamos a una ruta segura o login
  return isAdm ? children : <Navigate to="/login" replace state={{ from: location }} />;
};