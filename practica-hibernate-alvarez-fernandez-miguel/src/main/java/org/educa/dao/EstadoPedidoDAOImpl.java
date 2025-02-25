package org.educa.dao;

import org.educa.dao.hibernate.DAOSession;
import org.educa.entity.EstadoPedidoEntity;
import org.hibernate.Session;

public class EstadoPedidoDAOImpl implements EstadoPedidoDAO {
    @Override
    public EstadoPedidoEntity getStatus() {
        try(Session session = DAOSession.getSession()){
            int preparando = 1;
            return session.get(EstadoPedidoEntity.class,preparando);
        }
    }
}
