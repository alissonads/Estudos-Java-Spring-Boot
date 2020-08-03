package br.com.ads.testemongo.SpringMongoDB.repository;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface referente ao Repositório do Funcionario.
 *
 * extende as Funcionalidade da interface MongoRepository, para utilizar
 * os métodos definidos que são importantes para a execução de manipulação
 * do Banco de dados (noSql).
 */
@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {

    /**
     * Método adicional customizado para consulta ao Banco de dados utilizando um Range da idade dos funcionarios.
     * obs: semelhate a função "between, and" do SQL.
     *
     * @param de do tipo Integer é o inicio da consulta
     * @param ate do tipo Integer é o limite da consulta
     * @return: retorna uma List<Funcionario> contendo os resultados.
     */
    @Query("{ $and : [{'idade': {$gte: ?0}}, {'idade': {$lte: ?1}}] }")
    public List<Funcionario> obterFuncionariosPorRangeDeIdade(Integer de, Integer ate);

    /**
     * Metodo adicional que busca atraves do nome do funcionario(s)
     *
     * @param nome do tipo String é o nome que se deseja buscar.
     *
     * @return: retorna uma List<Funcionario> contendo os resultados.
     */
    public List<Funcionario> findByNome(String nome);
}
