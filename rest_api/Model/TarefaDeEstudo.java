package com.upt.lp.rest_api.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TarefaDeEstudo extends Tarefa {
    private String disciplina; // Matéria ou disciplina
    private String dificuldade; // Nível de dificuldade (Fácil, Médio, Difícil)

    //Construtor vazio
    public TarefaDeEstudo() {
    }

    //Construtor
    public TarefaDeEstudo(String titulo, String descricao, LocalDateTime dataDeValidade, String prioridade, Utilizador utilizador, String disciplina, String dificuldade) {
        super(titulo, descricao, dataDeValidade, prioridade, utilizador);
        this.disciplina = disciplina;
        this.dificuldade = dificuldade;
    }

    public String getDisciplina() {
        return disciplina;
    }
        
    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
        
    public String getDificuldade() {
        return dificuldade;
    }
        
     public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
        
}

