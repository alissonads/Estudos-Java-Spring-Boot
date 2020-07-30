package br.com.ads.testemongo.SpringMongoDB.repository;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {

    @Query("{ $and : [{'idade': {$gte: ?0}}, {'idade': {$lte: ?1}}] }")
    public List<Funcionario> obterFuncionariosPorRangeDeIdade(Integer de, Integer ate);

    public List<Funcionario> findByNome(String nome);

}
