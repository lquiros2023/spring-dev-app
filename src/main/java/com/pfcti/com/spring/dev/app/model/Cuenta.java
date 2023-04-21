package com.pfcti.com.spring.dev.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;
    private String tipo;
    private boolean estado;
    @ManyToOne
    @JoinColumn(name="cliente_id",referencedColumnName = "id")
    private Cliente cliente;
}
