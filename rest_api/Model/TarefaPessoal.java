
package com.upt.lp.rest_api.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity

public class TarefaPessoal extends Tarefa{

    private String localizacao; // Local onde a tarefa será realizada
    private int tempoEstimado; // Tempo estimado para conclusão (em minutos)
    private boolean repete; // Se a tarefa se repete
    private String frequenciaRepeticao; // Frequência de repetição (Diária, Semanal, etc.)

    //Construtor vazio
    public TarefaPessoal() {
    }

    //Construtor
    public TarefaPessoal(String titulo, String descricao, LocalDateTime dataDeValidade, String prioridade, Utilizador utilizador, String localizacao, int tempoEstimado, boolean repete, String frequenciaRepeticao) {
        super(titulo, descricao, dataDeValidade, prioridade, utilizador);
        this.localizacao = localizacao;
        this.tempoEstimado = tempoEstimado;
        this.repete = repete;
        this.frequenciaRepeticao = frequenciaRepeticao;
    }

    public String getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public int getTempoEstimado() {
        return tempoEstimado;
    }
    
    public void setTempoEstimado(int tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
    
    public boolean isRepete() {
        return repete;
    }
    
    public void setRepete(boolean repete) {
        this.repete = repete;
    }
    
    public String getFrequenciaRepeticao() {
        return frequenciaRepeticao;
    }
    
    public void setFrequenciaRepeticao(String frequenciaRepeticao) {
        this.frequenciaRepeticao = frequenciaRepeticao;
    }
    

    

    
}

