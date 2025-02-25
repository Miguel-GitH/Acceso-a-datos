package org.educa.dao.hibernate;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

@Getter
@Setter
public class DAOSession {
    private static Session session;

    private DAOSession() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Session getSession() {
        if (session == null || !session.isOpen()) {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
        }
        return session;
    }
}
