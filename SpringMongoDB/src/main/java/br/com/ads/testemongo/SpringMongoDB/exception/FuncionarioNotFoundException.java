package br.com.ads.testemongo.SpringMongoDB.exception;

public class FuncionarioNotFoundException extends RuntimeException {

    public FuncionarioNotFoundException() {
        super();
    }

    public FuncionarioNotFoundException(String erro) {
        super(erro);
    }
}
