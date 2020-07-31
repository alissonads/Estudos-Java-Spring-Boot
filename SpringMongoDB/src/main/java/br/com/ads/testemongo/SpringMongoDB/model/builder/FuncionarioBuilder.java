package br.com.ads.testemongo.SpringMongoDB.model.builder;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;

import java.util.Map;

public class FuncionarioBuilder {
    private Funcionario funcionario;

    public FuncionarioBuilder() {
        funcionario = new Funcionario();
    }

    public FuncionarioBuilder(final Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public  FuncionarioBuilder setCodigo(final String codigo) {
        funcionario.setCodigo(codigo);
        return this;
    }

    public  FuncionarioBuilder setNome(final String nome) {
        funcionario.setNome(nome);
        return this;
    }

    public  FuncionarioBuilder setIdade(final Integer idade) {
        funcionario.setIdade(idade);
        return this;
    }

    public  FuncionarioBuilder setSalario(final Double salario) {
        funcionario.setSalario(salario);
        return this;
    }

    public  FuncionarioBuilder setChefe(final Funcionario chefe) {
        funcionario.setChefe(chefe);
        return this;
    }

    public Funcionario criar() {
        return funcionario;
    }

    public  FuncionarioBuilder copiarDados(final Funcionario funcionario) {

        this.funcionario.set(funcionario);
        return this;
    }

    public FuncionarioBuilder adicionarDados(final Map<String, Object> dados) {

        if (dados.containsKey("nome")) {
            setNome(dados.get("nome").toString());
        }
        if (dados.containsKey("idade")){
            setIdade((Integer)dados.get("idade"));
        }
        if (dados.containsKey("salario")) {
            setSalario((Double)dados.get("salario"));
        }
        if (dados.containsKey("chefe")) {
            Map<String, Object> temp = (Map<String, Object>)dados.get("chefe");
            if (temp.get("codigo") == null) {
                setChefe(null);
            } else {
                Funcionario chefe = new Funcionario(temp.get("codigo").toString());
                setChefe(chefe);
            }
        }

        return this;
    }
}
