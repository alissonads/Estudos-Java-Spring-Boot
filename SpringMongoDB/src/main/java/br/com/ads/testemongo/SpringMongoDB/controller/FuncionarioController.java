package br.com.ads.testemongo.SpringMongoDB.controller;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<Funcionario> obterTodos() {
        return funcionarioService.obterTodos();
    }

    @GetMapping("/{codigo}")
    public Funcionario obterPorCodigo(@PathVariable("codigo") String codigo) {
        return funcionarioService.obterPorCodigo(codigo);
    }

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return funcionarioService.criar(funcionario);
    }

    @GetMapping("/range")
    public List<Funcionario> obterFuncionariosPorRangeDeIdade(@RequestParam("de") Integer de,
                                                              @RequestParam("ate") Integer ate){
        return funcionarioService.obterFuncionariosPorRangeDeIdade(de, ate);
    }

    @GetMapping("/por-nome")
    public List<Funcionario> obterFuncionarioPorNome(@RequestParam("nome") String nome) {
        return funcionarioService.obterFuncionarioPorNome(nome);
    }

    @PutMapping("/{codigo}")
    public Funcionario atualizar(@PathVariable("codigo") String codigo,
                                 @RequestBody Funcionario fucionario) throws IllegalArgumentException {
        return funcionarioService.atualizar(codigo, fucionario);
    }

    @DeleteMapping("/{codigo}")
    public void deletar(@PathVariable("codigo") String codigo) {
        funcionarioService.deletar(codigo);
    }
}
