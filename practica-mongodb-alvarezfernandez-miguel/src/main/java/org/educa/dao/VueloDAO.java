package org.educa.dao;

import org.educa.wrappers.BeneficioVuelo;

import java.util.List;

public interface VueloDAO {
    /**
     * Metodo que obtiene los datos de la entidad beneficio vuelo Beneficio, num pasajeros, coste, total etc.
     * @return retorna los datos de beneficio vuelo rellenos
     */
    List<BeneficioVuelo> getBeneficioVuelo();
}
