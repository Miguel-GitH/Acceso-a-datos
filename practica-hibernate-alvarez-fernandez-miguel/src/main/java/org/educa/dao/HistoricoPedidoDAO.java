package org.educa.dao;

import org.educa.entity.HistoricoPedidoEntity;

public interface HistoricoPedidoDAO {
    /**
     * Metodo que utilizamos para escribir en la base de datos una vez la venta esté confirmada
     * @param historicoPedido Se le da los datos del historico pedido que son los que se van a insertar
     */
    void saveHistoric(HistoricoPedidoEntity historicoPedido);
}
