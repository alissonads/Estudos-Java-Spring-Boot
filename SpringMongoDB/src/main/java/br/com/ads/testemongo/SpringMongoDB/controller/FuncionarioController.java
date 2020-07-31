package br.com.ads.testemongo.SpringMongoDB.controller;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Funcionario> obterPorCodigo(@PathVariable("codigo") String codigo) {

        final Funcionario funcionario = funcionarioService.obterPorCodigo(codigo);

        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public ResponseEntity<Funcionario> atualizar(@PathVariable("codigo") String codigo,
                                                 @RequestBody Funcionario outro) throws FuncionarioNotFoundException {

        final Funcionario funcionario = funcionarioService.atualizar(codigo, outro);

        return ResponseEntity.ok(funcionario);
    }

    @PatchMapping("/{codigo}")
    public ResponseEntity<Funcionario> atualizarParcial(@PathVariable("codigo") String codigo,
                                                        @RequestBody Map<String, Object> outro) throws FuncionarioNotFoundException {

        final Funcionario funcionario = funcionarioService.atualizarParcial(codigo, outro);

        return ResponseEntity.ok(funcionario);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("codigo") String codigo) {
        funcionarioService.deletar(codigo);
    }
}
