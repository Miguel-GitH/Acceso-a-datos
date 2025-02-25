package org.educa.dao;

import org.educa.entity.EstadoProductoEntity;
import org.hibernate.Session;

public interface EstadoProductoDAO {
    /**
     * Metodo utilizado para devolver el estado del producto que estemos vendiendo
     * @return Devolvemos los datos de ese estado seleccionado
     */
    EstadoProductoEntity getStatus(Session session);
}
