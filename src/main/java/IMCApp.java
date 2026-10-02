import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IMCApp extends Application {

    private TextField campoNome;
    private TextField campoAltura;
    private TextField campoPeso;

    private Label resultado;

    private TableView<Pessoa> tabela;
    private ObservableList<Pessoa> pessoas;

    @Override
    public void start(Stage janela) {

        pessoas = FXCollections.observableArrayList();

        campoNome = new TextField();
        campoNome.setPromptText("Nome");

        campoAltura = new TextField();
        campoAltura.setPromptText("Altura em metros");

        campoPeso = new TextField();
        campoPeso.setPromptText("Peso em kg");

        Button calcular = new Button("Calcular IMC");
        Button salvar = new Button("Salvar");
        Button carregar = new Button("Carregar");

        resultado = new Label("IMC: ");

        tabela = new TableView<>();

        TableColumn<Pessoa, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<Pessoa, Double> colunaAltura = new TableColumn<>("Altura");
        colunaAltura.setCellValueFactory(
                new PropertyValueFactory<>("altura")
        );

        TableColumn<Pessoa, Double> colunaPeso = new TableColumn<>("Peso");
        colunaPeso.setCellValueFactory(
                new PropertyValueFactory<>("peso")
        );

        TableColumn<Pessoa, Double> colunaImc = new TableColumn<>("IMC");
        colunaImc.setCellValueFactory(
                new PropertyValueFactory<>("imc")
        );

        TableColumn<Pessoa, String> colunaClassificacao =
                new TableColumn<>("Classificação");

        colunaClassificacao.setCellValueFactory(
                new PropertyValueFactory<>("classificacao")
        );

        tabela.getColumns().add(colunaNome);
        tabela.getColumns().add(colunaAltura);
        tabela.getColumns().add(colunaPeso);
        tabela.getColumns().add(colunaImc);
        tabela.getColumns().add(colunaClassificacao);

        tabela.setItems(pessoas);

        calcular.setOnAction(e -> calcularIMC());

        salvar.setOnAction(e -> salvarDados());

        carregar.setOnAction(e -> carregarDados());

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        layout.getChildren().addAll(
                campoNome,
                campoAltura,
                campoPeso,
                calcular,
                resultado,
                salvar,
                carregar,
                tabela
        );

        Scene cena = new Scene(layout, 700, 500);

        janela.setTitle("Calculadora de IMC");
        janela.setScene(cena);
        janela.show();
    }

    private void calcularIMC() {

        try {
            String nome = campoNome.getText();

            double altura = Double.parseDouble(
                    campoAltura.getText()
            );

            double peso = Double.parseDouble(
                    campoPeso.getText()
            );

            Pessoa pessoa = new Pessoa(
                    nome,
                    altura,
                    peso
            );

            pessoas.add(pessoa);

            resultado.setText(
                    "IMC: " +
                            String.format("%.2f", pessoa.getImc()) +
                            " - " +
                            pessoa.getClassificacao()
            );

        } catch (Exception e) {
            resultado.setText("Digite os dados corretamente.");
        }
    }

    private void salvarDados() {

        try {
            ArquivoUtil.salvar(pessoas);
            resultado.setText("Dados salvos com sucesso!");

        } catch (Exception e) {
            resultado.setText("Erro ao salvar os dados.");
        }
    }

    private void carregarDados() {

        try {
            pessoas.clear();

            pessoas.addAll(
                    ArquivoUtil.carregar()
            );

            resultado.setText("Dados carregados com sucesso!");

        } catch (Exception e) {
            resultado.setText("Erro ao carregar os dados.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}