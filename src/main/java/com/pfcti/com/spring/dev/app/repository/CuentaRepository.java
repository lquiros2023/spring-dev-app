package com.pfcti.com.spring.dev.app.repository;

import com.pfcti.com.spring.dev.app.model.Cuenta;
import com.pfcti.com.spring.dev.app.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta,Integer> {
    void deleteAllByCliente_Id(int clienteId);


}