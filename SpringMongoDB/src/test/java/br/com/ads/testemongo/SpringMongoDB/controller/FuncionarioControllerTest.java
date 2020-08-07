package br.com.ads.testemongo.SpringMongoDB.controller;

import br.com.ads.testemongo.SpringMongoDB.exception.FuncionarioNotFoundException;
import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.model.builder.FuncionarioBuilder;
import br.com.ads.testemongo.SpringMongoDB.service.FuncionarioService;
import br.com.ads.testemongo.SpringMongoDB.utils.JsonConvertionUtils;
import org.apiguardian.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static br.com.ads.testemongo.SpringMongoDB.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
public class FuncionarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FuncionarioService funcionarioService;
    @InjectMocks
    private FuncionarioController funcionarioController;

    private static final String API_URL = "/funcionarios";

    @BeforeEach
    public void config() {
        mockMvc = MockMvcBuilders.standaloneSetup(funcionarioController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    public void retornarSucesso_QuandoCriarFuncionario() throws Exception {
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .setNome("Fulano")
                                        .setIdade(30)
                                        .setSalario(3000.0D)
                                        .criar();

        when(funcionarioService.criar(funcionario)).thenReturn(funcionario);

        mockMvc.perform(post(API_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(funcionario)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$.idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$.salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarBadRequest_QuandoNaoConseguirCriarFuncionario() throws Exception {
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .setNome("Fulano")
                                        .setIdade(30)
                                        .setSalario(3000.0D)
                                        .criar();

        mockMvc.perform(post(API_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(funcionario)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void retornarSucesso_QuandoBuscarTodosOsFuncionariosComparandoComUmExistente() throws Exception{
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .setCodigo("5f231cc64b384868eea4a740")
                                        .setNome("Kelly")
                                        .setIdade(39)
                                        .setSalario(3000.0D)
                                        .criar();


        when(funcionarioService.obterTodos())
                .thenReturn(Collections.singletonList(funcionario));

        mockMvc.perform(get(API_URL)
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$[0].idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$[0].salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarSucesso_QuandoBuscarTodosOsFuncionarios() throws Exception{
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .setNome("Fulano")
                                        .setIdade(30)
                                        .setSalario(3000.0D)
                                        .criar();

        when(funcionarioService.obterTodos())
                .thenReturn(Collections.singletonList(funcionario));

        mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retornarSucesso_QuandoBuscarFuncionarioPorCodigo() throws Exception {
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .setCodigo("5f231cc64b384868eea4a740")
                                        .setNome("Kelly")
                                        .setIdade(39)
                                        .setSalario(3000.0D)
                                        .criar();

        when(funcionarioService.obterPorCodigo(funcionario.getCodigo()))
                .thenReturn(funcionario);

        mockMvc.perform(get(API_URL + "/" + funcionario.getCodigo())
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$.idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$.salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarNotFound_QuandoNaoEncontradoBuscaFuncionarioPorCodigo() throws Exception {
        when(funcionarioService.obterPorCodigo("1"))
                .thenThrow(FuncionarioNotFoundException.class);

        mockMvc.perform(get(API_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void retornarSucesso_QuandoBuscarFuncionarioPorNome() throws Exception {
        Funcionario funcionario = FuncionarioBuilder.instancia()
                .setCodigo("5f231cc64b384868eea4a740")
                .setNome("Kelly")
                .setIdade(39)
                .setSalario(3000.0D)
                .criar();

        when(funcionarioService.obterFuncionarioPorNome(funcionario.getNome()))
                .thenReturn(Collections.singletonList(funcionario));

        mockMvc.perform(get(API_URL + "/por-nome?nome=" + funcionario.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(funcionario.getNome())))
                .andExpect(jsonPath("$[0].idade", is(funcionario.getIdade())))
                .andExpect(jsonPath("$[0].salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarSucesso_QuandoExecutarPesquisaPaginadaComparando() throws Exception {
        Funcionario funcionario = FuncionarioBuilder.instancia()
                .setCodigo("5f231cc64b384868eea4a740")
                .setNome("Kelly")
                .setIdade(39)
                .setSalario(3000.0D)
                .criar();

        when(funcionarioService.obterPesquisaPaginada(0, 2))
                .thenReturn(Collections.singletonList(funcionario));

        mockMvc.perform(get(API_URL + "/0/2")
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$[0].idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$[0].salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarSucesso_QuandoExecutarPesquisaPaginada() throws Exception {
        when(funcionarioService.obterPesquisaPaginada(0, 2))
                .thenReturn(Collections.singletonList(FuncionarioBuilder.instancia().criar()));

        mockMvc.perform(get(API_URL + "/0/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void retornarSucesso_QuandoAtualizarFuncionario() throws Exception {

        String codigo = "5f231cc64b384868eea4a740";
        Funcionario funcionario = FuncionarioBuilder.instancia()
                                    .setIdade(40)
                                    .setSalario(0.0)
                                    .criar();

        when(funcionarioService.atualizar(codigo, funcionario))
                .thenReturn(funcionario);

        mockMvc.perform(put(API_URL + "/" + codigo)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(funcionario)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$.idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$.salario", is(funcionario.getSalario())));

    }

    @Test
    public void retornarNotFound_QuandoTentarAtualizarFuncionario() throws Exception {

        String codigo = "1";
        Funcionario funcionario = FuncionarioBuilder.instancia()
                .setIdade(40)
                .setSalario(0.0)
                .criar();

        when(funcionarioService.atualizar(codigo, funcionario))
                .thenThrow(FuncionarioNotFoundException.class);

        mockMvc.perform(put(API_URL + "/" + codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(funcionario)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void retornarSucesso_QuandoAtualizarParcialTotal() throws Exception {
        String codigo = "5f231cc64b384868eea4a740";
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", "Fulano");
        dados.put("idade", 40);
        dados.put("salario", 0.0);

        Funcionario funcionario = FuncionarioBuilder.instancia()
                                        .adicionarDados(dados).criar();

        when(funcionarioService.atualizarParcial(codigo, dados))
                .thenReturn(funcionario);

        mockMvc.perform(patch(API_URL + "/" + codigo)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dados)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome", is(funcionario.getNome())))
               .andExpect(jsonPath("$.idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("$.salario", is(funcionario.getSalario())));
    }

    @Test
    public void retornarNotFound_QuandoTentarAtualizarParcialTotal() throws Exception {
        String codigo = "5f231cc64b384868eea4a740";
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", "Fulano");
        dados.put("idade", 40);
        dados.put("salario", 0.0);

        when(funcionarioService.atualizarParcial(codigo, dados))
                .thenThrow(FuncionarioNotFoundException.class);

        mockMvc.perform(patch(API_URL + "/" + codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dados)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void retornarSucesso_QuandoDeletarUmFuncionario() throws Exception {
        String codigo = "5f231cc64b384868eea4a740";

        doNothing().when(funcionarioService).deletar(codigo);

        mockMvc.perform(delete(API_URL + "/" + codigo)
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    public void retornarNotFound_QuandoDeletarUmFuncionario() throws Exception {
        String codigo = "5f231cc64b384868eea4a740";

        doThrow(FuncionarioNotFoundException.class).when(funcionarioService).deletar(codigo);

        mockMvc.perform(delete(API_URL + "/" + codigo)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
