package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upt.lp.rest_api.Model.TarefaDeEstudo;

import java.util.List;


@Repository
public interface TarefaDeEstudoRepository extends JpaRepository<TarefaDeEstudo, Long> {
    List<TarefaDeEstudo> findByDisciplina(String disciplina);
}
