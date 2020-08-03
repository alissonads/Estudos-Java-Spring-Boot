package br.com.ads.testemongo.SpringMongoDB.service;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.model.builder.FuncionarioBuilder;
import br.com.ads.testemongo.SpringMongoDB.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * É a classe responsael pelo serviço.
 * Quem da a ordem de de buscar, atualizar, deletar um funcionario.
 */
@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    /**
     * Construtor do Serviço;
     *
     * @param funcionarioRepository do tipo FuncionarioRepository é uma referência a uma implementação
     *                              da interface FuncionarioRepository
     */
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Método que da a ordem de buscar todos os Funcionarios.
     *
     * @return: retorna uma "List<Funcionario>" coms os resultados.
     */
    public List<Funcionario> obterTodos() {
        /** Faz a busca de todos os funcionarios. */
        return funcionarioRepository.findAll();
    }

    /**
     * Método que da a ordem de buscar um Funcionario pelo seu identificador.
     *
     * @param codigo do tipo String é o Identificador do Funcionario.
     * @return: retorna o Funcionario caso ele exista se não laça uma exceção.
     */
    public Funcionario obterPorCodigo(String codigo) {
        /** faz a busca passando o código*/
        return funcionarioRepository
                    .findById(codigo)
                    .orElseThrow(
                            () -> new FuncionarioNotFoundException("Funcionario não existe.")
                    );
    }

    /**
     * Métodod que dá a ordem de criação de um Funcionario.
     *
     * @param funcionario do tipo Funcionario é o funcionario que será criado.
     * @return: retorna o funcionario que foi criado.
     */
    public Funcionario criar(final Funcionario funcionario) {
        /** Para guardar a atualização do Funcionario caso exista um chefe registrado.*/
        Funcionario temp = null;

        /** se a referência ao funcionario possuir um chefe registrado. */
        if (funcionario.getChefe() != null) {
            /**
             * -> Cria um funcionario;
             * -> Copia os dados da referência;
             * -> Busca o Funcionario(Chefe) pelo seu Identificador;
             * -> Atualiza no funcionario o seu chefe;
             * -> Retorna-o atualizado.
             * */
            temp = new FuncionarioBuilder()
                                .copiarDados(funcionario)
                                /** atualiza os dados do chefe. */
                                .setChefe(this.funcionarioRepository
                                                .findById(funcionario.getChefe().getCodigo())
                                                .orElseThrow(() -> new FuncionarioNotFoundException(
                                                                "Chefe não existente."
                                                            )
                                          ))
                                .criar();
        }

        /**
         * Se o atualizou os dados com o chefe. Passa o funcionario atualizado (temp),
         * caso contrário passa o funcionario original (referência passada por parametro)
         * */
        return funcionarioRepository.save((temp != null)? temp : funcionario);
    }

    /**
     * Método que dá a ordem de busca pelos funcionaros que estão dentro de um Randge de idade.
     *
     * @param de do tipo Integer é o inicio
     * @param ate do tipo Integer é o limite permitido
     * @return: retorna uma "List<Funcionario>" coms os resultados.
     */
    public List<Funcionario> obterFuncionariosPorRangeDeIdade(Integer de, Integer ate) {
        /** Busca os funcionario pelo Rande de idade. */
        return funcionarioRepository.obterFuncionariosPorRangeDeIdade(de, ate);
    }

    /**
     * Método que dá a ordem de buscar o(s) Funcionario(s) pelo nome.
     *
     * @param nome do tipo String é o nome do(s) Funcionario(s).
     * @return: retorna uma "List<Funcionario>" coms os resultados.
     */
    public List<Funcionario> obterFuncionarioPorNome(String nome) {
        /** Busca o(s) Funcionario(s) pelo nome. */
        return funcionarioRepository.findByNome(nome);
    }

    /**
     * Método que dá a ordem de Atualizar o Funcionario pelo seu Identificador.
     *
     * @param codigo do tipo String é o Identificador do Funcionario.
     * @param outro do tipo Funcionario é que será compiados os dados para atualização
     * @return: retorna o Funcionario que foi atualizado.
     * @throws FuncionarioNotFoundException lança uma exceção caso ocorra algun erro.
     */
    public Funcionario atualizar(String codigo, Funcionario outro) throws FuncionarioNotFoundException {
        /**
         * -> Pega a referencia do resultado da busca do Funcionario pelo seu Identificador;
         * -> Copia os dados da referência do outro funcionario para ser atualizado;
         * -> retorna o funcionario atualizado.
         *
         * */
        Funcionario funcionario = new FuncionarioBuilder(this.obterPorCodigo(codigo))
                                        .copiarDados(outro)
                                        .criar();

        /** salva os dados do funcionario atualizado. */
        funcionarioRepository.save(funcionario);

        /**
         * busca os dados alterados para atualizar o todos os dados de retorno
         * inclusive os do chefe(caso exista).
         * */
        funcionario = this.obterPorCodigo(funcionario.getCodigo());

        return funcionario;
    }

    /**
     * Método que dá a ordem de Atualização total o parcial do Funcionario pelo seu Identificador.
     *
     * @param codigo do tipo String é o Identificador do Funcionario.
     * @param dados do tipo Map<String, Object> é os dados para atualização.
     * @return: retorna o Funcionario que foi atualizado.
     * @throws FuncionarioNotFoundException lança uma exceção caso ocorra algun erro.
     */
    public Funcionario atualizarParcial(String codigo, Map<String, Object> dados) throws FuncionarioNotFoundException {
        /**
         * -> Pega a referencia do resultado da busca do Funcionario pelo seu Identificador;
         * -> Adicioana os dados para ser atualizado;
         * -> retorna o funcionario atualizado.
         *
         * */
        Funcionario funcionario = new FuncionarioBuilder(this.obterPorCodigo(codigo))
                                        .adicionarDados(dados)
                                        .criar();
        /** salva os dados do funcionario atualizado. */
        funcionarioRepository.save(funcionario);
        /**
         * busca os dados alterados para atualizar o todos os dados de retorno
         * inclusive os do chefe(caso exista).
         * */
        funcionario = this.obterPorCodigo(funcionario.getCodigo());

        return funcionario;
    }

    /**
     * Método que dá a ordem para deletar o Funcionario pelo seu Identificador.
     *
     * @param codigo do tipo String é o Identificador do Funcionario.
     */
    public void deletar(String codigo) {
        /** Deleta através do Identificador */
        funcionarioRepository.deleteById(codigo);
    }
}
