package org.educa.service;

import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.EstadoProductoEntity;
import org.educa.entity.ProductoEntity;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    private final EstadoProductoService estadoProductoService = new EstadoProductoService();

    /**
     * Metodo que utilizo para calcular el precio final con el precio y el descuento de la base
     * de datos comprobando si tiene o no.
     * Aprovecho el if para hacer un clave valor haciendo uso de un HashMap dandole como clave el
     * nombre y si se repite entonces no meterlo en la lista esto nos servira como filtro para no
     * sacar repetidos
     * @return Una lista filtrada con todos los productos disponibles
     */
    public List<ProductoEntity> findAllProducts() {
        List<ProductoEntity> lista = productoDAO.findAllProducts();
        Map<String, ProductoEntity> listaAgrupada = new HashMap<>();
        for(ProductoEntity producto : lista){
            if (producto.getDescuento() != null) {
                    BigDecimal descuentoPorcentaje = producto.getDescuento().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    BigDecimal descuentoTotal = producto.getPrecio().multiply(descuentoPorcentaje);
                    producto.setPrecioFinal(producto.getPrecio().subtract(descuentoTotal).setScale(2, RoundingMode.HALF_UP));
            } else {
                producto.setPrecioFinal(producto.getPrecio().setScale(2, RoundingMode.HALF_UP));
            }
            if(!listaAgrupada.containsKey(producto.getNombre())){
                listaAgrupada.put(producto.getNombre(),producto);
            }
        }
        return new ArrayList<>(listaAgrupada.values());
    }

    /**
     * Metodo para obtener el primer producto que coincida con lo que el usuario quiera
     * @param nombre Nombre seleccionado por el usuario interactivamente
     * @param talla Talla seleccionada por el usuario interactivamente
     * @param color Color seleccionado por el usuario interactivamente
     * @return Retorna el primer producto que encuentre con los datos entregados por el usuario
     * @throws SQLException
     */
    public ProductoEntity getFirstProductoByNameTallaAndColor(String nombre, String talla, String color) throws SQLException {
        return productoDAO.getFirstProductByNameTallaAndColor(nombre,talla,color);
    }

    /**
     * Metodo que encuentra por nombre y que agrupa haciendo uso de un modelo clave valor.
     * La clave es el nombre, talla, color en este caso y se le introduce en el caso de que no este
     * @param producto Datos del producto que sacaremos a partir del nombre que le demos
     * @return Devuelve los productos ya agrupados una vez pasan por el filtro
     * @throws SQLException
     */
    public List<ProductoEntity> findByName(ProductoEntity producto) throws SQLException {
        List<ProductoEntity> lista = productoDAO.findByName(producto);
        Map<String, ProductoEntity> listaAgrupada = new HashMap<>();
        lista.forEach(productoEntity -> {
            String clave = productoEntity.getNombre() + productoEntity.getTalla() + productoEntity.getColor();
            listaAgrupada.putIfAbsent(clave,productoEntity);
        });
        return new ArrayList<>(listaAgrupada.values());
    }

    /**
     * Metodo utilizado para cambiar el estado del producto comprado
     *
     * @param productos Lista de productos comprados para poder modificarles el estado
     * @param session sesion utilizada para hacer uso de rollback en caso de que algo falle
     */
    public void changeStatus(List<ProductoEntity> productos, Session session) {
       EstadoProductoEntity estadoProductoEntity = estadoProductoService.getStatus(session);
       productoDAO.changeStatus(estadoProductoEntity, productos, session);

    }
}
