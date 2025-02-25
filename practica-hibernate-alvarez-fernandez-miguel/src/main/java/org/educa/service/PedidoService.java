package org.educa.service;

import org.educa.dao.PedidoDAO;
import org.educa.dao.PedidoDAOImpl;
import org.educa.dao.hibernate.DAOSession;
import org.educa.entity.HistoricoPedidoEntity;
import org.educa.entity.PedidoEntity;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private final EstadoPedidoService estadoPedidoService = new EstadoPedidoService();
    private final ProductoService productoService = new ProductoService();

    /**
     * Metodo utilizado para insertar en la base de datos un pedido con todos sus datos
     * @param pedido pedido creado a partir del producto y los datos del cliente el cual se introducira
     */
    public void insertarPedido(PedidoEntity pedido) {
      pedido.setEstadoPedido(estadoPedidoService.getStatus());
      pedido.setFecha(Timestamp.valueOf(LocalDateTime.now()));
      try(Session session = DAOSession.getSession()){
          pedido.setHistoricoPedidoEntity(getHistoricoPedidoData(pedido));
          pedidoDAO.insertarPedido(pedido, session);
          productoService.changeStatus(pedido.getProductos(), session);
      }
    }

    /**
     * Metodo utilizado para rellenar los datos de historico pedido
     * @param pedido le paso el pedido para pasarle algunos datos necesarios
     * @return devuelvo el historico ya relleno hacia pedido
     */
    private List<HistoricoPedidoEntity> getHistoricoPedidoData(PedidoEntity pedido) {
        HistoricoPedidoEntity historicoPedido = new HistoricoPedidoEntity();
        historicoPedido.setPedidoEntity(pedido);
        historicoPedido.setFecMod(LocalDate.now());
        historicoPedido.setUsuMod(pedido.getCliente().getNombre());
        historicoPedido.setCambios("pedido añadido");

        List<HistoricoPedidoEntity> historicoPedidoList = new ArrayList<>();
        historicoPedidoList.addFirst(historicoPedido);
        return historicoPedidoList;
    }
}
