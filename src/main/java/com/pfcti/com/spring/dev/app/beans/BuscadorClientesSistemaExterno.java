package com.pfcti.com.spring.dev.app.beans;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.ClienteQueryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("sistemaExterno")
public class BuscadorClientesSistemaExterno implements BuscadorClientes{
    @Override
    public List<ClienteDto> obtenerListaClientes(ClienteQueryDto clienteQueryDto){
      return new ArrayList<>();
    }
}
