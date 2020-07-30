package br.com.ads.testemongo.SpringMongoDB.service;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> obterTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario obterPorCodigo(String codigo) {
        return funcionarioRepository
                    .findById(codigo)
                    .orElseThrow(
                            () -> new IllegalArgumentException("Funcionario não existe.")
                    );
    }

    public Funcionario criar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }
}
