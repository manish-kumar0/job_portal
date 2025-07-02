// // frontend/employara/src/api/authApi.js

// import axios from "axios";
// import { handle401 } from "./handle401";

// const authApi = axios.create({
//   baseURL: "https://backend-7wlz.onrender.com/api/auth",
//   withCredentials: true,
// });

// authApi.interceptors.request.use(
//   (config) => {
//     const token = localStorage.getItem("token");
//     if (token) {
//       config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => Promise.reject(error)
// );

// authApi.interceptors.response.use(
//   (response) => response,
//   (error) => handle401(error, authApi)
// );

// export default authApi;



// frontend/employara/src/api/authApi.js

import axios from "axios";
import { handle401 } from "./handle401";

const authApi = axios.create({
  baseURL: "https://backend-7wlz.onrender.com/api/auth",
  withCredentials: true, // ✅ sends cookies
});

// ✅ No Authorization header required
authApi.interceptors.request.use(
  (config) => config,
  (error) => Promise.reject(error)
);

// ✅ Retry on 401 using refresh token
authApi.interceptors.response.use(
  (response) => response,
  (error) => handle401(error, authApi)
);

export default authApi;
