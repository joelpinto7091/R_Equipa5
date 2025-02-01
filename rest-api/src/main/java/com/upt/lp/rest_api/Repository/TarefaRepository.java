package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.Tarefa;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUtilizadorId(Long userId);
}
