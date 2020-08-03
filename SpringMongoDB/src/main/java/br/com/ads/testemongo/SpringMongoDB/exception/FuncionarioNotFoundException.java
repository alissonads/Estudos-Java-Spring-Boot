package br.com.ads.testemongo.SpringMongoDB.exception;

/**
* Exceção customizada para o funcionario
* */
public class FuncionarioNotFoundException extends RuntimeException {

    public FuncionarioNotFoundException() {
        super();
    }

    public FuncionarioNotFoundException(String erro) {
        super(erro);
    }
}
