package Model;



import jakarta.persistence.*;
import java.time.LocalDateTime;


public class Utilizador {

    
    private Long id;

   
    private String nome;

   
    private String password;

   
    private String email;

   
    private boolean isAdmin = false;

  
    private LocalDateTime dataCriacao = LocalDateTime.now();

    //Construtor vazio
    public Utilizador() {
    }

    //Construtor
    public Utilizador(String nome, String password, String email) {
        this.nome = nome;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

   
}
    

