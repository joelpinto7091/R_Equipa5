package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upt.lp.rest_api.Model.TarefaPessoal;

import java.util.List;


@Repository
public interface TarefaPessoalRepository extends JpaRepository<TarefaPessoal, Long> {
    List<TarefaPessoal> findByLocalizacao(String localizacao);
}
