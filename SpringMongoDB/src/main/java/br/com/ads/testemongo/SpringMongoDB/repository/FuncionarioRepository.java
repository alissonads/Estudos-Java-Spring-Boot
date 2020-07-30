package br.com.ads.testemongo.SpringMongoDB.repository;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {
}
