package org.educa.dao;

import org.educa.entity.PedidoEntity;
import org.hibernate.Session;

public interface PedidoDAO {
    /**
     * Metodo que utilizamos para insertar el pedido con los datos del producto y cliente dados
     *
     * @param pedido  Le pasamos los datos del pedido para insertarlo en la base de datos
     * @param session sesion utilizada para insertar en la base de datos y más tarde hacer rollback
     *                en caso de error
     */
    void insertarPedido(PedidoEntity pedido, Session session);
}
