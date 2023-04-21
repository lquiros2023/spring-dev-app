package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.model.Cliente;
import com.pfcti.com.spring.dev.app.repository.*;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ClienteService {
    private ClienteRepository clienteRepository;
    private CuentaRepository cuentaRepository;
    private TarjetaRepository tarjetaRepository;
    private InversionRepository inversionRepository;
    private DireccionRepository direccionRepository;

    public void insertarCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());
        clienteRepository.save(cliente);

    }

    public ClienteDto obtenerCliente(int clientId) {
        Cliente cliente = clienteRepository.findById(clientId).
                orElseThrow(() -> {
                            throw new RuntimeException("No existe");
                        }
                );
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setCedula(cliente.getCedula());
        clienteDto.setTelefono(cliente.getTelefono());
        return clienteDto;
    }

    public void actualizarCliente(ClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(clienteDto.getId()).
                orElseThrow(() -> {
                            throw new RuntimeException("No existe");
                        }
                );
        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());
        clienteRepository.save(cliente);
    }

    public List<ClienteDto> obtenerClientes() {
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(cliente -> {
            clienteDtos.add(fromClienteToClienteDto(cliente));
        });
        return clienteDtos;
    }

    public List<ClienteDto> obtenerClientesPorCodigoISOPaisYCuentasActivas(String codigoISOPais) {
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findClientesByPaisAndCuentas_EstadoIsTrue(codigoISOPais);
        clientes.forEach(cliente ->
        {
            clienteDtos.add(fromClienteToClienteDto(cliente));
        });
        return clienteDtos;
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
  public void eliminarCliente(Integer clienteId){
        direccionRepository.deleteAllByCliente_Id(clienteId);
        cuentaRepository.deleteAllByCliente_Id(clienteId);
        clienteRepository.deleteById(clienteId);
        inversionRepository.deleteAllByCliente_Id(clienteId);
        tarjetaRepository.deleteAllByCliente_Id(clienteId);
  }

  public List<ClienteDto> buscarPorApellidos(String apellidos){
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.buscarPorApellidos(apellidos);
        clientes.forEach(cliente -> clienteDtos.add(fromClienteToClienteDto(cliente)));
        return clienteDtos;
  }

  public List<ClienteDto> buscarPorApellidosQueryNativo(String apellidos){
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Tuple> tuples = clienteRepository.buscarPorApellidosQueryNativo(apellidos);
        tuples.forEach(tuple -> {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setApellidos((String) tuple.get("apellidos"));
            clienteDto.setNombre((String) tuple.get("nombre"));
            clienteDto.setCedula((String) tuple.get("cedula"));
            clienteDto.setTelefono((String) tuple.get("telefono"));
            clienteDto.setId((Integer) tuple.get("id"));
            clienteDtos.add(clienteDto);

        });
        return clienteDtos;
  }
    public void updateClienteByQuery(String nombre, String apellidos){
        clienteRepository.updateClienteByQuery(nombre,apellidos);
    }
    public List<ClienteDto> findByApellidosAndAndNombre(String apellidos, String nombre)
    {
        return clienteRepository
                .findByApellidosAndAndNombre(apellidos,nombre)
                .stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());
    }


    public  List<ClienteDto> findClientesByPaisAndTarjetas_EstadoIsTrue(String pais){
        return clienteRepository
                .findClientesByPaisAndTarjetas_EstadoIsTrue(pais)
                .stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());
    }

}
