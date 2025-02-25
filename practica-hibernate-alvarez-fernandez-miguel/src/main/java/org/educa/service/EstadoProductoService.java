package org.educa.service;

import org.educa.dao.EstadoProductoDAO;
import org.educa.dao.EstadoProductoDAOImpl;
import org.educa.entity.EstadoProductoEntity;
import org.hibernate.Session;

public class EstadoProductoService {
    private final EstadoProductoDAO estadoProductoDAO = new EstadoProductoDAOImpl();

    /**
     * Metodo para obtener el estado del producto
     * @return retorna los datos del estado que necesitamos
     */
    public EstadoProductoEntity getStatus(Session session) {
        return estadoProductoDAO.getStatus(session);
    }
}
