import React, { useContext, useEffect, useRef } from "react";
import authApi from "@/api/authApi";
import publicApi from "@/api/publicApi";
import { Client } from "@stomp/stompjs";
import { toast } from "sonner";
import axios from "axios";

axios.defaults.withCredentials = true;

const Context = React.createContext();

const AuthContex = ({ children }) => {
  const [user, setUser] = React.useState(null);
  const [loading, setLoading] = React.useState(true);
  const [isInitialized, setIsInitialized] = React.useState(false);
  const [dialCodes, setDialCodes] = React.useState([]);
  const [categories, setCategories] = React.useState([]);
  const [jobs, setJobs] = React.useState([]);
  const [company, setCompany] = React.useState(null);
  const [notifications, setNotifications] = React.useState([]);
  const [selectedJob, setSelectedJob] = React.useState(null);
  const [appliedJobs, setAppliedJobs] = React.useState([]);

  const socketRef = useRef(null);
  const connectedRef = useRef(false);

  const getAccessToken = async () => {
    try {
      const response = await authApi.post("/token-for-websocket");
      return response.data;
    } catch (err) {
      console.error("Token refresh failed!", err);
      throw err;
    }
  };

  const fetchUser = async () => {
    setLoading(true);
    const token = localStorage.getItem("token");

    if (!token) {
      setUser(null);
      setLoading(false);
      return;
    }

    try {
      authApi.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      const response = await authApi.get("/me");
      setUser(response.data);
    } catch (error) {
      setUser(null);
      localStorage.removeItem("token");
      delete authApi.defaults.headers.common["Authorization"];
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await publicApi.get("/categories");
      setCategories(response.data);
    } catch (error) {
      setCategories([]);
    }
  };

  const fetchNotifications = async () => {
    if (!user) return;
    try {
      const response = await publicApi.get("/notification");
      setNotifications(response.data);
    } catch (error) {}
  };

  const login = async (userData) => {
    setLoading(true);
    try {
      const response = await authApi.post("/login", userData);
      const token =
        response.data.token ||
        response.data.jwt ||
        response.data.access_token ||
        response.data?.data?.token;

      if (token) {
        localStorage.setItem("token", token);
        authApi.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      } else {
        toast.error("Token not received from server");
        throw new Error("Token missing in response");
      }

      setIsInitialized(true);
      await fetchUser();
    } catch (error) {
      toast.error("Login failed");
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const signup = async (userData) => {
    setLoading(true);
    try {
      await authApi.post("/register", userData);
      setIsInitialized(true);
      await fetchUser();
    } catch (error) {
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    setLoading(true);
    try {
      await authApi.post("/logout");
      setUser(null);
      setIsInitialized(false);
      localStorage.removeItem("token");
      delete authApi.defaults.headers.common["Authorization"];
    } catch (error) {} finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUser();
    fetchCategories();
  }, []);

  useEffect(() => {
    if (user) fetchNotifications();
  }, [user]);

  useEffect(() => {
    const connectWebSocket = async () => {
      if (connectedRef.current || !user) return;

      try {
        const token = await getAccessToken();

        socketRef.current = new Client({
          brokerURL: "wss://backend-7wlz.onrender.com/ws",
          connectHeaders: { Authorization: token },
          reconnectDelay: 5000,
          onConnect: () => {
            connectedRef.current = true;
            socketRef.current.subscribe("/user/queue/notifications", (message) => {
              const body = JSON.parse(message.body);
              setNotifications((prev) => [body, ...prev]);
            });
          },
          onStompError: (frame) => {
            console.error("WebSocket STOMP error:", frame);
          },
        });

        socketRef.current.activate();
      } catch (err) {
        console.error("❌ WebSocket connection error:", err);
      }
    };

    connectWebSocket();

    return () => {
      if (socketRef.current && socketRef.current.active) {
        socketRef.current.deactivate();
        connectedRef.current = false;
      }
    };
  }, [user]);

  const value = {
    user,
    setUser,
    loading,
    setLoading,
    fetchUser,
    login,
    signup,
    logout,
    dialCodes,
    categories,
    setDialCodes,
    setCategories,
    isInitialized,
    jobs,
    setJobs,
    company,
    setCompany,
    selectedJob,
    setSelectedJob,
    notifications,
    setNotifications,
    appliedJobs,
    setAppliedJobs,
    socketRef,
    connectedRef,
    getAccessToken,
    fetchCategories,
  };

  return <Context.Provider value={value}>{children}</Context.Provider>;
};

export default AuthContex;
export const useAuth = () => useContext(Context);
