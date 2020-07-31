package br.com.ads.testemongo.SpringMongoDB.service;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.model.builder.FuncionarioBuilder;
import br.com.ads.testemongo.SpringMongoDB.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Funcionario criar(final Funcionario funcionario) {

        Funcionario temp = null;

        if (funcionario.getChefe() != null) {
            temp = new FuncionarioBuilder(funcionario)
                                .setChefe(this.funcionarioRepository
                                                .findById(funcionario.getChefe().getCodigo())
                                                .orElseThrow(() -> new FuncionarioNotFoundException(
                                                                "Chefe não existente."
                                                            )
                                          ))
                                .criar();
        }

        return funcionarioRepository.save((temp != null)? temp : funcionario);
    }

    public List<Funcionario> obterFuncionariosPorRangeDeIdade(Integer de, Integer ate) {
        return funcionarioRepository.obterFuncionariosPorRangeDeIdade(de, ate);
    }

    public List<Funcionario> obterFuncionarioPorNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }

    public Funcionario atualizar(String codigo, Funcionario outro) throws FuncionarioNotFoundException {

        Funcionario funcionario = new FuncionarioBuilder(this.obterPorCodigo(codigo))
                                        .copiarDados(outro)
                                        .criar();
        //funcionario.set(outro);

        funcionarioRepository.save(funcionario);
        funcionario = this.obterPorCodigo(funcionario.getCodigo());

        return funcionario;
    }

    public Funcionario atualizarParcial(String codigo, Map<String, Object> dados) throws FuncionarioNotFoundException {

        Funcionario funcionario = new FuncionarioBuilder(this.obterPorCodigo(codigo))
                                        .adicionarDados(dados)
                                        .criar();
        funcionarioRepository.save(funcionario);
        funcionario = this.obterPorCodigo(funcionario.getCodigo());

        return funcionario;
    }

    public void deletar(String codigo) {
        final Funcionario funcionario = this.obterPorCodigo(codigo);

        funcionarioRepository.delete(funcionario);
    }
}
