package br.com.ads.testemongo.SpringMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
public class Funcionario {

    @Id
    private String codigo;
    private String nome;
    private Integer idade;
    private BigDecimal salario;
    @DBRef
    private Funcionario chefe;

    public void set(Funcionario outro) {
        nome = (outro.nome == null || outro.nome.isEmpty())? nome : outro.nome;
        idade = (outro.idade == null)? idade : outro.idade;
        salario = (outro.salario == null)? salario : outro.salario;
        chefe = outro.chefe;
    }


}
