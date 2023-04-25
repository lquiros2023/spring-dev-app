package com.pfcti.com.spring.dev.app.repository;


import com.pfcti.com.spring.dev.app.model.Cliente;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {
  List<Cliente> findClientesByPaisAndCuentas_EstadoIsTrue(String pais);
  @Query(value = "select c from Cliente c where c.apellidos=:apellidos")
  List<Cliente> buscarPorApellidos(String apellidos);

  @Query(value = "select nombre, apellidos, cedula, telefono, id from TCLIENTE where apellidos=:apellidos", nativeQuery = true)
  List<Tuple> buscarPorApellidosQueryNativo(String apellidos);

  @Modifying
  @Query(value = "update Cliente c set c.nombre =:nombre where c.apellidos =:apellidos")
  void updateClienteByQuery(String nombre, String apellidos);

  List<Cliente> findByApellidosAndAndNombre(String apellidos, String nombre);

  List<Cliente> findClientesByPaisAndTarjetas_EstadoIsTrue(String pais);

  List<Cliente> findClientesByCedula(String cedula);

  List<Cliente> findClientesByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
}