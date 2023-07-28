package br.com.alura.screenmatch.excecao;

// Cria um tipo de exceção personalizada e extende de RuntimeException (não checada)
public class ErroDeConversaoDeAnoException extends RuntimeException {
    private String mensagem;

    public ErroDeConversaoDeAnoException(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
