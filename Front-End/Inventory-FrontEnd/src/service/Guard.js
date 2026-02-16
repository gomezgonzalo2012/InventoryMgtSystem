import React from "react";
import { isAdmin, isAuthenticated } from "./ApiService";
import { Navigate, useLocation } from "react-router-dom";

export const ProtectedRoute = ({ element: Component }) => {
  const location = useLocation();
  return isAuthenticated() ? (
    Component
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};

export const AdminRoute = ({ element: Component }) => {
  const location = useLocation();
  return isAdmin() ? (
    Component
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};