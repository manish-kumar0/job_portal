// frontend/employara/src/api/handle401.js

import publicApi from './publicApi';

export const handle401 = async (error, apiInstance) => {
  const originalRequest = error.config;

  if (
    (error.response?.status === 401 || error.response?.status === 403) &&
    !originalRequest._retry &&
    originalRequest.url !== '/refresh'
  ) {
    originalRequest._retry = true;

    try {
      const refreshResponse = await publicApi.post('/refresh', null, {
        withCredentials: true,
      });

      const newToken = refreshResponse.data.token;
      if (newToken) {
        localStorage.setItem('token', newToken);
        apiInstance.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
      }

      if (refreshResponse.data.user) {
        window.dispatchEvent(
          new CustomEvent('user-refreshed', {
            detail: refreshResponse.data.user,
          })
        );
      }

      return apiInstance(originalRequest);
    } catch (refreshError) {
      window.dispatchEvent(new CustomEvent('auth-error'));
      return Promise.reject(refreshError);
    }
  }

  return Promise.reject(error);
};
