package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.dto.ClienteDto;
import com.pfcti.com.spring.dev.app.dto.CuentaDto;
import com.pfcti.com.spring.dev.app.model.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CuentaServiceTest {
    @Autowired
    CuentaService cuentaService;
    @Test
    void busquedaCuentasPorCriterio() {
        CuentaDto
        cuentaDto = new CuentaDto();
        cuentaDto.setEstado(true);
        List<CuentaDto> cuentaDtos = cuentaService.busquedaCuentasPorCriterio(cuentaDto);
        cuentaDtos.forEach((cuentaDto4 -> {System.out.println("Estado cuenta: " + cuentaDto4.getEstado());}));
        assertTrue(cuentaDtos.size() == 1);

    }
}