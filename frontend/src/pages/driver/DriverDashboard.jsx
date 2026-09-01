import { useCallback,useEffect,useState } from "react";
import Layout from "../../components/Layout";
import StatusBadge from "../../components/StatusBadge";
import Spinner from "../../components/Spinner";
import { useAuth } from "../../context/AuthContext";
import { dashboardApi, driverApi, rideApi } from "../../services/api";

const money=v=>`R ${Number(v||0).toLocaleString("en-ZA",{minimumFractionDigits:2,maximumFractionDigits:2})}`;

export default function DriverDashboard(){
 const {user}=useAuth();const id=Number(user.userId);const [pending,setPending]=useState([]),[mine,setMine]=useState([]),[stats,setStats]=useState(null),[earnings,setEarnings]=useState(0),[online,setOnline]=useState(false),[loading,setLoading]=useState(true);
 const load=useCallback(async()=>{try{const [p,m,s,e,d]=await Promise.all([rideApi.pending(),rideApi.driver(id),dashboardApi.driver(id),driverApi.earnings(id),driverApi.location(id).catch(()=>null)]);setPending(p);setMine(m);setStats(s);setEarnings(e||0);setOnline(Boolean(d?.available));}catch(e){console.error(e)}finally{setLoading(false)}},[id]);
 useEffect(()=>{load();const t=setInterval(load,10000);return()=>clearInterval(t)},[load]);
 const action=async(fn,msg)=>{try{await fn();await load()}catch(e){alert(e.response?.data?.message||msg)}};
 const toggle=()=>action(()=>online?driverApi.offline(id):driverApi.online(id),"Could not change your availability.");
 return <Layout><div className="page-head"><div><div className="eyebrow">DRIVER</div><h1>Driver dashboard</h1><p className="muted">Find jobs, manage your moves and track your earnings.</p></div><button onClick={toggle} className={online?"btn btn-success":"btn btn-dark"}>{online?"● Online":"○ Go online"}</button></div>
 <div className="stats-grid"><div className="stat-card"><span>Available jobs</span><strong>{pending.length}</strong></div><div className="stat-card"><span>My moves</span><strong>{mine.length}</strong></div><div className="stat-card"><span>Earnings</span><strong>{money(earnings)}</strong></div><div className="stat-card"><span>Rating</span><strong>★ {stats?.averageRating?.toFixed?.(1)||"—"}</strong></div></div>
 <section className="section"><div className="section-head"><h2>Available jobs</h2><button className="btn btn-ghost" onClick={load}>Refresh</button></div>{loading?<Spinner/>:pending.length===0?<div className="empty"><div className="empty-icon">🚚</div><h3>No available jobs</h3><p>Stay online and new requests will appear here.</p></div>:<div className="card-list">{pending.map(r=><article className="ride-card" key={r.id}><div className="ride-top"><div><span className="ride-id">JOB #{r.id}</span><h3>{r.pickupLocation} <span className="arrow">→</span> {r.destination}</h3></div><StatusBadge status={r.status}/></div><div className="ride-meta"><span>🚚 {r.requiredVehicleType?.replaceAll("_"," ")}</span><span>⚖ {r.estimatedWeight||0} kg</span><span>👥 {r.helpersRequired||0} helpers</span></div><p className="description">{r.loadDescription}</p><div className="ride-actions"><button className="btn btn-primary" onClick={()=>action(()=>rideApi.accept(r.id,id),"Unable to accept job.")}>Accept job</button><button className="btn btn-danger" onClick={()=>action(()=>rideApi.decline(r.id,id),"Unable to decline job.")}>Decline</button></div></article>)}</div>}</section>
 <section className="section"><div className="section-head"><h2>My moves</h2></div><div className="card-list">{mine.length===0?<div className="empty">No assigned moves yet.</div>:mine.map(r=><article className="ride-card compact" key={r.id}><div className="ride-top"><div><span className="ride-id">MOVE #{r.id}</span><h3>{r.pickupLocation} <span className="arrow">→</span> {r.destination}</h3></div><StatusBadge status={r.status}/></div><div className="ride-actions">{r.status==="ACCEPTED"&&<button className="btn btn-primary" onClick={()=>action(()=>rideApi.start(r.id),"Unable to start move.")}>Start move</button>}{r.status==="IN_PROGRESS"&&<button className="btn btn-success" onClick={()=>action(()=>rideApi.complete(r.id),"Unable to complete move.")}>Complete move</button>}</div></article>)}</div></section>
 </Layout>;
}
