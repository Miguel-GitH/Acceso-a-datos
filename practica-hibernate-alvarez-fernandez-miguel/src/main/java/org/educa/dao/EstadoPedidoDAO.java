package org.educa.dao;

import org.educa.entity.EstadoPedidoEntity;

public interface EstadoPedidoDAO {
    /**
     * Metodo utilizado para obtener el estado del pedido que se va a vender
     * @return Devolvemos los datos del estado del pedido
     */
    EstadoPedidoEntity getStatus();
}
