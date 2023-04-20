package com.pfcti.com.spring.dev.app.service;

import com.pfcti.com.spring.dev.app.repository.DireccionRepository;
import com.pfcti.com.spring.dev.app.repository.InversionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class InversionService {
    private InversionRepository inversionRepository;

}
