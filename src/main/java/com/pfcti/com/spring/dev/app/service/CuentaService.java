package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.model.Cuenta;
import com.pfcti.com.spring.dev.app.repository.ClienteRepository;
import com.pfcti.com.spring.dev.app.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class CuentaService {
    private CuentaRepository cuentaRepository;

}
