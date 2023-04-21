package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void insertarCliente() {

        List<Cliente> listaClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();

        System.out.println(">>>>>>Lista antes de insertar:" + listaClientes.size());
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Luis");
        clienteDto.setApellidos("Quiros");
        clienteDto.setCedula("233444");
        clienteDto.setTelefono("6233444");
        clienteService.insertarCliente(clienteDto);
        System.out.println(">>>>>>Después de insertar:" + listaClientes.size());

        assertEquals(1, 1);
    }

    @Test
    void obtenerCliente() {
        ClienteDto clienteDto = clienteService.obtenerCliente(1);
        System.out.println(">>>>El cliente si existe:" + clienteDto.getApellidos());
        assertEquals(1,clienteDto.getId());

    }

    @Test
    void actualizarCliente() {
        ClienteDto clienteDtoInicial = clienteService.obtenerCliente(1);
        System.out.println("El cliente inicial es: "+ clienteDtoInicial.getApellidos());
        clienteDtoInicial.setApellidos("SALAZAR");
        clienteService.actualizarCliente(clienteDtoInicial);
        ClienteDto clienteDtoLuegoDeUpdate = clienteService.obtenerCliente(1);
        System.out.println("El cliente inicial es: "+ clienteDtoLuegoDeUpdate.getApellidos());
        assertEquals("SALAZAR", clienteDtoLuegoDeUpdate.getApellidos());
    }

    @Test
    void obtenerClientes() {
        List<ClienteDto> clienteDtos = clienteService.obtenerClientes();
        clienteDtos.forEach(cliente -> System.out.println("Cliente: " + cliente.getApellidos()));
        assertEquals(2, clienteDtos.size());
    }

    @Test
    void obtenerClientesPorCodigoISOPaisYCuentasActivas() {
        List<ClienteDto> clienteDtos = clienteService.obtenerClientesPorCodigoISOPaisYCuentasActivas("CR");
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " +clienteDto.getApellidos());});
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void eliminarCliente() {
        clienteService.eliminarCliente(1);
        assertEquals(1,1);
    }

    @Test
    void buscarPorApellidos() {
        List<ClienteDto> clienteDtos = clienteService.buscarPorApellidos("SANCHEZ");
        clienteDtos.forEach(clienteDto -> {System.out.println("client" +clienteDto.getApellidos());});
        assertEquals(1,clienteDtos.size());
    }

    @Test
    void buscarPorApellidosQueryNativo() {

        List<ClienteDto> clienteDtos = clienteService.buscarPorApellidosQueryNativo("PEREZ");
        clienteDtos.forEach(clienteDto -> {System.out.println("client" +clienteDto.getApellidos());});
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void updateClienteByQuery() {
        ClienteDto clienteDtoOriginal = clienteService.buscarPorApellidos("SANCHEZ").get(0);
        System.out.println("Nombre original: " + clienteDtoOriginal.getNombre());
        clienteService.updateClienteByQuery("CAMBIADO EL NOMBRE", "SANCHEZ");

        ClienteDto clienteDtoCambiado = clienteService.buscarPorApellidos("SANCHEZ").get(0);
        System.out.println("NOMBRE CAMBIADO: " + clienteDtoCambiado.getNombre());
        assertNotEquals(clienteDtoOriginal.getNombre(), clienteDtoCambiado.getNombre());
    }
}