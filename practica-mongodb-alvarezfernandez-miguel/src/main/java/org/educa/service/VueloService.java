package org.educa.service;

import org.educa.dao.VueloDAO;
import org.educa.dao.VueloDAOImpl;
import org.educa.entity.ReservaEntity;
import org.educa.wrappers.BeneficioVuelo;

import java.math.BigDecimal;
import java.util.List;


public class VueloService {
    private final VueloDAO dao = new VueloDAOImpl();
    private final ReservaService reservaService = new ReservaService();

    /**
     * Metodo utilizado para calcular el benificio, total y coste
     * @return retornamos los datos de beneficio vuelo una vez calculados
     */
    public List<BeneficioVuelo> getBeneficioVuelo() {

        List<BeneficioVuelo> beneficioVueloList = dao.getBeneficioVuelo();


        for (BeneficioVuelo beneficioVuelo : beneficioVueloList) {

            List<ReservaEntity> reservaList = reservaService.findReservasByVueloId(beneficioVuelo.getVueloId());

            Integer numPasajeros = 0;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal beneficio = BigDecimal.ZERO;

            for (ReservaEntity reserva : reservaList) {
                if(!reserva.getEstado().equals("Cancelada")) {
                    numPasajeros++;
                    total = total.add(reserva.getPrecio().divide(BigDecimal.TWO));

                } else{
                    total = total.add(reserva.getPrecio());
                }


                beneficio = total.subtract(beneficioVuelo.getCoste());
            }

            beneficioVuelo.setNumPasajeros(numPasajeros);
            beneficioVuelo.setTotal(total);
            beneficioVuelo.setBeneficio(beneficio);
        }

        return beneficioVueloList;
    }

}
