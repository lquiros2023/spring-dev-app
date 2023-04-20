package com.pfcti.com.spring.dev.app.repository;


import com.pfcti.com.spring.dev.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}