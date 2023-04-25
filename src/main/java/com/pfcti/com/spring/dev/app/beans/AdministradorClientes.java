package com.pfcti.com.spring.dev.app.beans;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.com.spring.dev.app.dto.enums.ClienteQueryType;
import com.pfcti.com.spring.dev.app.model.Cliente;
import com.pfcti.com.spring.dev.app.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AdministradorClientes {
    private ClienteRepository clienteRepository;
    private ClienteQueryType defaultClienteQueryType;

    public AdministradorClientes(ClienteRepository clienteRepository, ClienteQueryType defaultClienteQueryType) {
        System.out.println(">>>> AdministradorClientes INSTANCIA " + this);
        this.clienteRepository = clienteRepository;
        this.defaultClienteQueryType = defaultClienteQueryType;
    }


    private ClienteDto fromClienteToClienteDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        return clienteDto;
       /* clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setCedula(cliente.getCedula());
        clienteDto.setTelefono(cliente.getTelefono());*/

    }
    public List<ClienteDto> obtenerListaClientesPorCriterio(ClienteQueryDto clienteQueryDto){
        List<Cliente> clientes = null;
        if (ClienteQueryType.CEDULA.equals(clienteQueryDto.getClienteQueryType())){
            clientes = clienteRepository.findClientesByCedula(clienteQueryDto.getTextoBusqueda());
        } else if (ClienteQueryType.NOMBRES.equals(clienteQueryDto.getClienteQueryType())) {
            clientes = clienteRepository.findClientesByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                    clienteQueryDto.getTextoBusqueda(),clienteQueryDto.getTextoBusqueda());
        }

        return clientes.stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());
    }

}
