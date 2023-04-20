package com.pfcti.com.spring.dev.app.repository;

import com.pfcti.com.spring.dev.app.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion,Integer> {
}
