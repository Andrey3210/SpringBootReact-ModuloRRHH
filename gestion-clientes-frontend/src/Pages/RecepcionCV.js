import React, { useState, useEffect } from "react";
import "./RecepcionCV.css";
import searchIcon from "../assets/search.png";

const RecepcionCV = () => {
  const [areas, setAreas] = useState([]);
  const [puestos, setPuestos] = useState([]);
  const [areaActiva, setAreaActiva] = useState("");
  const [puestoActivo, setPuestoActivo] = useState(null);

  const [postulantes, setPostulantes] = useState([]);
  const [loading, setLoading] = useState(false);

  // 🟢 1) Cargar todos los puestos desde el backend
  useEffect(() => {
    const cargarPuestos = async () => {
      try {
        const resp = await fetch("http://localhost:8080/RRHH/puestos");
        const data = await resp.json();

        setPuestos(data);

        // Extraer áreas únicas
        const uniqueAreas = [...new Set(data.map((p) => p.area))];
        setAreas(uniqueAreas);

        // Seleccionar la primera área automáticamente
        if (uniqueAreas.length > 0) setAreaActiva(uniqueAreas[0]);
      } catch (err) {
        console.error("❌ Error cargando puestos:", err);
      }
    };

    cargarPuestos();
  }, []);

  // 🟢 2) Cuando cambia el área → seleccionar el primer puesto
  useEffect(() => {
    if (!areaActiva) return;
    const pArea = puestos.filter((p) => p.area === areaActiva);
    if (pArea.length > 0) setPuestoActivo(pArea[0]); // Guardamos el objeto completo
  }, [areaActiva, puestos]);

  // 🟢 3) Cuando cambia el puesto activo → traer postulantes desde backend
  useEffect(() => {
    if (!puestoActivo) return;

    const cargarPostulantes = async () => {
      setLoading(true);
      try {
        const resp = await fetch(
          `http://localhost:8080/RRHH/postulantes-proceso/puesto/${puestoActivo.id_puesto}/revision-cv`
        );
        const data = await resp.json();
        setPostulantes(data);
      } catch (err) {
        console.error("❌ Error cargando postulantes:", err);
      } finally {
        setLoading(false);
      }
    };

    cargarPostulantes();
  }, [puestoActivo]);

  // Filtrar puestos visibles según área
  const puestosDeAreaActiva = puestos.filter((p) => p.area === areaActiva);

  return (
    <div className="rcv-layout">
      <div className="rcv-header">
        <h2 className="fw-bold">Recepción de CVs</h2>

        <div className="input-group shadow-sm mb-3" style={{ maxWidth: "600px" }}>
          <span className="input-group-text bg-white">
            <img src={searchIcon} alt="buscar" style={{ width: 24, height: 24 }} />
          </span>
          <input type="text" className="form-control" placeholder="Buscar postulante o puesto" />
        </div>

        {/* Áreas dinámicas */}
        <ul className="nav nav-tabs mb-2">
          {areas.map((area) => (
            <li className="nav-item" key={area}>
              <button
                className={"nav-link " + (areaActiva === area ? "active fw-bold" : "")}
                onClick={() => setAreaActiva(area)}
              >
                {area}
              </button>
            </li>
          ))}
        </ul>

        {/* Puestos dinámicos por área */}
        <ul className="nav nav-pills mb-3">
          {puestosDeAreaActiva.map((puesto) => (
            <li className="nav-item" key={puesto.id_puesto}>
              <button
                className={
                  "nav-link " +
                  (puestoActivo?.id_puesto === puesto.id_puesto
                    ? "active fw-bold bg-success"
                    : "")
                }
                onClick={() => setPuestoActivo(puesto)}
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

      {/* TABLA DE POSTULANTES */}
      <div className="rcv-table-scroll">
        {loading ? (
          <div className="text-center p-5 fw-bold">Cargando postulantes...</div>
        ) : postulantes.length === 0 ? (
          <div className="text-center p-5 fw-bold text-danger">
            No hay postulantes en revisión para este puesto
          </div>
        ) : (
          <table className="table table-striped table-bordered align-middle w-100">
            <thead className="table-success text-center sticky-thead">
              <tr>
                <th>ID postulante</th>
                <th>Nombre completo</th>
                <th>Email</th>
                <th>Estado</th>
                <th>Calificación</th>
                <th>CV</th>
              </tr>
            </thead>
            <tbody>
              {postulantes.map((p, i) => (
                <tr key={i}>
                  <td className="text-center">{p.idPostulante}</td>
                  <td>{`${p.nombres} ${p.apellidoPaterno} ${p.apellidoMaterno || ""}`}</td>
                  <td>{p.email}</td>
                  <td>{p.estado}</td>
                  <td className="text-center">{p.calificacion ?? "-"}</td>
                  <td className="text-center">
                    <button className="btn btn-success btn-sm rounded-pill px-3">Ver CV</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default RecepcionCV;
