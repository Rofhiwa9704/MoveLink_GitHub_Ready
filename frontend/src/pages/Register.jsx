import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { authApi } from "../services/api";
import Toast from "../components/Toast";

export default function Register() {
  const [form, setForm] = useState({ firstName:"", lastName:"", email:"", phoneNumber:"", password:"" });
  const [busy, setBusy] = useState(false); const [toast, setToast] = useState(null);
  const navigate = useNavigate();
  const update = e => setForm({...form, [e.target.name]: e.target.value});
  const submit = async e => {
    e.preventDefault(); setBusy(true); setToast(null);
    try { await authApi.register(form); navigate("/login", { state: { registered: true } }); }
    catch(err) { setToast({type:"error", message:err.response?.data?.message || "Registration failed. Please check your information."}); }
    finally { setBusy(false); }
  };
  return <div className="auth-page">
    <div className="auth-brand"><span className="brand-mark large">M</span><div><strong>MoveLink</strong><small>Transport made simple.</small></div></div>
    <div className="auth-card">
      <div className="eyebrow">GET STARTED</div><h1>Create your account</h1><p className="muted">Join MoveLink and book reliable transport.</p>
      <form onSubmit={submit} className="form-stack">
        <div className="form-grid"><label>First name<input name="firstName" value={form.firstName} onChange={update} required /></label><label>Last name<input name="lastName" value={form.lastName} onChange={update} required /></label></div>
        <label>Email<input type="email" name="email" value={form.email} onChange={update} required /></label>
        <label>Phone number<input name="phoneNumber" value={form.phoneNumber} onChange={update} placeholder="+27..." required /></label>
        <label>Password<input type="password" name="password" value={form.password} onChange={update} minLength="6" required /></label>
        <button className="btn btn-primary btn-lg" disabled={busy}>{busy ? "Creating..." : "Create account"}</button>
      </form>
      <p className="auth-footer">Already have an account? <Link to="/login">Sign in</Link></p><Toast toast={toast} />
    </div>
  </div>;
}
