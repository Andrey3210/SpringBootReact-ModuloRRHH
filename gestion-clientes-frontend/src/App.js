import logo from './logo.svg';
import './App.css';
import ListClientesComponent from './components/ListClientesComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import AddClienteComponent from './components/AddClienteComponent';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div>
      <BrowserRouter>
        <HeaderComponent />
        <div className='container'>
          <Routes>
            <Route exact path="/" element={<ListClientesComponent />} />
            <Route path="/clientes" element={<ListClientesComponent />} />
            <Route path="/add-cliente" element={<AddClienteComponent />} />
            <Route path="/edit-cliente/:id" element={<AddClienteComponent />} />
          </Routes>
        </div>
        <FooterComponent />
      </BrowserRouter>
    </div>
  );
}

export default App;
