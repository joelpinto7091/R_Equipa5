package Model;

import java.time.LocalDateTime;

public class TarefaTrabalho extends Tarefa {

    private String nomeProjeto; 
    private String nomeEquipa;
    private String tipoTrabalho;
    private double tempoEstimado;

    //Construtor vazio
    public TarefaTrabalho() {
    }

    //Construtor
    public TarefaTrabalho(String titulo, String descricao, LocalDateTime dataDeValidade, String prioridade, Utilizador utilizador, String nomeProjeto, String nomeEquipa, String tipoTrabalho, double tempoEstimado) {
        super(titulo, descricao, dataDeValidade, prioridade, utilizador);
        this.nomeProjeto = nomeProjeto;
        this.nomeEquipa = nomeEquipa;
        this.tipoTrabalho = tipoTrabalho;
        this.tempoEstimado = tempoEstimado;
    }
    
    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getNomeEquipa() {
        return nomeEquipa;
    }

    public void setNomeEquipa(String nomeEquipa) {
        this.nomeEquipa = nomeEquipa;
    }

    public String getTipoTrabalho() {
        return tipoTrabalho;
    }

    public void setTipoTrabalho(String tipoTrabalho) {
        this.tipoTrabalho = tipoTrabalho;
    }

    public double getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(double tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }


}
