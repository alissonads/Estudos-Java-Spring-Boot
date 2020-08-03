package br.com.ads.testemongo.SpringMongoDB.controller;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador de Funcionarios que faz a interação entre a requisição do cliente
 * e a regra de negócio das funcionalidades.
* */
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    /**
     * Constutor do Controller.
     *
     * @param funcionarioService do tipo FuncionarioService é uma referência ao serviço responsavel.
     */
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * Método que obtem todos os Funcionarios registrados no banco de dados.
     *
     * @return: returna uma "List<Funcionarios>" defuncionarios, e código 200 caso tenha sucesso.
     */
    @GetMapping
    public List<Funcionario> obterTodos() {
        return funcionarioService.obterTodos();
    }

    /**
     * Método que obtem um Funcionario registrados no banco de dados pelo seu identificador.
     *
     * @param codigo do tipo String, contém o identificador do funcionario
     * @return: returna a Resposta(201) com o funcionario caso o encontre.
     */
    @GetMapping("/{codigo}")
    public ResponseEntity<Funcionario> obterPorCodigo(@PathVariable("codigo") String codigo) {

        final Funcionario funcionario = funcionarioService.obterPorCodigo(codigo);

        return ResponseEntity.ok(funcionario);
    }

    /**
     * Método para criação de um funcionario (método HTTP POST).
     *
     * @param funcionario do tipo Funcionario que será resgistrado no Banco de dados.
     * @return: retorna o funcionario registrado no Banco de dados, e código 201 caso tenha sucesso.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {
        /** Faz a requisição de Criação e recebe o Funcionario criado*/
        final Funcionario resposta = funcionarioService.criar(funcionario);

        /** Retorna a resposta */
        return ResponseEntity.ok(resposta);
    }


    /**
     * Método que obtem através Range da idade de Funcionarios registrados no banco de dados.
     *
     * Exemplo de utilização: http://localhost:8080/funcionarios/range?de=30&ate=40
     *
     * @param de do tipo Integer é a partir de qual idade entrara na lista.
     * @param ate do tipo Integer é o limite de idade aceitável que entrará na lista.
     * @return: retorna uma "List<Funcionario>" com os resultados.
     */
    @GetMapping("/range")
    public List<Funcionario> obterFuncionariosPorRangeDeIdade(@RequestParam("de") Integer de,
                                                              @RequestParam("ate") Integer ate){
        /** Faz a requisição de Buscar por Range de idade e retorna a Lista*/
        return funcionarioService.obterFuncionariosPorRangeDeIdade(de, ate);
    }

    /**
     * Método que obtem Funcionarios registrados no banco de dados pelo nome.
     *
     * Exemplo de utilização: http://localhost:8080/funcionarios/por-nome?nome=Fulano
     *
     * @param nome do tipo String, contém o nome do(s) funcionario(s)
     * @return: returna uma "List<Funcionario>" com os resultados, e código 200 caso tenha sucesso.
     */
    @GetMapping("/por-nome")
    public List<Funcionario> obterFuncionarioPorNome(@RequestParam("nome") String nome) {
        /** Faz a requisição de Buscar por nome e retorna a Lista*/
        return funcionarioService.obterFuncionarioPorNome(nome);
    }

    /**
     * Método que atualiza todos os dados de um funcioario utilizando o seu Identificador (Método HTTP PUT)
     *
     * @param codigo do tipo String é o Identificador do funcionario.
     * @param outro do tipo Funcionario São todos os dados que serão copiados e atualizados no funcionario solicitado
     *              pelo seu Identificador.
     * @return: Caso tenha sucesso retorna o funcionario atualizado, e código 200 caso tenha sucesso.
     * @throws FuncionarioNotFoundException Lança a exceção caso não exista
     */
    @PutMapping("/{codigo}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable("codigo") String codigo,
                                                 @RequestBody Funcionario outro) throws FuncionarioNotFoundException {
        /** Faz a requisição de atualização total e recebe o Funcionario atualizado.*/
        final Funcionario funcionario = funcionarioService.atualizar(codigo, outro);

        /** Retorna a resposta */
        return ResponseEntity.ok(funcionario);
    }

    /**
     * Método que atualiza tudo ou de forma parcial os dados de um funcioario,
     * utilizando o seu Identificador (Método HTTP PATCH)
     *
     * @param codigo do tipo String é o Identificador do funcionario.
     * @param dados do tipo "Map<String, Object>" são os dados do funcionario identificado pelo nome do atributo
     * @return: Caso tenha sucesso retorna o funcionario atualizado, e código 200 caso tenha sucesso.
     * @throws FuncionarioNotFoundException Lança a exceção caso não exista
     */
    @PatchMapping("/{codigo}")
    public ResponseEntity<Funcionario> atualizarParcial(@PathVariable("codigo") String codigo,
                                                        @RequestBody Map<String, Object> dados) throws FuncionarioNotFoundException {
        /** Faz a requisição de atualização total ou parcial e recebe o Funcionario atualizado.*/
        final Funcionario funcionario = funcionarioService.atualizarParcial(codigo, dados);

        /** Retorna a resposta */
        return ResponseEntity.ok(funcionario);
    }

    /**
     * Método que deleta um funcionario através de seu identificador.
     *
     * @param codigo do tipo String é o Identificador do funcionario.
     *
     * @return: retorna o código 204 caso tenha sucesso
     */
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("codigo") String codigo) {
        /** Faz a requisição para deletar o Funcionario */
        funcionarioService.deletar(codigo);
    }
}
