package br.com.ads.testemongo.SpringMongoDB.controller;

import br.com.ads.testemongo.SpringMongoDB.model.Funcionario;
import br.com.ads.testemongo.SpringMongoDB.model.builder.FuncionarioBuilder;
import br.com.ads.testemongo.SpringMongoDB.service.FuncionarioService;
import br.com.ads.testemongo.SpringMongoDB.utils.JsonConvertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static br.com.ads.testemongo.SpringMongoDB.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(funcionarioController)
                                 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                 .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                                 .build();
    }

    @Test
    public void retornarSucesso_QuandoCriarFuncionario() throws Exception {
        Funcionario funcionario = new FuncionarioBuilder()
                                        .setNome("Fulano")
                                        .setIdade(30)
                                        .setSalario(3000.0D)
                                        .criar();

        when(funcionarioService.criar(funcionario)).thenReturn(funcionario);

        mockMvc.perform(post("/funcionarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(funcionario)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("nome", is(funcionario.getNome())))
               .andExpect(jsonPath("idade", is(funcionario.getIdade())))
               .andExpect(jsonPath("salario", is(funcionario.getSalario())));
    }
}
