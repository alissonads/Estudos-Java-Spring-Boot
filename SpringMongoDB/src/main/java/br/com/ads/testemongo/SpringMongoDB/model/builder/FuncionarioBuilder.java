package br.com.ads.testemongo.SpringMongoDB.model.builder;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;

import java.util.Map;

/**
 * Builder para encapsular a construção e atualizão do funcionario.
 * Manipula os dados necessários.
 *
 * -> Constroi um funcionario vazio;
 * -> Constroi um funcionario utilizando os dados de outro funcionario;
 * -> Constroi um funcionario utilizando uma "Map" com os dados do funcionario;
 * */
public class FuncionarioBuilder {
    private Funcionario funcionario;

    /**
     * Construtor padrão
     */
    private FuncionarioBuilder() {
        funcionario = new Funcionario();
    }

    /**
     * @param funcionario: Recebe uma referência ao funcionario que será manipulado
     *                     e atualizado os seus dados.
     */
    private FuncionarioBuilder(final Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public static FuncionarioBuilder instancia() {
        return new FuncionarioBuilder();
    }

    public static FuncionarioBuilder instancia(final Funcionario funcionario) {
        return new FuncionarioBuilder(funcionario);
    }

    /**
     * Método que adicioa o codigo do funcionario.
     *
     * @param codigo do tipo String
     * @return: returna uma referência para o proprio builder.
     */
    public  FuncionarioBuilder setCodigo(final String codigo) {
        funcionario.setCodigo(codigo);
        return this;
    }

    /**
     * Método que adicioa o nome do funcionario.
     *
     * @param nome do tipo String
     * @return: returna uma referência para o proprio builder.
     */
    public  FuncionarioBuilder setNome(final String nome) {
        funcionario.setNome(nome);
        return this;
    }

    /**
     * Método que adicioa a idade do funcionario.
     *
     * @param idade do tipo Integer
     * @return: returna uma referência para o proprio builder.
     */
    public  FuncionarioBuilder setIdade(final Integer idade) {
        funcionario.setIdade(idade);
        return this;
    }

    /**
     * Método que adicioa o salário do funcionario.
     *
     * @param salario do tipo double
     * @return: returna uma referência para o proprio builder.
     */
    public  FuncionarioBuilder setSalario(final Double salario) {
        funcionario.setSalario(salario);
        return this;
    }

    /**
     * Método que adicioa o chefe do funcionario.
     *
     * @param chefe do tipo Funcionario
     * @return: returna uma referência para o proprio builder.
     */
    public  FuncionarioBuilder setChefe(final Funcionario chefe) {
        funcionario.setChefe(chefe);
        return this;
    }


    /**
     * Método que retorna o funcionario criado.
     *
     * @return: retorna um funcionario
     */
    public Funcionario criar() {
        return funcionario;
    }

    /**
     * Método responsável em manipular os dados de outro funcionario
     * para a criação do funcionario.
     *
     * obs: copia os dados do funcionario passado por parametro.
     *      criado específicamente para o método HTTP PUT.
     *
     * @param funcionario do tipo Funcionario
     * @return: returna uma referência para o proprio builder.
     * */
    public  FuncionarioBuilder copiarDados(final Funcionario funcionario) {

        this.funcionario.set(funcionario);
        return this;
    }

    /**
     * Método responsável em manipular os dados de um "Map"
     * para a criação do funcionario.
     *
     * obs: passar os nomes dos atributos desejados da coleção no banco de dados(noSql)
     *      que deseja atualizar.
     *      Criado especificamente para o método HTTP PATCH
     *
     * @param dados do tipo Map<String, Object> contendo os dados desejados.
     * @return: returna uma referência para o proprio builder.
     * */
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
            if (temp.containsKey("codigo") && temp.get("codigo") != null) {
                Funcionario chefe = new Funcionario(temp.get("codigo").toString());
                setChefe(chefe);
            } else {
                setChefe(null);
            }
        }

        return this;
    }
}
