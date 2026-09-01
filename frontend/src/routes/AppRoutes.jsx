import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Register from "../pages/Register";
import CustomerDashboard from "../pages/customer/CustomerDashboard";
import BookRide from "../pages/customer/BookRide";
import DriverDashboard from "../pages/driver/DriverDashboard";
import AdminDashboard from "../pages/admin/AdminDashboard";
import ProtectedRoute from "../components/ProtectedRoute";
import { useAuth } from "../context/AuthContext";

function HomeRedirect(){const {user}=useAuth();if(!user)return <Navigate to="/login" replace/>;return <Navigate to={user.role==="DRIVER"?"/driver":user.role==="ADMIN"?"/admin":"/customer"} replace/>}

export default function AppRoutes(){
 return <BrowserRouter><Routes>
  <Route path="/" element={<HomeRedirect/>}/><Route path="/login" element={<Login/>}/><Route path="/register" element={<Register/>}/>
  <Route element={<ProtectedRoute roles={["CUSTOMER"]}/>}><Route path="/customer" element={<CustomerDashboard/>}/><Route path="/customer/book" element={<BookRide/>}/></Route>
  <Route element={<ProtectedRoute roles={["DRIVER"]}/>}><Route path="/driver" element={<DriverDashboard/>}/></Route>
  <Route element={<ProtectedRoute roles={["ADMIN"]}/>}><Route path="/admin" element={<AdminDashboard/>}/></Route>
  <Route path="*" element={<HomeRedirect/>}/>
 </Routes></BrowserRouter>
}
