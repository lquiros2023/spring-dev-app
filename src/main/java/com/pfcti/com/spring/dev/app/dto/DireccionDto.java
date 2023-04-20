package com.pfcti.com.spring.dev.app.dto;

import lombok.Data;

@Data
public class DireccionDto {
    private int id;
    private String direccion;
    private String nomenclatura;
    private int clienteId;
}
