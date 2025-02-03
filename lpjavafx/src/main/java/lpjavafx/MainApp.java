package lpjavafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import com.upt.lp.rest_api.Model.Tarefa;
import com.upt.lp.rest_api.Model.Utilizador;
import com.upt.lp.rest_api.Service.TarefaService;
import com.upt.lp.rest_api.Service.UtilizadorService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MainApp extends Application {

    @Autowired
    private UtilizadorService utilizadorService;

    @Autowired
    private TarefaService tarefaService;

    private ListView<String> listView = new ListView<>();
    private ObservableList<String> tarefaList = FXCollections.observableArrayList();
    private Long usuarioAutenticadoId;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 0);

        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu email");
        grid.add(emailField, 1, 0);

        Label passwordLabel = new Label("Senha:");
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, primaryStage, "Erro", "Por favor, preencha todos os campos.");
            } else {
                Optional<Utilizador> usuario = utilizadorService.validarCredenciais(email, password);

                if (usuario.isPresent()) {
                    usuarioAutenticadoId = usuario.get().getId();
                    showAlert(Alert.AlertType.INFORMATION, primaryStage, "Sucesso", "Login realizado com sucesso.");
                    abrirTelaTarefas(primaryStage);
                } else {
                    showAlert(Alert.AlertType.ERROR, primaryStage, "Erro", "Credenciais inválidas.");
                }
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setTitle("Tela de Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirTelaTarefas(Stage stage) {
        carregarTarefas();

        Button btnCriar = new Button("Criar");
        Button btnEliminar = new Button("Eliminar");
        Button btnConcluir = new Button("Concluir");
        Button btnVoltar = new Button("Voltar");

        btnCriar.setOnAction(e -> criarTarefa());
        btnEliminar.setOnAction(e -> eliminarTarefa());
        btnConcluir.setOnAction(e -> concluirTarefa());
        btnVoltar.setOnAction(e -> stage.close());

        HBox buttonBox = new HBox(10, btnCriar, btnEliminar, btnConcluir, btnVoltar);
        buttonBox.setPadding(new Insets(10));

        VBox vbox = new VBox(10, listView, buttonBox);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Gestão de Tarefas");
        stage.show();
    }

    private void carregarTarefas() {
        if (usuarioAutenticadoId != null) {
            List<Tarefa> tarefas = tarefaService.procurarPorUtilizador(usuarioAutenticadoId);
            tarefaList.clear();
            for (Tarefa tarefa : tarefas) {
                tarefaList.add(tarefa.getTitulo() + " - " + tarefa.getDescricao());
            }
            listView.setItems(tarefaList);
        }
    }

    private void criarTarefa() {
        Stage tarefaStage = new Stage();
        tarefaStage.setTitle("Criar Tarefa");
        
        TextField tituloField = new TextField();
        TextArea descricaoField = new TextArea();
        DatePicker dataPicker = new DatePicker();
        ComboBox<String> prioridadeBox = new ComboBox<>();
        prioridadeBox.getItems().addAll("Alta", "Média", "Baixa");
        Button salvarButton = new Button("Salvar");
        
        salvarButton.setOnAction(e -> {
            String titulo = tituloField.getText();
            String descricao = descricaoField.getText();
            LocalDateTime dataVencimento = dataPicker.getValue().atStartOfDay();
            String prioridade = prioridadeBox.getValue();

            if (titulo.isEmpty() || descricao.isEmpty() || dataVencimento == null || prioridade == null) {
                showAlert(Alert.AlertType.ERROR, tarefaStage, "Erro", "Preencha todos os campos.");
            } else {
                Tarefa tarefa = new Tarefa(titulo, descricao, dataVencimento, prioridade, new Utilizador());
                tarefaService.guardar(tarefa);
                carregarTarefas();
                tarefaStage.close();
            }
        });

        VBox layout = new VBox(10, tituloField, descricaoField, dataPicker, prioridadeBox, salvarButton);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 400, 300);
        tarefaStage.setScene(scene);
        tarefaStage.show();
    }

    private void eliminarTarefa() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tarefaService.eliminar(selectedIndex + 1L); // Apenas uma simulação, ajuste conforme necessário
            carregarTarefas();
        }
    }

    private void concluirTarefa() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            showAlert(Alert.AlertType.INFORMATION, null, "Concluído", "Tarefa concluída com sucesso!");
            carregarTarefas();
        }
    }

    private void showAlert(Alert.AlertType alertType, Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (owner != null) {
            alert.initOwner(owner);
        }
        alert.show();
    }
}
