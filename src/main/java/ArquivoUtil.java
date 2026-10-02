import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    private static final String ARQUIVO = "dados_pessoas.txt";

    public static void salvar(List<Pessoa> pessoas) throws Exception {
        FileWriter arquivo = new FileWriter(ARQUIVO);

        for (Pessoa pessoa : pessoas) {
            arquivo.write(
                    pessoa.getNome() + "," +
                            pessoa.getAltura() + "," +
                            pessoa.getPeso() + "\n"
            );
        }

        arquivo.close();
    }

    public static List<Pessoa> carregar() throws Exception {
        List<Pessoa> pessoas = new ArrayList<>();

        BufferedReader arquivo = new BufferedReader(
                new FileReader(ARQUIVO)
        );

        String linha;

        while ((linha = arquivo.readLine()) != null) {
            String[] dados = linha.split(",");

            String nome = dados[0];
            double altura = Double.parseDouble(dados[1]);
            double peso = Double.parseDouble(dados[2]);

            pessoas.add(new Pessoa(nome, altura, peso));
        }

        arquivo.close();

        return pessoas;
    }
}