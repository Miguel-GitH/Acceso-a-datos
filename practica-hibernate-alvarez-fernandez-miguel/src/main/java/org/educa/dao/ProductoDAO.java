package org.educa.dao;

import org.educa.entity.EstadoProductoEntity;
import org.educa.entity.ProductoEntity;
import org.hibernate.Session;

import java.util.List;

public interface ProductoDAO {
    /**
     * Metodo utilizado para encontrar todos los productos de la base de datos
     * @return Devolvemos los productos encontrados en la base de datos
     */
    List<ProductoEntity> findAllProducts();

    /**
     * Metodo utilizado para sacar el primer producto según su nombre, talla y color
     * @param nombre nombre seleccionado por el usuario en el prompt
     * @param talla talla seleccionada por el usuario en el prompt
     * @param color color seleccionado por el usuario en el prompt
     * @return retorna los datos de ese producto seleccionado anteriormente
     */
    ProductoEntity getFirstProductByNameTallaAndColor(String nombre, String talla, String color);

    /**
     * Metodo utilizado para encontrar el producto por el nombre
     * @param producto Se le pasan los datos del producto para poder utilizar el nombre
     * @return retorna los datos seleccionados según el nombre del producto
     */
    List<ProductoEntity> findByName(ProductoEntity producto);

    /**
     * Metodo utilizado para cambiar el estado de del producto
     * @param estadoProductoEntity Le paso el estado del producto para poder actualizarlo en la base de datos
     * @param productos Lista de productos a actualizar el estado con esto se tomará la id del producto
     * @param session Sesion de la cual vamos a hacer uso para luego poder hacer un rollback en caso de que algo salga mal
     */
    void changeStatus(EstadoProductoEntity estadoProductoEntity, List<ProductoEntity> productos, Session session);
}
