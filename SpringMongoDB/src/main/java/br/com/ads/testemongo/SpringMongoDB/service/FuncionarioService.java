package br.com.ads.testemongo.SpringMongoDB.service;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.repository.FuncionarioRepository;
import org.springframework.http.ResponseEntity;
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
                            () -> new FuncionarioNotFoundException("Funcionario não existe.")
                    );
    }

    public Funcionario criar(Funcionario funcionario) {
        if (funcionario.getChefe() != null) {
            Funcionario chefe = this.funcionarioRepository
                                        .findById(funcionario.getChefe().getCodigo())
                                        .orElseThrow(() -> new FuncionarioNotFoundException(
                                            "Chefe não existente."
                                         ));
            funcionario.setChefe(chefe);
        }

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> obterFuncionariosPorRangeDeIdade(Integer de, Integer ate) {
        return funcionarioRepository.obterFuncionariosPorRangeDeIdade(de, ate);
    }

    public List<Funcionario> obterFuncionarioPorNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }

    public Funcionario atualizar(String codigo, Funcionario outro) throws FuncionarioNotFoundException {
        Funcionario funcionario = this.obterPorCodigo(codigo);
        funcionario.set(outro);

        funcionarioRepository.save(funcionario);
        funcionario = this.obterPorCodigo(funcionario.getCodigo());
        return funcionario;
    }

    public void deletar(String codigo) {
        final Funcionario funcionario = this.obterPorCodigo(codigo);

        funcionarioRepository.delete(funcionario);
    }
}
