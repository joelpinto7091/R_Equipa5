package com.upt.lp.rest_api.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TarefaDeCompras extends Tarefa {
    private String loja; // Nome da loja ou mercado
    private double orcamento; // Orçamento estimado

    //Construtor vazio
    public TarefaDeCompras() {
    }

    //Construtor
    public TarefaDeCompras(String titulo, String descricao, LocalDateTime dataDeValidade, String prioridade, Utilizador utilizador, String loja, double orcamento) {
        super(titulo, descricao, dataDeValidade, prioridade, utilizador);
        this.loja = loja;
        this.orcamento = orcamento;
    }

    public String getLoja() {
        return loja;
    }
    
    public void setLoja(String loja) {
        this.loja = loja;
    }
    
    public double getOrcamento() {
        return orcamento;
    }
    
    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }
    
    
} 
