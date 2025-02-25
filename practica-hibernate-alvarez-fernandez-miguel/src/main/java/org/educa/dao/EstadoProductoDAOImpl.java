package org.educa.dao;

import org.educa.entity.EstadoProductoEntity;
import org.hibernate.Session;

public class EstadoProductoDAOImpl implements EstadoProductoDAO {
    @Override
    public EstadoProductoEntity getStatus(Session session) {
            return session.get(EstadoProductoEntity.class,2);
    }
}
