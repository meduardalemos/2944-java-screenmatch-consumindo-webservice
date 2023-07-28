package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Abre o scanner, instancia a variável busca e inicia a lista que irá receber os titulos
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> titulos = new ArrayList<>();

        // Configura o gson builder
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        //Loop que pergunta o nome do filme até o usuário digitar sair
        while(!busca.equalsIgnoreCase("sair")){

            System.out.println("Digite o filme que deseja buscar: ");
            busca = leitura.nextLine();

            if(busca.equalsIgnoreCase("sair")){
                break;
            }

            // Configura o endereço de busca na API
            String endereco = "http://www.omdbapi.com/?t=" + busca.replace(" ", "+") +
                    "&apikey=20249976";
            try {
                // Solicita a API as informações através do endereço de busca e protocolo HTTP
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                // Atribui ao json a resposta fornecida pela API
                String json = response.body();
                /* Utiliza o record meuTituloOmbd para converter os dados obtidos via API em
                meuTitulo */
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                // Adiciona o titulo convertido à lista
                titulos.add(meuTitulo);
            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereço.");
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMensagem());
            }
        }

        // Cria arquivo json e escreve nele o conteúdo da lista
        FileWriter escrita = new FileWriter("filmes.jason");
        escrita.write(gson.toJson(titulos));
        escrita.close();

        System.out.println("O programa foi finalizado corretamente!");
    }
}
