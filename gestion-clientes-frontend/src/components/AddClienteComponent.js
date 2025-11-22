import React, { useEffect } from 'react'
import { useState } from 'react';
import ClienteService from '../services/ClienteService';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

export const AddClienteComponent = () => {
  const [nombre, setNombre] = useState('');
  const [apellido, setApellido] = useState('');
  const [email, setEmail] = useState('');
  const navigate = useNavigate();
  const {id} = useParams();

  const saveOrUpdateCliente = (e) => {
    e.preventDefault();
    const cliente = {nombre, apellido, email};

    if(id) {
        ClienteService.updateCliente(id, cliente).then((response) => {
            console.log("Cliente actualizado exitosamente", response.data);
            navigate('/clientes');
        }).catch(error => {
            console.log("Error al actualizar el cliente", error);
        });
    }
    else {
        ClienteService.saveCliente(cliente).then((response) => {
            console.log("Cliente registrado exitosamente", response.data);
            navigate('/clientes');
        }).catch(error => {
            console.log("Error al registrar el cliente", error);
        });
    }
    
  }

  const title = () => {
    if(id){
        return <h2 className='text-center'>Actualizar Cliente</h2>
    }
    return <h2 className='text-center'>Registrar Cliente</h2>
  }

  useEffect(() => {
    ClienteService.getClienteById(id).then((response) => {
        setNombre(response.data.nombre);
        setApellido(response.data.apellido);
        setEmail(response.data.email);
    }).catch(error => {
        console.log("Error al obtener el cliente por ID", error);
    });
  }, [id]);

  return (
    <div>
        <div className='container' style={{marginTop: '20px'}}>
            <div className='row'>
                <div className='card col-md-6 offset-md-3 offset-md-3'>
                    <h2 className='text-center'>Registro de Clientes</h2>
                    <div className='card-body'>
                        <form>
                            <div className='form-group mb-2'>
                                <label className='form-label'> Nombre: </label>
                                <input 
                                    type='text'
                                    className='form-control'
                                    placeholder='Digite su nombre'
                                    name='nombre'
                                    value={nombre}
                                    onChange={(e) => setNombre(e.target.value)}
                                />
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label'> Apellido: </label>
                                <input 
                                    type='text'
                                    className='form-control'
                                    placeholder='Digite su apellido'
                                    name='apellido'
                                    value={apellido}
                                    onChange={(e) => setApellido(e.target.value)}
                                />
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label'> Email: </label>
                                <input 
                                    type='email'
                                    className='form-control'
                                    placeholder='Digite su email'
                                    name='email'
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <button className='btn btn-success' onClick={saveOrUpdateCliente}>Guardar</button>
                            &nbsp;
                            <Link to='/clientes' className='btn btn-danger'>Cancelar</Link>
                        </form>
                    </div>  
                </div>
            </div>
        </div>
    </div>
  )
}

export default AddClienteComponent;