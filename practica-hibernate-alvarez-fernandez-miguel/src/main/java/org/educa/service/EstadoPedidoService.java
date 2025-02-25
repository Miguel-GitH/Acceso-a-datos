package org.educa.service;

import org.educa.dao.EstadoPedidoDAO;
import org.educa.dao.EstadoPedidoDAOImpl;
import org.educa.entity.EstadoPedidoEntity;

public class EstadoPedidoService {
private final EstadoPedidoDAO estadoProductoDAO = new EstadoPedidoDAOImpl();

    /**
     * Metodo para obtener el estado del producto
     * @return retorna el estado de producto que vayamos a utilizar
     */
    public EstadoPedidoEntity getStatus() {
        return estadoProductoDAO.getStatus();
    }
}
