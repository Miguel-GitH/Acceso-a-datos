package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteEntity;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    /**
     * Metodo para verificar la identidad del usuario
     * @param dni dni introducido por el usuario
     * @param password contraseña introducida por el usuario
     * @return retorna los datos del cliente que hace login
     */
    public ClienteEntity login(String dni, String password) {
        return clienteDAO.login(dni,password);
    }
}
