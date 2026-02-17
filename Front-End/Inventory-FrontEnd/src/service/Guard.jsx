import React from "react";
import { isAdmin, isAuthenticated } from "./ApiService";
import { Navigate, useLocation } from "react-router-dom";

export const ProtectedRoute = ({ children }) => {
  const location = useLocation();

  if (!isAuthenticated()) {
    // Redirect a login si no está autenticado
    return <Navigate to="/login" replace state={{ from: location }} />;
  }

  return children;
};

export const AdminRoute = ({ children }) => {
  const location = useLocation();
  if (!isAdmin()) {
    // Si no es admin, podrías mandarlo al login o a una página de "No Autorizado"
    return <Navigate to="/login" replace state={{ from: location }} />;
  }

  return children;
};