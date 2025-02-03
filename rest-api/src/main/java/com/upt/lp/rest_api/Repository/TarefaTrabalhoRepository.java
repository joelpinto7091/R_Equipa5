package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upt.lp.rest_api.Model.TarefaTrabalho;

import java.util.List;

@Repository
public interface TarefaTrabalhoRepository extends JpaRepository<TarefaTrabalho, Long> {
    List<TarefaTrabalho> findByNomeProjeto(String nomeProjeto);
}
