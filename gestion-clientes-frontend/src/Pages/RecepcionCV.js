// RecepcionCV.js
import React, { useState, useEffect } from "react";
import "./RecepcionCV.css";
import searchIcon from "../assets/search.png";

const RecepcionCV = () => {
  const [areas, setAreas] = useState([]);
  const [puestos, setPuestos] = useState([]);
  const [areaActiva, setAreaActiva] = useState("");
  const [puestoActivo, setPuestoActivo] = useState("");

  // 👉 Cargar puestos desde el backend
  useEffect(() => {
    const cargarPuestos = async () => {
      try {
        const resp = await fetch("http://localhost:8080/api/puestos");
        const data = await resp.json();

        setPuestos(data);

        // Áreas únicas desde la BD
        const uniqueAreas = [...new Set(data.map((p) => p.area))];
        setAreas(uniqueAreas);

        // Área por defecto
        if (uniqueAreas.length > 0) {
          setAreaActiva(uniqueAreas[0]);
        }
      } catch (error) {
        console.error("Error cargando puestos:", error);
      }
    };

    cargarPuestos();
  }, []);

  // ⭐ Cuando cambia el área (o llegan nuevos puestos),
  //    seleccionamos automáticamente el PRIMER puesto de esa área
  useEffect(() => {
    if (!areaActiva) return;
    const puestosArea = puestos.filter((p) => p.area === areaActiva);
    if (puestosArea.length > 0) {
      setPuestoActivo(puestosArea[0].nombre_puesto);
    } else {
      setPuestoActivo("");
    }
  }, [areaActiva, puestos]);

  // Puestos filtrados según área activa
  const puestosDeAreaActiva = puestos.filter((p) => p.area === areaActiva);

  const candidatos = Array(18).fill({
    id: 1,
    nombre: "Marco Aurelio Huaman Denegri",
    email: "marco.huaman@gmail.com",
    telefono: "+51 985 628 455",
    edad: 45,
    sexo: "Masculino",
    nivel: "Técnico",
    experiencia: "2 años",
  });

  return (
    <div className="rcv-layout">
      {/* HEADER FIJO */}
      <div className="rcv-header">
        <h2 className="fw-bold">Recepción de CVs</h2>

        <div className="input-group shadow-sm mb-3" style={{ maxWidth: "600px" }}>
          <span className="input-group-text bg-white">
            <img src={searchIcon} alt="buscar" style={{ width: 24, height: 24 }} />
          </span>
          <input
            type="text"
            className="form-control"
            placeholder="Buscar postulante o puesto"
          />
        </div>

        {/* TABS DE ÁREAS (desde BD) */}
        <ul className="nav nav-tabs mb-2">
          {areas.map((area) => (
            <li className="nav-item" key={area}>
              <button
                className={
                  "nav-link " + (areaActiva === area ? "active fw-bold" : "")
                }
                onClick={() => setAreaActiva(area)}
              >
                {area}
              </button>
            </li>
          ))}
        </ul>

        {/* PILLS DE PUESTOS SEGÚN ÁREA ACTIVA (desde BD) */}
        <ul className="nav nav-pills mb-3">
          {puestosDeAreaActiva.map((puesto) => (
            <li className="nav-item" key={puesto.id_puesto}>
              <button
                className={
                  "nav-link " +
                  (puestoActivo === puesto.nombre_puesto
                    ? "active fw-bold bg-success"
                    : "")
                }
                onClick={() => setPuestoActivo(puesto.nombre_puesto)}
              >
                {puesto.nombre_puesto}
              </button>
            </li>
          ))}
        </ul>

        <div className="d-flex justify-content-end mb-1">
          <button className="btn btn-light border d-flex align-items-center">
            <i className="bi bi-funnel me-2"></i> Todos
          </button>
        </div>
      </div>

      {/* ZONA SCROLLABLE */}
      <div className="rcv-table-scroll">
        <table className="table table-striped table-bordered align-middle w-100">
          <thead className="table-success text-center sticky-thead">
            <tr>
              <th>ID postulante</th>
              <th>Nombres completos</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Edad</th>
              <th>Sexo</th>
              <th>Nivel de estudios</th>
              <th>Experiencia</th>
              <th>CV</th>
            </tr>
          </thead>
          <tbody>
            {candidatos.map((c, i) => (
              <tr key={i}>
                <td className="text-center">{c.id}</td>
                <td>{c.nombre}</td>
                <td>{c.email}</td>
                <td>{c.telefono}</td>
                <td>{c.edad}</td>
                <td>{c.sexo}</td>
                <td>{c.nivel}</td>
                <td>{c.experiencia}</td>
                <td className="text-center">
                  <button className="btn btn-success btn-sm rounded-pill px-3">
                    Ver CV
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RecepcionCV;
