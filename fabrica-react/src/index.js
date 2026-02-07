import React from "react";
import ReactDOM from "react-dom/client";

import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";
import Rotas from "./route/route";
import { ToastContainer, toast } from 'react-toastify';
import { Route } from "react-router-dom";
import Login from "./pages/Login/login";

import { AuthProvider } from "./utils/AuthContext";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <AuthProvider>
      <BrowserRouter>
        {/* <Route path="/login" element={<Login />} /> */}
        <Rotas />
      </BrowserRouter>
    </AuthProvider>
    <ToastContainer />
  </React.StrictMode>
);

reportWebVitals();
