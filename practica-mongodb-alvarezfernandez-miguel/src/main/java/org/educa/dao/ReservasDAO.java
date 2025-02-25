package org.educa.dao;

import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.wrappers.InfoPasajero;

import java.math.BigDecimal;
import java.util.List;

public interface ReservasDAO {
    /**
     * Metodo para encontrar por id la reserva
     * @param id parametro de filtro utilizado en el metodo
     * @return retornamos la reserva con la que coincida la id y _id
     */
    ReservaEntity findById(int id);

    /**
     * Metodo para actualizar una reserva
     * @param reservaToUpdate le pasamos la nueva reserva
     * @return retornamos las lineas que se han actualizado
     */
    Long update(ReservaEntity reservaToUpdate);

    /**
     * Metodo para borrar un documento dentro de la base de datos
     * @param id filtro para borrar determinado documento a traves de un identificador
     * @return retornamos el numero de documentos borrados
     */
    Long delete(int id);

    /**
     * Metodo para guardar una reserva en la base de datos
     * @param reserva le pasamos la reserva con los datos obtenidos del usuario
     * @return retornamos el numero de objetos insertados
     */
    Integer save(ReservaEntity reserva);

    /**
     * Metodo para encontrar todas las reservas
     * @return devuelve todos los documentos de la coleccion reservas
     */
    List<ReservaEntity> findAll();

    /**
     * Metodo para encontrar la reserva filtrando la cantidad de (precio) que queramos obtener en ese caso superior o igual a
     * @param cantidad cantidad que tendra que ser superada o igualada por el filtro
     * @return retornamos los documentos que coincidan con el filtro
     */
    List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad);

    /**
     * Metodo para encontrar las reservas a traves del pasaporte que nos pasara el usuario
     * @param pasaporte filtro para encontrar las reservas de ese usuario
     * @return retornamos la informacion de dicho pasajero
     */
    InfoPasajero findReservasByPasaporte(String pasaporte);

    /**
     * Metodo para encontrar las reservas a traves del id del vuelo
     * @param vueloId parametro usado como filtro para encontrar las reservas
     * @return retornamos los datos de las reservas
     */
    List<ReservaEntity> findReservasByVueloId(Integer vueloId);
}
