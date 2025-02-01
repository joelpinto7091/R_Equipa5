package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.TarefaPessoal;

import java.util.List;

public interface TarefaPessoalRepository extends JpaRepository<TarefaPessoal, Long> {
    List<TarefaPessoal> findByLocalizacao(String localizacao);
}
