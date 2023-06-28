/* eslint-disable react-refresh/only-export-components */
/* eslint-disable react/prop-types */
import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";
import { REGISTER_URL, LOGIN_URL } from "../urls";

export const AuthContext = React.createContext();

const initialAuthState = {
  isAuthenticated: false,
  isLoading: false,
  error: "",
  username: "",
  email: "",
  password: "",
};

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(initialAuthState);
  const navigate = useNavigate();

  const submitLogin = async (e, userData) => {
    if (e) e.preventDefault();
    let newAuth = { ...auth };
    newAuth.username = userData.username;
    newAuth.password = userData.password;

    try {
      const response = await axios.post(LOGIN_URL, userData, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200) {
        toast.success("Login success");
        newAuth.isAuthenticated = true;
        newAuth.isLoading = false;
        newAuth.error = "";
        newAuth.username = response.data.username;
        localStorage.setItem("username", response.data.username); // Store in localStorage
        navigate("/home", { replace: true });
      }
    } catch (error) {
      console.log("submitLogin -> error: ", error);
      toast.error("Login failed");
      newAuth.isAuthenticated = false;
      newAuth.isLoading = false;
      newAuth.error = "Login Failed";
    }
  };

  const submitRegister = async (e, userData) => {
    if (e) e.preventDefault();
    let newAuth = { ...auth };
    newAuth.email = userData.email;
    newAuth.password = userData.password;

    try {
      const response = await axios.post(REGISTER_URL, userData, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200) {
        toast.success("Register success");
        newAuth.isLoading = false;
        newAuth.error = "";
        navigate("/login", { replace: true });
      }
    } catch (error) {
      console.log("submitRegister -> error: ", error);
      toast.error("Register failed");
      newAuth.isAuthenticated = false;
      newAuth.isLoading = false;
      newAuth.error = "Register Failed";
    }
  };

  useEffect(() => {
    const username = localStorage.getItem("username"); // Retrieve from localStorage

    if (username) {
      setAuth((prevAuth) => ({
        ...prevAuth,
        isAuthenticated: true,
        isLoading: false,
        username: username,
      }));
    } else {
      setAuth((prevAuth) => ({
        ...prevAuth,
        isLoading: false,
      }));
    }
  }, []);

  return (
    <AuthContext.Provider value={{ auth, submitLogin, submitRegister }}>
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const context = useContext(AuthContext);
  return context;
}
