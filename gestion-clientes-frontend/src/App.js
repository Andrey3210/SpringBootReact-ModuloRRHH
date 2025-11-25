import logo from './logo.svg';
import './App.css';
import ListClientesComponent from './components/ListClientesComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import AddClienteComponent from './components/AddClienteComponent';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Home from './Pages/Home';
import RecepcionCV from './Pages/RecepcionCV';
import DetalleCV from "./Pages/DetalleCV";

function App() {
  return (
    <BrowserRouter>
      <div style={{ display: "flex" }}>
        <Sidebar />
        <div style={{ marginLeft: "70px", padding: "20px" }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/recepcion-cv" element={<RecepcionCV />} />
            <Route path="/reclutamiento" element={<Home />} />
            <Route path="/asistencia" element={<Home />} />
            <Route path="/incentivos" element={<Home />} />
            <Route path="/vacaciones" element={<Home />} />
            <Route path="/gestion" element={<Home />} />
            <Route path="/rrhh/postulantes/:idPostulante/cv" element={<DetalleCV />}/>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
