package Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


	


	
	public class Tarefa {
	 
	    private Long id;

	  
	    private String titulo;

	  
	    private String descricao;

	  
	    private LocalDateTime dataDeValidade;

	 
	    private String prioridade;

	    private String estado = "pendente";

	    

	    
	    
	    private Utilizador utilizador;

	   //Construtor vazio 
	    public Tarefa() {
	    }

	    //Construtor
	    public Tarefa(String titulo, String descricao, LocalDateTime dataDeValidade, String prioridade, Utilizador utilizador) {
	        this.titulo = titulo;
	        this.descricao = descricao;
	        this.dataDeValidade = dataDeValidade;
	        this.prioridade = prioridade;
	        this.utilizador = utilizador;
	    }
	    

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public LocalDateTime getDataDeValidade() {
	        return dataDeValidade;
	    }

	    public void setDataDeValidade(LocalDateTime dataDeValidade) {
	        this.dataDeValidade = dataDeValidade;
	    }

	    public String getPrioridade() {
	        return prioridade;
	    }

	    public void setPrioridade(String prioridade) {
	        this.prioridade = prioridade;
	    }

	    public String getEstado() {
	        return estado;
	    }

	    public void setEstado(String estado) {
	        this.estado = estado;
	    }

	    public Utilizador getUtilizador() {
	        return utilizador;
	    }

	    public void setUtilizador(Utilizador utilizador) {
	        this.utilizador = utilizador;
	    }

	}




