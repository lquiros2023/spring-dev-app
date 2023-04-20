package com.pfcti.com.spring.dev.app.repository;

import com.pfcti.com.spring.dev.app.model.Inversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InversionRepository extends JpaRepository<Inversion,Integer> {
}