package com.pfcti.com.spring.dev.app.repository;

import com.pfcti.com.spring.dev.app.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta,Integer> {
}
