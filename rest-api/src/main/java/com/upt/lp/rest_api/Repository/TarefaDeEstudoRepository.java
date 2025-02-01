package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.TarefaDeEstudo;

import java.util.List;

public interface TarefaDeEstudoRepository extends JpaRepository<TarefaDeEstudo, Long> {
    List<TarefaDeEstudo> findByDisciplina(String disciplina);
}
