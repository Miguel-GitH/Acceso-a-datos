package org.educa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;



@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "historico_pedido")
public class HistoricoPedidoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cambios;
    @Column(name = "usu_mod")
    private String usuMod;
    @Column(name = "fec_mod")
    private LocalDate fecMod;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedidoEntity;
}
