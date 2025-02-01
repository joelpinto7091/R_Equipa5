package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.TarefaTrabalho;

import java.util.List;

public interface TarefaTrabalhoRepository extends JpaRepository<TarefaTrabalho, Long> {
    List<TarefaTrabalho> findByNomeProjeto(String nomeProjeto);
}
