package App;



import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import Model.Tarefa;
import Model.TarefaPessoal;
import Model.TarefaTrabalho;
import Model.Utilizador;
import Rest.RestClient;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AppPrincipal extends Application {

    RestClient restClient = new RestClient();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showLoginScreen(primaryStage);
    }

    private void showLoginScreen(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();

        Button btnConfirm = new Button("Confirmar");
        Button btnCreateAccount = new Button("Criar Conta");

        root.add(lblEmail, 0, 0);
        root.add(txtEmail, 1, 0);
        root.add(lblPassword, 0, 1);
        root.add(txtPassword, 1, 1);
        root.add(btnConfirm, 0, 2);
        root.add(btnCreateAccount, 1, 2);

        btnConfirm.setOnAction(ae -> {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            handleLogin(email, password, primaryStage);
        });

        btnCreateAccount.setOnAction(ae -> showCreateAccountScreen(primaryStage));

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(String email, String password, Stage primaryStage) {
        Utilizador user = restClient.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            showMessage("Login bem-sucedido!", AlertType.INFORMATION);
            if(user.isAdmin()) {
                showAdminWindow(primaryStage);
            }else {
                openMainApp(primaryStage, user);
            }
        } else {
            showMessage("Email ou senha incorretos.", AlertType.ERROR);
        }
    }

    private void showCreateAccountScreen(Stage primaryStage) {
        Stage stage = new Stage();
        stage.setTitle("Criar Conta");

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        Label lblName = new Label("Nome:");
        TextField txtName = new TextField();
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();

        Button btnSave = new Button("Salvar");
        Button btnCancel = new Button("Cancelar");

        root.add(lblName, 0, 0);
        root.add(txtName, 1, 0);
        root.add(lblEmail, 0, 1);
        root.add(txtEmail, 1, 1);
        root.add(lblPassword, 0, 2);
        root.add(txtPassword, 1, 2);
        root.add(btnSave, 0, 3);
        root.add(btnCancel, 1, 3);

        btnSave.setOnAction(ae -> {
            Utilizador newUser = new Utilizador();
            newUser.setNome(txtName.getText());
            newUser.setEmail(txtEmail.getText());
            newUser.setPassword(txtPassword.getText());

            if (restClient.createUser(newUser)) {
                showMessage("Conta criada com sucesso!", AlertType.INFORMATION);
                stage.close();
            } else {
                showMessage("Erro ao criar conta.", AlertType.ERROR);
            }
        });

        btnCancel.setOnAction(ae -> stage.close());

        Scene scene = new Scene(root, 350, 200);
        stage.setScene(scene);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }

    private void showTaskListWindow(Stage primaryStage) {
        Stage stage = new Stage();
        stage.setTitle("Lista de Tarefas");
    
        ListView<String> listView = new ListView<>();
        List<Tarefa> tasks = getAllTarefas();
    
        CheckBox chkVerTodas = new CheckBox("Ver todas");
        CheckBox chkPessoal = new CheckBox("Pessoal");
        CheckBox chkTrabalho = new CheckBox("Trabalho");
    
        chkVerTodas.setSelected(true);
    
        chkVerTodas.setOnAction(e -> {
            if (chkVerTodas.isSelected()) {
                chkPessoal.setSelected(false);
                chkTrabalho.setSelected(false);
                atualizarListaTarefas(listView, tasks);
            }
        });
        
        chkPessoal.setOnAction(e -> {
            if (chkPessoal.isSelected()) {
                chkVerTodas.setSelected(false);
                chkTrabalho.setSelected(false);
                atualizarListaTarefasPessoal(listView, getAllTarefasPessoais());
            }
        });
        
        chkTrabalho.setOnAction(e -> {
            if (chkTrabalho.isSelected()) {
                chkVerTodas.setSelected(false);
                chkPessoal.setSelected(false);
                atualizarListaTarefasTrabalho(listView, getAllTarefasTrabalho());
            }
        });
        
        atualizarListaTarefas(listView, tasks);
    
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnVoltar = new Button("Voltar");
    
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean selecionado = (newSelection != null);
            btnEditar.setDisable(!selecionado);
            btnEliminar.setDisable(!selecionado);
        });
    
        btnEditar.setOnAction(ae -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Tarefa tarefaSelecionada = tasks.get(index);
                showEditTaskWindow(stage, tarefaSelecionada);
                atualizarListaTarefas(listView, tasks);
            }
        });
    
        btnEliminar.setOnAction(ae -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Tarefa tarefaSelecionada = tasks.get(index);
                restClient.deleteTarefa(tarefaSelecionada.getId());
                tasks.remove(index);
                atualizarListaTarefas(listView, tasks);
            }
        });
    
        btnVoltar.setOnAction(ae -> stage.close());
    
        HBox buttonBox = new HBox(10, btnEditar, btnEliminar, btnVoltar);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
    
        HBox filterBox = new HBox(10, chkVerTodas, chkPessoal, chkTrabalho);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));
    
        VBox root = new VBox(10, filterBox, listView, buttonBox);
        root.setPadding(new Insets(10));
    
        Scene scene = new Scene(root, 400, 350);
        stage.setScene(scene);
        stage.show();
    }
    
    private void atualizarListaTarefas(ListView<String> listView, List<Tarefa> tarefasFiltradas) {
        listView.getItems().clear();
        for (Tarefa task : tarefasFiltradas) {
            listView.getItems().add(task.getTitulo() + " - " + task.getEstado());
        }
    }
    private void atualizarListaTarefasPessoal(ListView<String> listView, List<TarefaPessoal> tarefasFiltradas) {
        listView.getItems().clear();
        for (TarefaPessoal task : tarefasFiltradas) {
            listView.getItems().add(task.getTitulo() + " - " + task.getEstado());
        }
    }
    private void atualizarListaTarefasTrabalho(ListView<String> listView, List<TarefaTrabalho> tarefasFiltradas) {
        listView.getItems().clear();
        for (TarefaTrabalho task : tarefasFiltradas) {
            listView.getItems().add(task.getTitulo() + " - " + task.getEstado());
        }
    }
    private List<TarefaPessoal> getAllTarefasPessoais() {
        return restClient.getAllTarefasPessoais();  // Assumindo que RestClient tem um método para buscar tarefas pessoais
    }

    private List<TarefaTrabalho> getAllTarefasTrabalho() {
        return restClient.getAllTarefasTrabalho();  // Assumindo que RestClient tem um método para buscar tarefas de trabalho
    }


    
    

    // Método para buscar tarefas da API
    private List<Tarefa> getAllTarefas() {
        return restClient.getAllTarefas();  // Assumindo que RestClient tem um método para buscar tarefas
    }

    private void openMainApp(Stage primaryStage, Utilizador user) {
        // Botões
        Button btnVerTarefas = new Button("Lista de Tarefas");
        Button btnAdicionarTarefa = new Button("Adicionar Tarefa");
        Button btnPerfil = new Button("Perfil");

        // Ações dos botões
        btnVerTarefas.setOnAction(e -> showTaskListWindow(primaryStage));
        btnAdicionarTarefa.setOnAction(e -> showAddTaskWindow(primaryStage, user));
        btnPerfil.setOnAction(e -> showProfileWindow(primaryStage, user));

        // Layout
        VBox root = new VBox(10, btnVerTarefas, btnAdicionarTarefa, btnPerfil);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Cena
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddTaskWindow(Stage primaryStage, Utilizador user) {
        Stage stage = new Stage();
        stage.setTitle("Adicionar Tarefa");
    
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
    
        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();
        Label lblDescricao = new Label("Descrição:");
        TextField txtDescricao = new TextField();
        Label lblDataValidade = new Label("Data de Validade:");
        DatePicker dpDataValidade = new DatePicker();
        Label lblPrioridade = new Label("Prioridade:");
        TextField txtPrioridade = new TextField();
        Label lblTipoTarefa = new Label("Tipo de Tarefa:");
        ComboBox<String> cbTipoTarefa = new ComboBox<>();
        cbTipoTarefa.getItems().addAll("Pessoal", "Trabalho");
    
        // Campos específicos para Tarefa Pessoal
        Label lblLocalizacao = new Label("Localização:");
        TextField txtLocalizacao = new TextField();
        Label lblTempoEstimadoPessoal = new Label("Tempo Estimado (minutos):");
        TextField txtTempoEstimadoPessoal = new TextField();
        Label lblRepete = new Label("Repete:");
        CheckBox chkRepete = new CheckBox();
        Label lblFrequencia = new Label("Frequência de Repetição:");
        TextField txtFrequencia = new TextField();
    
        // Campos específicos para Tarefa Trabalho
        Label lblNomeProjeto = new Label("Nome do Projeto:");
        TextField txtNomeProjeto = new TextField();
        Label lblNomeEquipa = new Label("Nome da Equipe:");
        TextField txtNomeEquipa = new TextField();
        Label lblTipoTrabalho = new Label("Tipo de Trabalho:");
        TextField txtTipoTrabalho = new TextField();
        Label lblTempoEstimadoTrabalho = new Label("Tempo Estimado (horas):");
        TextField txtTempoEstimadoTrabalho = new TextField();
    
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
    
        root.add(lblTitulo, 0, 0);
        root.add(txtTitulo, 1, 0);
        root.add(lblDescricao, 0, 1);
        root.add(txtDescricao, 1, 1);
        root.add(lblDataValidade, 0, 2);
        root.add(dpDataValidade, 1, 2);
        root.add(lblPrioridade, 0, 3);
        root.add(txtPrioridade, 1, 3);
        root.add(lblTipoTarefa, 0, 4);
        root.add(cbTipoTarefa, 1, 4);
        root.add(btnSalvar, 0, 10);
        root.add(btnCancelar, 1, 10);
    
        // Evento para atualizar os campos com base na seleção do tipo de tarefa
        cbTipoTarefa.setOnAction(event -> {
            root.getChildren().removeAll(lblLocalizacao, txtLocalizacao, lblTempoEstimadoPessoal, txtTempoEstimadoPessoal,
                    lblRepete, chkRepete, lblFrequencia, txtFrequencia, lblNomeProjeto, txtNomeProjeto,
                    lblNomeEquipa, txtNomeEquipa, lblTipoTrabalho, txtTipoTrabalho, lblTempoEstimadoTrabalho,
                    txtTempoEstimadoTrabalho);
    
            if (cbTipoTarefa.getValue().equals("Pessoal")) {
                root.add(lblLocalizacao, 0, 5);
                root.add(txtLocalizacao, 1, 5);
                root.add(lblTempoEstimadoPessoal, 0, 6);
                root.add(txtTempoEstimadoPessoal, 1, 6);
                root.add(lblRepete, 0, 7);
                root.add(chkRepete, 1, 7);
                root.add(lblFrequencia, 0, 8);
                root.add(txtFrequencia, 1, 8);
            } else if (cbTipoTarefa.getValue().equals("Trabalho")) {
                root.add(lblNomeProjeto, 0, 5);
                root.add(txtNomeProjeto, 1, 5);
                root.add(lblNomeEquipa, 0, 6);
                root.add(txtNomeEquipa, 1, 6);
                root.add(lblTipoTrabalho, 0, 7);
                root.add(txtTipoTrabalho, 1, 7);
                root.add(lblTempoEstimadoTrabalho, 0, 8);
                root.add(txtTempoEstimadoTrabalho, 1, 8);
            }
        });
    
        btnSalvar.setOnAction(ae -> {
            if (txtTitulo.getText().isEmpty() || txtDescricao.getText().isEmpty() || 
                dpDataValidade.getValue() == null || txtPrioridade.getText().isEmpty() || 
                cbTipoTarefa.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, preencha todos os campos.");
                alert.show();
                return;
            }
    
            if (cbTipoTarefa.getValue().equals("Pessoal")) {
                TarefaPessoal novaTarefa = new TarefaPessoal(txtTitulo.getText(), txtDescricao.getText(),
                        dpDataValidade.getValue().atTime(18, 0), txtPrioridade.getText(), user,
                        txtLocalizacao.getText(), Integer.parseInt(txtTempoEstimadoPessoal.getText()),
                        chkRepete.isSelected(), txtFrequencia.getText());
                restClient.createTarefaPessoal(novaTarefa);
            } else if (cbTipoTarefa.getValue().equals("Trabalho")) {
                TarefaTrabalho novaTarefa = new TarefaTrabalho(txtTitulo.getText(), txtDescricao.getText(),
                        dpDataValidade.getValue().atTime(18, 0), txtPrioridade.getText(), user,
                        txtNomeProjeto.getText(), txtNomeEquipa.getText(), txtTipoTrabalho.getText(),
                        Double.parseDouble(txtTempoEstimadoTrabalho.getText()));
                restClient.createTarefaTrabalho(novaTarefa);
            }
    
            stage.close();
        });
    
        btnCancelar.setOnAction(ae -> stage.close());
    
        Scene scene = new Scene(root, 500, 350);
        stage.setScene(scene);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
    

    

private void showEditUserWindow(Stage stage, Utilizador user) {
    Stage editStage = new Stage();
    editStage.setTitle("Editar Utilizador");

    GridPane root = new GridPane();
    root.setHgap(10);
    root.setVgap(10);
    root.setAlignment(Pos.CENTER);

    Label lblNome = new Label("Nome:");
    TextField txtNome = new TextField(user.getNome());
    Label lblEmail = new Label("Email:");
    TextField txtEmail = new TextField(user.getEmail());
    
    Button btnSalvar = new Button("Salvar");
    Button btnCancelar = new Button("Cancelar");

    root.add(lblNome, 0, 0);
    root.add(txtNome, 1, 0);
    root.add(lblEmail, 0, 1);
    root.add(txtEmail, 1, 1);
    root.add(btnSalvar, 0, 3);
    root.add(btnCancelar, 1, 3);

    btnSalvar.setOnAction(ae -> {
        user.setNome(txtNome.getText());
        user.setEmail(txtEmail.getText());
        

        restClient.updateUtilizador(user);
        editStage.close();
    });

    btnCancelar.setOnAction(ae -> editStage.close());

    Scene scene = new Scene(root, 400, 250);
    editStage.setScene(scene);
    editStage.initOwner(stage);
    editStage.initModality(Modality.WINDOW_MODAL);
    editStage.showAndWait();
}

private void showEditTaskWindow(Stage stage, Tarefa tarefa) {
    Stage editStage = new Stage();
    editStage.setTitle("Editar Tarefa");

    GridPane root = new GridPane();
    root.setHgap(10);
    root.setVgap(10);
    root.setAlignment(Pos.CENTER);

    Label lblTitulo = new Label("Título:");
    TextField txtTitulo = new TextField(tarefa.getTitulo());
    Label lblDescricao = new Label("Descrição:");
    TextField txtDescricao = new TextField(tarefa.getDescricao());
    Label lblDataValidade = new Label("Data de Validade:");
    DatePicker dpDataValidade = new DatePicker(tarefa.getDataDeValidade().toLocalDate());
    Label lblPrioridade = new Label("Prioridade:");
    TextField txtPrioridade = new TextField(tarefa.getPrioridade());

    Button btnSalvar = new Button("Salvar");
    Button btnCancelar = new Button("Cancelar");

    root.add(lblTitulo, 0, 0);
    root.add(txtTitulo, 1, 0);
    root.add(lblDescricao, 0, 1);
    root.add(txtDescricao, 1, 1);
    root.add(lblDataValidade, 0, 2);
    root.add(dpDataValidade, 1, 2);
    root.add(lblPrioridade, 0, 3);
    root.add(txtPrioridade, 1, 3);
    root.add(btnSalvar, 0, 4);
    root.add(btnCancelar, 1, 4);

    btnSalvar.setOnAction(ae -> {
        tarefa.setTitulo(txtTitulo.getText());
        tarefa.setDescricao(txtDescricao.getText());
        tarefa.setDataDeValidade(dpDataValidade.getValue().atTime(18, 0));
        tarefa.setPrioridade(txtPrioridade.getText());

        restClient.updateTarefa(tarefa);
        editStage.close();
    });

    btnCancelar.setOnAction(ae -> editStage.close());

    Scene scene = new Scene(root, 400, 250);
    editStage.setScene(scene);
    editStage.initOwner(stage);
    editStage.initModality(Modality.WINDOW_MODAL);
    editStage.showAndWait();
}

private void showProfileWindow(Stage primaryStage, Utilizador user) {
    Stage stage = new Stage();
    stage.setTitle("Perfil");

    VBox root = new VBox(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));

    Label lblNome = new Label("Nome: " + user.getNome());
    Label lblEmail = new Label("Email: " + user.getEmail());
    Label lblAdmin = new Label("Administrador: " + (user.isAdmin() ? "Sim" : "Não"));
    Label lblDataCriacao = new Label("Data de Criação: " + user.getDataCriacao().toString());

    Button btnFechar = new Button("Fechar");
    btnFechar.setOnAction(e -> stage.close());

    root.getChildren().addAll(lblNome, lblEmail, lblAdmin, lblDataCriacao, btnFechar);

    Scene scene = new Scene(root, 350, 250);
    stage.setScene(scene);
    stage.initOwner(primaryStage);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.showAndWait();
}


private void showAdminWindow(Stage primaryStage) {
    primaryStage.setTitle("Administração");

    VBox root = new VBox(10);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));

    Label lblAdmin = new Label("Bem-vindo, Administrador!");

    Button btnVerUtilizadores = new Button("Ver Utilizadores");
    Button btnVerTarefas = new Button("Ver Tarefas");
    Button btnLogout = new Button("Logout");
    Button btnVoltar = new Button("Voltar");

    btnVerUtilizadores.setOnAction(e -> showUserListWindow(primaryStage));
    btnVerTarefas.setOnAction(e -> showTaskListWindow(primaryStage));
    btnLogout.setOnAction(e -> showLoginScreen(primaryStage));
    btnVoltar.setOnAction(e -> showLoginScreen(primaryStage));

    root.getChildren().addAll(lblAdmin, btnVerUtilizadores, btnVerTarefas, btnLogout, btnVoltar);

    Scene scene = new Scene(root, 300, 250);
    primaryStage.setScene(scene);
    primaryStage.show();
}

