package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.Utilizador;

import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    Optional<Utilizador> findByEmail(String email);
}
