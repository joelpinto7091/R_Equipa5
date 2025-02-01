package com.upt.lp.rest_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upt.lp.rest_api.Model.TarefaDeCompras;

import java.util.List;

public interface TarefaDeComprasRepository extends JpaRepository<TarefaDeCompras, Long> {
    List<TarefaDeCompras> findByLoja(String loja);
}