private void showUserListWindow(Stage primaryStage) {
    
    Stage stage = new Stage();
    stage.setTitle("Lista de Utilizadores");

    ListView<String> listView = new ListView<>();
    List<Utilizador> users = getAllUsers(); // Método para buscar todos os utilizadores
    for (Utilizador user : users) {
        listView.getItems().add(user.getNome() + " - " + user.getEmail());
    }

    Button btnEditar = new Button("Editar");
    Button btnEliminar = new Button("Eliminar");
    Button btnVoltar = new Button("Voltar");

    btnEditar.setDisable(true);
    btnEliminar.setDisable(true);

    listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        boolean selecionado = (newSelection != null);
        btnEditar.setDisable(!selecionado);
        btnEliminar.setDisable(!selecionado);
    });

    btnEditar.setOnAction(ae -> {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Utilizador userSelecionado = users.get(index);
            showEditUserWindow(stage, userSelecionado);
            atualizarListaUtilizadores(listView);
        }
    });

    btnEliminar.setOnAction(ae -> {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Utilizador userSelecionado = users.get(index);
            restClient.deleteUtilizador(userSelecionado.getId());
            listView.getItems().remove(index);
            users.remove(index);
        }
    });

    btnVoltar.setOnAction(ae -> stage.close());

    HBox buttonBox = new HBox(10, btnEditar, btnEliminar, btnVoltar);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));

    VBox root = new VBox(10, listView, buttonBox);
    root.setPadding(new Insets(10));

    Scene scene = new Scene(root, 400, 350);
    stage.setScene(scene);
    stage.show();
}

private List<Utilizador> getAllUsers() {
    return restClient.getAllUtilizadores();  // Assumindo que RestClient tem um método para buscar utilizadores
}

// Method to update the user list
private void atualizarListaUtilizadores(ListView<String> listView) {
    List<Utilizador> users = getAllUsers();
    listView.getItems().clear();
    for (Utilizador user : users) {
        listView.getItems().add(user.getNome() + " - " + user.getEmail());
    }
}









    private void showMessage(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Sistema");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}

