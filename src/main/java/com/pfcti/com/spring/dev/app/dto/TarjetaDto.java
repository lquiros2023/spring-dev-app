package com.pfcti.com.spring.dev.app.dto;

import com.pfcti.com.spring.dev.app.model.Cliente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TarjetaDto {
    private int id;
    private String numero;
    private String tipo;
    private Cliente cliente;
}
