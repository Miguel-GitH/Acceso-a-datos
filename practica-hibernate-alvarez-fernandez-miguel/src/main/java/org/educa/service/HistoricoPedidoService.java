package org.educa.service;

import org.educa.dao.HistoricoPedidoDAO;
import org.educa.dao.HistoricoPedidoDAOImpl;
import org.educa.entity.HistoricoPedidoEntity;
import org.educa.entity.PedidoEntity;

import java.time.LocalDate;


public class HistoricoPedidoService {
    private final HistoricoPedidoDAO historicoPedidoDAO = new HistoricoPedidoDAOImpl();

    /**
     * Metodo el cual rellenamos los datos del historico_pedido
     * @param pedido Parametro dado desde pedidoService una vez creado para almacenar los datos en un historico
     */
    public void saveHistoric(PedidoEntity pedido) {
        HistoricoPedidoEntity historicoPedido = new HistoricoPedidoEntity();
        historicoPedido.setPedidoEntity(pedido);
        historicoPedido.setFecMod(LocalDate.now());
        historicoPedido.setUsuMod("ADMIN");
        historicoPedido.setCambios("pedido añadido");
        historicoPedidoDAO.saveHistoric(historicoPedido);
    }
}
