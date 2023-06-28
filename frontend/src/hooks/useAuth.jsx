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
	const [cart, setCart] = useState(() => {
    const storedCart = localStorage.getItem("cart");
    return storedCart ? JSON.parse(storedCart) : [];
  });
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

  const addToCart = (product) => {
    setCart((prevCart) => {
      const updatedCart = [...prevCart, product];
      localStorage.setItem("cart", JSON.stringify(updatedCart)); // Store in localStorage
      return updatedCart;
    });
  };

  const removeFromCart = (product) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.filter((p) => p.name !== product.name);
      localStorage.setItem("cart", JSON.stringify(updatedCart)); // Store in localStorage
      return updatedCart;
    });
  };

	const clearCart = () => {
		setCart([]);
	}

  useEffect(() => {
    const username = localStorage.getItem("username");
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

  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(cart));
  }, [cart]);

  return (
    <AuthContext.Provider
      value={{ auth, cart, addToCart, removeFromCart, clearCart, submitLogin, submitRegister }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const context = useContext(AuthContext);
  return context;
}