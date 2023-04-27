package com.pfcti.com.spring.dev.app.api;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.CuentaDto;
import com.pfcti.com.spring.dev.app.model.Cuenta;
import com.pfcti.com.spring.dev.app.service.ClienteService;
import com.pfcti.com.spring.dev.app.service.CuentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/cliente")
public class ClienteApi {
    @Autowired
    private ClienteService clienteService;
    private ClienteService cuentaService;

    @GetMapping("/{id}")

    public ClienteDto buscarCliente(@PathVariable int id){
        log.info("Busqueda de cliente : {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping(value = "/xml/{id}",produces = MediaType.APPLICATION_XML_VALUE)
    public ClienteDto buscarClienteXml(@PathVariable int id){ log.info("Busqueda de cliente : {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping(value = "/xml/json/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ClienteDto buscarClienteXmlJson(@PathVariable int id){ log.info("Busqueda de cliente : {}", id);
        return clienteService.obtenerCliente(id);
    }

    @GetMapping(value = "/parameter")
    public ClienteDto buscarClienteParameter(@RequestParam int id){
        log.info("Busqueda de cliente : {}", id);
        return clienteService.obtenerCliente(id);
    }

    @PostMapping
    public void guardarCliente(@Valid @RequestBody ClienteDto clienteDto){
        log.info("cliente de cliente : {}", clienteDto);
        clienteService.insertarCliente(clienteDto);
    }

    @GetMapping(value = "/all")
    public List<ClienteDto> buscarTodosClientes(){
        return clienteService.obtenerClientes();
    }

    @PutMapping
    public void actualizarCliente(@RequestBody ClienteDto clienteDto){
        log.info("cliente de cliente : {}", clienteDto);
        clienteService.actualizarCliente(clienteDto);
    }


    @DeleteMapping(value = "/{id}")
    public void eliminarCliente(@PathVariable int id){
        log.info("cliente de cliente : {}", id);
        clienteService.eliminarCliente(id); }

/*

    @GetMapping("/{id}")
    public List<CuentaDto> buscarCuentaCliente(@PathVariable int id) {
        log.info("Busqueda de cliente : {}", id);
        return cuentaService.BuscarCuentaCliente(id);
    }

    private CuentaDto fromCuentaToCuentaeDto(Cuenta cuenta) {
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }

    @PostMapping
    public void actualizarCuenta(@Valid @RequestBody CuentaDto cuentaDto){
        log.info("Cuenta a guardar : {}", cuentaDto);
        cuentaService.actualizarCuenta(cuentaDto);
    }
*/

}
