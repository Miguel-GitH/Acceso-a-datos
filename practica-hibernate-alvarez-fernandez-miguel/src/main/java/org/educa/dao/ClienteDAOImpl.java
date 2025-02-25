package org.educa.dao;

import org.educa.dao.hibernate.DAOSession;
import org.educa.entity.ClienteEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public ClienteEntity login(String dni, String password) {
        try(Session session = DAOSession.getSession()){
            Query<ClienteEntity> query = session.createQuery("from ClienteEntity where dni = :dni " +
                    "and pass = :pass", ClienteEntity.class)
                    .setParameter("dni", dni).setParameter("pass", password)
                    .setReadOnly(true);
            ClienteEntity cliente = query.uniqueResult();
            if(cliente != null) {
                Hibernate.initialize(cliente.getDirecciones());
            }
            return cliente;
        }
    }
}
