package org.educa.service;

import org.educa.dao.ReservasDAO;
import org.educa.dao.ResevasDAOImpl;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.wrappers.InfoPasajero;

import java.math.BigDecimal;
import java.util.List;

public class ReservaService {
    private final ReservasDAO reservasDAO = new ResevasDAOImpl();

    /**
     * Metodo para encontrar las reservas por el id del vuelo
     * @param vueloId filtro para encontrar las reservas con ese vuelo
     * @return retornamos los documentos que coincidan con el filtro
     */
    public List<ReservaEntity> findReservasByVueloId(Integer vueloId) {
        return reservasDAO.findReservasByVueloId(vueloId);
    }

    /**
     * Metodo utilizado para encontrar las reservas a traves de un pasaporte
     * @param pasaporte filtro que nos da el usuario para sacar su informacion
     * @return retornamos la informacion del pasajero
     */
    public InfoPasajero findReservasByPasaporte(String pasaporte) {

        return reservasDAO.findReservasByPasaporte(pasaporte);
    }

    /**
     * Metodo utilizado para encontrar las reservas que superen o igualen la cantidad que nos diga el usuario
     * @param cantidad filtro utilizado para la consulta que no dara una reserva con un precio superior o igual a esa cantidad
     * @return retornamos los documentos encontrados a traves del filtro
     */
    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {
        return reservasDAO.findReservasByCantidad(cantidad);
    }

    /**
     * Metodo para guardar datos en la base de datos
     * @param reserva Entidad la cual se va a guardar en la base de datos como un documento
     * @return retornamos las lineas afectadas en este caso los usuarios introducidos
     */
    public Integer save(ReservaEntity reserva) {

        return reservasDAO.save(reserva);
    }

    /**
     * Metodo utilizado para encontrar una reserva a traves de un identificador
     * @param id identificador utilizado como filtro que nos pasa el usuario
     * @return retornamos el documento que coincida con el filtro
     */
    public ReservaEntity findById(int id) {

        return reservasDAO.findById(id);
    }

    /**
     * Metodo utilizado para actualizar una resrva
     * @param reservaToUpdate reserva nueva (la que se va a ver en la base de datos)
     * @return retornamos las lineas actualizadas
     */
    public Long update(ReservaEntity reservaToUpdate) {

        return reservasDAO.update(reservaToUpdate);
    }

    /**
     * Metodo utilizado para borrar una reserva
     * @param id le pasamos el filtro para indicar que reserva borramos
     * @return retornamos el numero de filas afectadas
     */
    public Long delete(int id) {

        return reservasDAO.delete(id);
    }

    /**
     * Metodo para encontrar todas las reservas de la base de datos
     * @return retornamos todas las reservas de la base de datos
     */
    public List<ReservaEntity> findAll() {

        return reservasDAO.findAll();
    }
}
