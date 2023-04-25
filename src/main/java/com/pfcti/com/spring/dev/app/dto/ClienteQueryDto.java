package com.pfcti.com.spring.dev.app.dto;

import com.pfcti.com.spring.dev.app.dto.enums.ClienteQueryType;
import lombok.Data;

@Data
public class ClienteQueryDto {
    private String textoBusqueda;
    private ClienteQueryType clienteQueryType;

}
