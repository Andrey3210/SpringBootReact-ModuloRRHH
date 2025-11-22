import React, {useEffect, useState} from 'react'
import ClienteService from '../services/ClienteService';
import { Link } from 'react-router-dom';

export const ListClientesComponent = () => {
  const [empleados, setEmpleados] = useState([]);

  useEffect(() => {
    listarClientes();
  },[])

  const deleteCliente = (clienteId) => {
    ClienteService.deleteCliente(clienteId).then( response => {
        listarClientes();
    }).catch(error => {
        console.log("Error al eliminar el cliente", error);
    });
  }

  const listarClientes = () => {
    ClienteService.getAllClientes().then(response => {
        setEmpleados(response.data);
    }).catch(error => {
        console.log("Error al obtener la lista de clientes", error);
    });
  }

  return (
    <div className='container' style={{marginTop: '20px'}}>
      <h2 className='text-center'>Lista de Empleados</h2>
      <Link to="/add-cliente" className="btn btn-primary mb-2">Agregar Cliente</Link>
      <table className='table table-bordered table-striped'>
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
            {
                empleados.map(empleado => 
                    <tr key={empleado.id}>
                        <td>{empleado.id}</td>
                        <td>{empleado.nombre}</td>
                        <td>{empleado.apellido}</td>
                        <td>{empleado.email}</td>
                        <td>
                            <Link className='btn btn-info' to={`/edit-cliente/${empleado.id}`}>Editar</Link>
                            <button style={{marginLeft: "10px"}} className='btn btn-danger' onClick={() => deleteCliente(empleado.id)}>Eliminar</button>
                        </td>
                    </tr>
                )
            }
        </tbody>
      </table>
    </div>
  )
}

export default ListClientesComponent;
