import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Layout from "../../components/Layout";
import StatusBadge from "../../components/StatusBadge";
import Spinner from "../../components/Spinner";
import { useAuth } from "../../context/AuthContext";
import { rideApi, notificationApi } from "../../services/api";

const money = v => v == null ? "—" : `R ${Number(v).toLocaleString("en-ZA",{minimumFractionDigits:2,maximumFractionDigits:2})}`;

export default function CustomerDashboard() {
  const { user } = useAuth(); const [rides,setRides]=useState([]); const [notifications,setNotifications]=useState([]);
  const [loading,setLoading]=useState(true); const [error,setError]=useState("");
  const load=useCallback(async()=>{try{setError("");setRides(await rideApi.customer(user.userId));}catch(e){setError(e.response?.data?.message||"Could not load your rides.");}finally{setLoading(false);}},[user.userId]);
  useEffect(()=>{load(); const id=setInterval(load,10000); return()=>clearInterval(id)},[load]);
  useEffect(()=>{notificationApi.list(user.userId).then(setNotifications).catch(()=>{})},[user.userId]);
  const cancel=async id=>{if(!confirm("Cancel this move?"))return;try{await rideApi.cancel(id);load()}catch(e){alert(e.response?.data?.message||"Unable to cancel move.")}};
  const active=rides.filter(r=>["PENDING","ACCEPTED","IN_PROGRESS"].includes(r.status)).length;
  const completed=rides.filter(r=>r.status==="COMPLETED").length;
  return <Layout><div className="page-head"><div><div className="eyebrow">CUSTOMER</div><h1>Good to see you, {user.email?.split("@")[0]}</h1><p className="muted">Manage your moves from one place.</p></div><Link className="btn btn-primary" to="/customer/book">+ Book a move</Link></div>
    <div className="stats-grid"><div className="stat-card"><span>Active moves</span><strong>{active}</strong></div><div className="stat-card"><span>Total moves</span><strong>{rides.length}</strong></div><div className="stat-card"><span>Completed</span><strong>{completed}</strong></div><div className="stat-card"><span>Notifications</span><strong>{notifications.filter(n=>!n.read).length}</strong></div></div>
    <section className="section"><div className="section-head"><h2>Your moves</h2><button className="btn btn-ghost" onClick={load}>Refresh</button></div>
    {loading?<Spinner text="Loading your moves..." />:error?<div className="empty error-box">{error}<button className="btn btn-ghost" onClick={load}>Try again</button></div>:rides.length===0?<div className="empty"><div className="empty-icon">📦</div><h3>No moves yet</h3><p>Tell us what you need moved and we'll take it from there.</p><Link className="btn btn-primary" to="/customer/book">Book your first move</Link></div>:
    <div className="card-list">{rides.map(r=><article className="ride-card" key={r.id}><div className="ride-top"><div><span className="ride-id">MOVE #{r.id}</span><h3>{r.pickupLocation} <span className="arrow">→</span> {r.destination}</h3></div><StatusBadge status={r.status}/></div><div className="ride-meta"><span>🚚 {r.requiredVehicleType?.replaceAll("_"," ")}</span><span>⚖ {r.estimatedWeight ?? "—"} kg</span><span>👥 {r.helpersRequired ?? 0} helpers</span>{r.fare!=null&&<span>💰 {money(r.fare)}</span>}</div>{r.driver&&<div className="driver-box"><div className="avatar">{r.driver.firstName?.[0]||"D"}</div><div><strong>{r.driver.firstName} {r.driver.lastName}</strong><small>{r.driver.vehicleModel||"Driver"} · {r.driver.vehiclePlateNumber||"Plate pending"}</small></div></div>}<div className="ride-actions">{["PENDING","ACCEPTED"].includes(r.status)&&<button className="btn btn-danger" onClick={()=>cancel(r.id)}>Cancel</button>}{r.status==="IN_PROGRESS"&&<span className="live-text">● Your move is in progress</span>}</div></article>)}</div>}</section>
  </Layout>;
}
