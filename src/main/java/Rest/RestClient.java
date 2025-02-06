package Rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import Model.Tarefa;
import Model.TarefaPessoal;
import Model.TarefaTrabalho;
import Model.Utilizador;

import java.util.*;

public class RestClient {

    private RestTemplate restTemplate = new RestTemplate();
    private static final String UTILIZADORES_API_URL = "http://localhost:8080/api/utilizadores";
    private static final String TAREFAS_API_URL = "http://localhost:8080/tarefas";
    private static final String TAREFAS_PESSOAIS_API_URL = "http://localhost:8080/tarefas-pessoais";
    private static final String TAREFAS_TRABALHO_API_URL = "http://localhost:8080/tarefas-trabalho";

    public void createTarefaPessoal(TarefaPessoal novaTarefa) {
        restTemplate.postForEntity(TAREFAS_PESSOAIS_API_URL, novaTarefa, Tarefa.class);
    }

    public void createTarefaTrabalho(TarefaTrabalho novaTarefa) {
        restTemplate.postForEntity(TAREFAS_TRABALHO_API_URL, novaTarefa, Tarefa.class);
    }

    public List<TarefaPessoal> getAllTarefasPessoais() {
        ResponseEntity<TarefaPessoal[]> response = restTemplate.getForEntity(TAREFAS_PESSOAIS_API_URL, TarefaPessoal[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public List<TarefaTrabalho> getAllTarefasTrabalho() {
        ResponseEntity<TarefaTrabalho[]> response = restTemplate.getForEntity(TAREFAS_TRABALHO_API_URL, TarefaTrabalho[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public void deleteTarefaPessoal(Long id) {
        restTemplate.delete(TAREFAS_PESSOAIS_API_URL + "/" + id);
    }

    public void deleteTarefaTrabalho(Long id) {
        restTemplate.delete(TAREFAS_TRABALHO_API_URL + "/" + id);
    }

    public Utilizador getUtilizadorById(Long id) {
        ResponseEntity<Utilizador> response = restTemplate.getForEntity(UTILIZADORES_API_URL + "/" + id, Utilizador.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    public boolean deleteUtilizador(Long id) {
        ResponseEntity<Void> response = restTemplate.exchange(UTILIZADORES_API_URL + "/" + id, HttpMethod.DELETE, null, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public List<Tarefa> getAllTarefas() {
        ResponseEntity<Tarefa[]> response = restTemplate.getForEntity(TAREFAS_API_URL, Tarefa[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public boolean deleteTarefa(Long id) {
        ResponseEntity<Void> response = restTemplate.exchange(TAREFAS_API_URL + "/" + id, HttpMethod.DELETE, null, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean saveTarefa(Tarefa tarefa) {
        ResponseEntity<Tarefa> response = restTemplate.postForEntity(TAREFAS_API_URL, tarefa, Tarefa.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public List<Tarefa> getTarefasByUtilizador(Long userId) {
        ResponseEntity<Tarefa[]> response = restTemplate.getForEntity(TAREFAS_API_URL + "/utilizador/" + userId, Tarefa[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public boolean createUser(Utilizador user) {

        ResponseEntity<Utilizador> response = restTemplate.postForEntity(UTILIZADORES_API_URL, user, Utilizador.class);
        return response.getStatusCode().is2xxSuccessful();

    }

    public Utilizador getUserByEmail(String email) {

        ResponseEntity<Utilizador> response = restTemplate.getForEntity(UTILIZADORES_API_URL + "/email/" + email, Utilizador.class);
        return response.getStatusCode().is2xxSuccessful() ? response.getBody() : null;

    }

    public void createTarefa(Tarefa novaTarefa) {
        restTemplate.postForEntity(TAREFAS_API_URL, novaTarefa, Tarefa.class);
    }

    public void updateTarefa(Tarefa tarefa) {
        restTemplate.put(TAREFAS_API_URL + "/" + tarefa.getId(), tarefa);
    }

    public List<Utilizador> getAllUtilizadores() {
        ResponseEntity<Utilizador[]> response = restTemplate.getForEntity(UTILIZADORES_API_URL, Utilizador[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public void updateUtilizador(Utilizador utilizador) {
        restTemplate.put(UTILIZADORES_API_URL + "/" + utilizador.getId(), utilizador);
    }
}

