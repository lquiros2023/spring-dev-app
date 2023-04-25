package com.pfcti.com.spring.dev.app.beans;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.com.spring.dev.app.dto.enums.ClienteQueryType;
import com.pfcti.com.spring.dev.app.model.Cliente;
import com.pfcti.com.spring.dev.app.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("baseDeDatos")
public class BuscadorClientesBdd implements BuscadorClientes {
    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteDto fromClienteToClienteDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        return clienteDto;

    }

    @Override
    public List<ClienteDto> obtenerListaClientes(ClienteQueryDto clienteQueryDto) {
        List<Cliente> clientes = null;
        if (ClienteQueryType.CEDULA.equals(clienteQueryDto.getClienteQueryType())) {
            clientes = clienteRepository.findClientesByCedula(clienteQueryDto.getTextoBusqueda());
        } else if (ClienteQueryType.NOMBRES.equals(clienteQueryDto.getClienteQueryType())) {
            clientes = clienteRepository.findClientesByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                    clienteQueryDto.getTextoBusqueda(), clienteQueryDto.getTextoBusqueda());
        }

        return clientes.stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());
    }
}

