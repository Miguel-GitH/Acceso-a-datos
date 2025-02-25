package org.educa.dao;

import org.educa.dao.hibernate.DAOSession;
import org.educa.entity.EstadoProductoEntity;
import org.educa.entity.ProductoEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public List<ProductoEntity> findAllProducts() {
        try (Session session = DAOSession.getSession()) {
            Query<ProductoEntity> query = session.createQuery(
                    "from ProductoEntity", ProductoEntity.class);
            return query.list();
        }
    }

    @Override
    public ProductoEntity getFirstProductByNameTallaAndColor(String nombre, String talla, String color) {
        try(Session session = DAOSession.getSession()){
            Query<ProductoEntity> query = session.createQuery("from ProductoEntity where nombre = :nombre and talla = :talla and color = :color", ProductoEntity.class)
                    .setParameter("nombre", nombre)
                    .setParameter("talla", talla)
                    .setParameter("color",color).setMaxResults(1);
            return query.uniqueResult();
        }
    }

    @Override
    public List<ProductoEntity> findByName(ProductoEntity producto) {
        try(Session session = DAOSession.getSession()){
            Query<ProductoEntity> query = session.createQuery("from ProductoEntity where" +
                    " nombre = :nombre", ProductoEntity.class)
                    .setParameter("nombre", producto.getNombre());
            return query.list();
        }
    }

    @Override
    public void changeStatus(EstadoProductoEntity estadoProductoEntity, List<ProductoEntity> productos, Session session) {
    try {
        productos.forEach(productoEntity -> {
            session.createMutationQuery("update ProductoEntity set " +
                            "estadoProducto = :estado where id = :id")
                    .setParameter("estado", estadoProductoEntity)
                    .setParameter("id", productoEntity.getId()).executeUpdate();
        });
        session.getTransaction().commit();
    } catch (Exception e) {
        session.getTransaction().rollback();
        throw new RuntimeException(e);
    }
    }
}
