// Sidebar.js
import React from "react";
import { NavLink } from "react-router-dom";
import "./Sidebar.css";

import iconHome from "../assets/Home.png";
import iconRecepcionCV from "../assets/recepcionCV.png";
import iconReclutamiento from "../assets/reclutamiento.png";
import iconAsistencia from "../assets/asistencia.png";
import iconIncentivos from "../assets/incentivos.png";
import iconVacaciones from "../assets/vacaciones.png";
import iconGestion from "../assets/gestion.png";

const Sidebar = () => {
  const items = [
    { id: "home", icon: iconHome, to: "/" },
    { id: "recepcionCV", icon: iconRecepcionCV, to: "/recepcion-cv" },
    { id: "reclutamiento", icon: iconReclutamiento, to: "/reclutamiento" },
    { id: "asistencia", icon: iconAsistencia, to: "/asistencia" },
    { id: "incentivos", icon: iconIncentivos, to: "/incentivos" },
    { id: "vacaciones", icon: iconVacaciones, to: "/vacaciones" },
    { id: "gestion", icon: iconGestion, to: "/gestion" },
  ];

  return (
    <div className="sidebar-container">
      {items.map((item) => (
        <NavLink
          key={item.id}
          to={item.to}
          end={item.to === "/"}
          className={({ isActive }) =>
            "sidebar-item" + (isActive ? " active" : "")
          }
        >
          <img src={item.icon} alt={item.id} className="sidebar-icon" />
        </NavLink>
      ))}
    </div>
  );
};

export default Sidebar;
