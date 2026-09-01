import { useState } from "react";
import { Link, Navigate, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import Toast from "../components/Toast";

export default function Login() {
  const { user, login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [busy, setBusy] = useState(false);
  const [toast, setToast] = useState(null);

  if (user) return <Navigate to={user.role === "DRIVER" ? "/driver" : user.role === "ADMIN" ? "/admin" : "/customer"} replace />;

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true); setToast(null);
    try {
      const data = await login(email.trim(), password);
      const fallback = data.role === "DRIVER" ? "/driver" : data.role === "ADMIN" ? "/admin" : "/customer";
      navigate(location.state?.from?.pathname || fallback, { replace: true });
    } catch (err) {
      setToast({ type: "error", message: err.response?.data?.message || "Unable to sign in. Check your details and try again." });
    } finally { setBusy(false); }
  };

  return <div className="auth-page">
    <div className="auth-brand"><span className="brand-mark large">M</span><div><strong>MoveLink</strong><small>Move smarter. Move safely.</small></div></div>
    <div className="auth-card">
      <div className="eyebrow">WELCOME BACK</div>
      <h1>Sign in to MoveLink</h1>
      <p className="muted">Book moves, manage deliveries and stay connected.</p>
      <form onSubmit={submit} className="form-stack">
        <label>Email<input type="email" value={email} onChange={e=>setEmail(e.target.value)} placeholder="you@example.com" required autoComplete="email" /></label>
        <label>Password<input type="password" value={password} onChange={e=>setPassword(e.target.value)} placeholder="Your password" required autoComplete="current-password" /></label>
        <button className="btn btn-primary btn-lg" disabled={busy}>{busy ? "Signing in..." : "Sign in"}</button>
      </form>
      <p className="auth-footer">Don't have an account? <Link to="/register">Create one</Link></p>
      <Toast toast={toast} />
    </div>
  </div>;
}
