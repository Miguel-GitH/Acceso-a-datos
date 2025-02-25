package org.educa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the pedido database table.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class PedidoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Timestamp fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion")
    private DireccionEntity direccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado")
    private EstadoPedidoEntity estadoPedido;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="pedido_producto", joinColumns = {@JoinColumn(name = "id_pedido")},
    inverseJoinColumns = {@JoinColumn(name = "id_producto")})
    private List<ProductoEntity> productos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "pedidoEntity",cascade = CascadeType.MERGE)
    private List<HistoricoPedidoEntity> historicoPedidoEntity;
}