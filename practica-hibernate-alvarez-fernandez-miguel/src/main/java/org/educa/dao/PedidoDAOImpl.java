package org.educa.dao;

import org.educa.entity.PedidoEntity;
import org.hibernate.Session;

public class PedidoDAOImpl implements PedidoDAO {
    @Override
    public void insertarPedido(PedidoEntity pedido, Session session) {
            session.merge(pedido);
    }
}
