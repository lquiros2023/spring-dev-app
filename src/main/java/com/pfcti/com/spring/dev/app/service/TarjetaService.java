package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.repository.InversionRepository;
import com.pfcti.com.spring.dev.app.repository.TarjetaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TarjetaService {
    private TarjetaRepository tarjetaRepository;

}
