import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Layout() {
  const { user, logout } = useAuth();
  const links = user?.role === "CUSTOMER"
    ? [{ to: "/customer", label: "Dashboard", icon: "⌂" }, { to: "/customer/book", label: "Book a Move", icon: "+" }]
    : user?.role === "DRIVER"
    ? [{ to: "/driver", label: "Dashboard", icon: "⌂" }]
    : [{ to: "/admin", label: "Dashboard", icon: "⌂" }];

  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="brand"><span className="brand-mark">M</span><span>Move<span>Link</span></span></div>
        <div className="top-actions">
          <span className="user-chip">{user?.email}</span>
          <button className="btn btn-ghost" onClick={logout}>Log out</button>
        </div>
      </header>
      <aside className="sidebar">
        <nav>
          {links.map(link => <NavLink key={link.to} to={link.to} className={({isActive}) => isActive ? "nav-item active" : "nav-item"}><span>{link.icon}</span>{link.label}</NavLink>)}
        </nav>
      </aside>
      <main className="main-content"><Outlet /></main>
      <nav className="mobile-nav">
        {links.map(link => <NavLink key={link.to} to={link.to} className={({isActive}) => isActive ? "mobile-nav-item active" : "mobile-nav-item"}><span>{link.icon}</span>{link.label}</NavLink>)}
        <button className="mobile-nav-item" onClick={logout}><span>↪</span>Logout</button>
      </nav>
    </div>
  );
}
