package br.com.ads.testemongo.SpringMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * classe que representa o funcionario.
 * */
@Data
@Document
public class Funcionario {

    @Id
    private String codigo;
    private String nome;
    private Integer idade;
    private Double salario;
    @DBRef
    private Funcionario chefe;

    public Funcionario() {
    }

    public Funcionario(final String codigo, final String nome, final Integer idade, final Double salario, final Funcionario chefe) {
        this(codigo);
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.chefe = chefe;
    }

    public Funcionario(final String codigo, final String nome, final Integer idade, final Double salario) {
        this(codigo, nome, idade, salario, null);
    }

    public Funcionario(final String nome, final Integer idade, final Double salario) {
        this("", nome, idade, salario);
    }

    public Funcionario(final String nome, final Integer idade) {
        this(nome, idade, 0.0d);
    }

    public Funcionario(final String codigo) {
        this.codigo = codigo;
    }

    public void set(Funcionario outro) {
        if (this != outro) {
            nome = outro.nome;
            idade = outro.idade;
            salario = outro.salario;
            chefe = outro.chefe;
        }
    }
}
