package com.pfcti.com.spring.dev.app.dto;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.BatchSize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data

public class ClienteDto {

    private int id;
    @NotNull
    @Size(max = 10)
    private String nombre;

    @NotNull
    private String apellidos;
    @NotNull
    private String cedula;
    private String telefono;
    private String pais;
    private List<DireccionDto> direcciones;
    private List<CuentaDto> cuentaDtos;
    private List<TarjetaDto> tarjetaDtos;


}
