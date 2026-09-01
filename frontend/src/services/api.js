import api from "../api/axios";

const get = (url, config) => api.get(url, config).then(r => r.data);
const post = (url, data, config) => api.post(url, data, config).then(r => r.data);
const put = (url, data, config) => api.put(url, data, config).then(r => r.data);

export const authApi = {
  register: (data) => post("/auth/register", data),
  login: (data) => post("/auth/login", data),
  users: () => get("/auth/users"),
};

export const rideApi = {
  request: (data) => post("/rides/request", data),
  all: () => get("/rides"),
  pending: () => get("/rides/pending"),
  customer: (id) => get(`/rides/customer/${id}`),
  driver: (id) => get(`/rides/driver/${id}`),
  accept: (rideId, driverId) => put(`/rides/${rideId}/accept/${driverId}`),
  decline: (rideId, driverId) => put(`/rides/${rideId}/decline/${driverId}`),
  assign: (rideId, driverId) => put(`/rides/${rideId}/assign/${driverId}`),
  start: (id) => put(`/rides/${id}/start`),
  complete: (id) => put(`/rides/${id}/complete`),
  cancel: (id) => put(`/rides/${id}/cancel`),
};

export const driverApi = {
  register: (data) => post("/drivers/register", data),
  all: () => get("/drivers"),
  available: () => get("/drivers/available"),
  approve: (id) => put(`/drivers/${id}/approve`),
  verify: (id) => put(`/drivers/${id}/verify`),
  online: (id) => put(`/drivers/${id}/online`),
  offline: (id) => put(`/drivers/${id}/offline`),
  earnings: (id) => get(`/drivers/${id}/earnings`),
  location: (id) => get(`/drivers/${id}/location`),
  updateLocation: (id, data) => put(`/drivers/${id}/location`, data),
};

export const dashboardApi = {
  driver: (id) => get(`/dashboard/driver/${id}`),
};

export const adminApi = {
  dashboard: () => get("/admin/dashboard"),
  users: (params = {}) => get("/admin/users", { params }),
  searchUsers: (email) => get("/admin/users/search", { params: { email } }),
  drivers: (params = {}) => get("/admin/drivers", { params }),
  rides: (params = {}) => get("/admin/rides", { params }),
  ridesByStatus: (status) => get("/admin/rides/status", { params: { status } }),
  ratings: () => get("/admin/ratings"),
};

export const notificationApi = {
  list: (userId) => get(`/notifications/user/${userId}`),
  create: (data) => post("/notifications", data),
  read: (id) => put(`/notifications/${id}/read`),
};

export const supportApi = {
  list: (userId) => get(`/support/user/${userId}`),
  all: () => get("/support"),
  create: (data) => post("/support", data),
  reply: (id, data) => put(`/support/${id}/reply`, data),
  close: (id) => put(`/support/${id}/close`),
};

export const walletApi = {
  create: (userId) => post(`/wallet/create/${userId}`),
  deposit: (data) => post("/wallet/deposit", data),
  pay: (data) => post("/wallet/pay", data),
  refund: (data) => post("/wallet/refund", data),
  get: (userId) => get(`/wallet/${userId}`),
  transactions: (userId) => get(`/wallet/${userId}/transactions`),
};

export const paymentApi = {
  create: (data) => post("/payments", data),
  complete: (id) => put(`/payments/${id}/complete`),
  get: (id) => get(`/payments/${id}`),
};

export const ratingApi = {
  create: (data) => post("/ratings", data),
  driver: (id) => get(`/ratings/driver/${id}`),
  average: (id) => get(`/ratings/driver/${id}/average`),
};

export const reviewApi = {
  create: (data) => post("/reviews", data),
  driver: (id) => get(`/reviews/driver/${id}`),
  average: (id) => get(`/reviews/driver/${id}/average`),
};

export const quoteApi = {
  create: (data) => post("/quotes", data),
  ride: (rideId) => get(`/quotes/ride/${rideId}`),
  accept: (id) => put(`/quotes/${id}/accept`),
};

export const payoutApi = {
  create: (data) => post("/payouts", data),
  approve: (id) => put(`/payouts/${id}/approve`),
  driver: (id) => get(`/payouts/driver/${id}`),
};

export const chatApi = {
  send: (data) => post("/chat/send", data),
  history: (rideId) => get(`/chat/${rideId}`),
};

export const trackingApi = {
  update: (driverId, data) => put(`/tracking/location/${driverId}`, data),
  driver: (driverId) => get(`/tracking/driver/${driverId}`),
  nearest: (params) => get("/tracking/nearest", { params }),
};
