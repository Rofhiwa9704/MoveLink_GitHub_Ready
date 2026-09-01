import { useEffect,useState } from "react";
import Layout from "../../components/Layout";
import Spinner from "../../components/Spinner";
import StatusBadge from "../../components/StatusBadge";
import { adminApi, driverApi, rideApi } from "../../services/api";

export default function AdminDashboard(){
 const [data,setData]=useState(null),[users,setUsers]=useState([]),[drivers,setDrivers]=useState([]),[rides,setRides]=useState([]),[tab,setTab]=useState("overview"),[loading,setLoading]=useState(true);
 const load=async()=>{setLoading(true);try{const [d,u,dr,r]=await Promise.all([adminApi.dashboard(),adminApi.users({page:0,size:20}),adminApi.drivers({page:0,size:20}),adminApi.rides({page:0,size:20})]);setData(d);setUsers(u.content||[]);setDrivers(dr.content||[]);setRides(r.content||[])}catch(e){alert(e.response?.data?.message||"Unable to load admin data.")}finally{setLoading(false)}};
 useEffect(()=>{load()},[]);
 if(loading)return <Layout><Spinner text="Loading admin console..." /></Layout>;
 return <Layout><div className="page-head"><div><div className="eyebrow">ADMIN</div><h1>Operations console</h1><p className="muted">Monitor MoveLink activity and manage the platform.</p></div><button className="btn btn-ghost" onClick={load}>Refresh</button></div>
 <div className="stats-grid admin-stats">{[["Users",data?.totalUsers],["Drivers",data?.totalDrivers],["Available drivers",data?.availableDrivers],["Total moves",data?.totalRides],["Pending",data?.pendingRides],["In progress",data?.inProgressRides],["Completed",data?.completedRides],["Revenue",`R ${Number(data?.totalRevenue||0).toLocaleString("en-ZA")}`]].map(([l,v])=><div className="stat-card" key={l}><span>{l}</span><strong>{v??0}</strong></div>)}</div>
 <div className="tabs">{["overview","users","drivers","rides"].map(t=><button key={t} className={tab===t?"tab active":"tab"} onClick={()=>setTab(t)}>{t}</button>)}</div>
 {tab==="overview"&&<section className="panel"><h2>Platform health</h2><div className="health-grid"><div><span>Verified drivers</span><strong>{data?.verifiedDrivers||0}</strong></div><div><span>Payments</span><strong>{data?.totalPayments||0}</strong></div><div><span>Ratings</span><strong>{data?.totalRatings||0}</strong></div><div><span>Open support tickets</span><strong>{data?.openSupportTickets||0}</strong></div></div></section>}
 {tab==="users"&&<DataTable headers={["ID","Name","Email","Phone","Role"]} rows={users.map(u=>[u.id,`${u.firstName||""} ${u.lastName||""}`,u.email,u.phoneNumber,u.role])}/>}
 {tab==="drivers"&&<DriverTable drivers={drivers} onVerify={async id=>{try{await driverApi.verify(id);load()}catch(e){alert(e.response?.data?.message||"Unable to verify driver.")}}}/>}
 {tab==="rides"&&<DataTable headers={["ID","Route","Status","Fare"]} rows={rides.map(r=>[r.id,`${r.pickupLocation} → ${r.destination}`,<StatusBadge status={r.status}/>,r.fare!=null?`R ${r.fare}`:"—"])}/>}
 </Layout>
}
function DataTable({headers,rows}){return <div className="table-wrap"><table><thead><tr>{headers.map(h=><th key={h}>{h}</th>)}</tr></thead><tbody>{rows.length?rows.map((r,i)=><tr key={i}>{r.map((x,j)=><td key={j}>{x}</td>)}</tr>):<tr><td colSpan={headers.length}>No records found.</td></tr>}</tbody></table></div>}
function DriverTable({drivers,onVerify}){return <div className="table-wrap"><table><thead><tr><th>ID</th><th>Vehicle</th><th>Plate</th><th>Verified</th><th>Available</th><th>Action</th></tr></thead><tbody>{drivers.map(d=><tr key={d.id}><td>{d.id}</td><td>{d.vehicleModel||d.vehicleType||"—"}</td><td>{d.vehiclePlateNumber||"—"}</td><td>{d.verified?"Yes":"No"}</td><td>{d.available?"Online":"Offline"}</td><td>{!d.verified&&<button className="btn btn-small btn-primary" onClick={()=>onVerify(d.id)}>Verify</button>}</td></tr>)}</tbody></table></div>}
