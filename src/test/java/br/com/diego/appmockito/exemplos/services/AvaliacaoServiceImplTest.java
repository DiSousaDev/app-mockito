package br.com.diego.appmockito.exemplos.services;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import br.com.diego.appmockito.exemplos.repositories.AvaliacaoRepository;
import br.com.diego.appmockito.exemplos.repositories.AvaliacaoRepositoryImpl;
import br.com.diego.appmockito.exemplos.repositories.PerguntaRepository;
import br.com.diego.appmockito.exemplos.repositories.PerguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Optional;

import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getAvaliacao;
import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getAvaliacoesList;
import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getAvaliacoesListIdNegativo;
import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getAvaliacoesListIdNull;
import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getPerguntas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceImplTest {

  @InjectMocks
  AvaliacaoServiceImpl service;

  @Mock
  AvaliacaoRepositoryImpl repository;

  @Mock
  PerguntaRepositoryImpl perguntaRepository;

  @Captor
  ArgumentCaptor<Long> captor;

  @BeforeEach
  void setUp() {
//    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAvaliacaoByName() {
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());

    // WHEN
    Optional<Avaliacao> avaliacao = service.findAvaliacaoByName("Matemática");

    // THEN
    assertTrue(avaliacao.isPresent());
    assertEquals(5L, avaliacao.orElseThrow().getId());
    assertEquals("Matemática", avaliacao.get().getNome());
  }

  @Test
  void findAvaliacaoByNameEmptyList() {
    // GIVEN
    when(repository.findAll()).thenReturn(Collections.emptyList());

    // WHEN
    Optional<Avaliacao> avaliacao = service.findAvaliacaoByName("Matemática");

    // THEN
    assertFalse(avaliacao.isPresent());
  }

  @Test
  void findAvaliacaoComPerguntas() {
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());

    // WHEN
    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Programação");

    // THEN
    assertEquals(7, avaliacao.getPerguntas().size());
    assertTrue(avaliacao.getPerguntas().contains("Java"));
  }

  @Test
  void findAvaliacaoComPerguntasVerify() {
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());

    // WHEN
    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Programação");

    // THEN
    assertEquals(7, avaliacao.getPerguntas().size());
    assertTrue(avaliacao.getPerguntas().contains("Java"));
    verify(repository).findAll();
    verify(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());
  }

  @Test
  void findAvaliacaoComPerguntasVerifyEmptyList() {
    // GIVEN
    when(repository.findAll()).thenReturn(Collections.emptyList());
//    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());

    // WHEN
    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Programação");

    // THEN
    assertNull(avaliacao);
    verify(repository).findAll();
    //verify(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());
  }

  @Test
  void salvarAvaliacao(){
    // GIVEN
    Avaliacao novaAvaliacao = getAvaliacao();
    novaAvaliacao.setPerguntas(getPerguntas());

    when(repository.salvar(any(Avaliacao.class))).then(new Answer<Avaliacao>() {

      Long sequenciaId = 9L;

      @Override
      public Avaliacao answer(InvocationOnMock invocationOnMock) throws Throwable{
        Avaliacao avaliacao = invocationOnMock.getArgument(0);
        avaliacao.setId(sequenciaId++);
        return avaliacao;
      }
    });

    // WHEN
    Avaliacao avaliacao = service.salvar(novaAvaliacao);

    // THEN
    assertNotNull(avaliacao);
    assertEquals(9L, avaliacao.getId());
    assertEquals("Scratch", avaliacao.getNome());
    verify(repository).salvar(any(Avaliacao.class));
    verify(perguntaRepository).salvarPerguntas(anyList());
  }

  @Test
  void testException(){
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesListIdNull());
    when(perguntaRepository.findPerguntasByAvaliacaoId(isNull())).thenThrow(IllegalArgumentException.class);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      service.findAvaliacaoPorNomeComPerguntas("Matemática");
    });
    assertEquals(IllegalArgumentException.class, exception.getClass());

    verify(repository).findAll();
    verify(perguntaRepository).findPerguntasByAvaliacaoId(isNull());
  }

  @Test
  void testArgumentMatchers(){
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());
    service.findAvaliacaoPorNomeComPerguntas("Matemática");

    verify(repository).findAll();
//  verify(perguntaRepository).findPerguntasByAvaliacaoId(argThat(arg -> arg != null && arg.equals(5L)));
    verify(perguntaRepository).findPerguntasByAvaliacaoId(argThat(arg -> arg != null && arg >= 5L));
//  verify(perguntaRepository).findPerguntasByAvaliacaoId(eq(5L));
  }

  @Test
  void testArgumentMatchers2(){
    when(repository.findAll()).thenReturn(getAvaliacoesListIdNegativo());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());
    service.findAvaliacaoPorNomeComPerguntas("Matemática");

    verify(repository).findAll();
    verify(perguntaRepository).findPerguntasByAvaliacaoId(argThat(new MyArgsMatchers()));
  }


  public static class MyArgsMatchers implements ArgumentMatcher<Long> {

    private Long argument;

    @Override
    public boolean matches(Long argument){
      this.argument = argument;
      return argument != null && argument > 0;
    }

    @Override
    public String toString(){
      return "\nExibindo mensagem personalizada de erro "
           + "imprime ao falhar o teste mockito "
           + "insira um numero inteiro positivo\n"
           + "Valor inserido: >>>>>>[ " + argument + " ]<<<<<<";
    }
  }

  @Test
  void testArgumentCaptor(){
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());
    // WHEN
    service.findAvaliacaoPorNomeComPerguntas("Matemática");

    // ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
    verify(perguntaRepository).findPerguntasByAvaliacaoId(captor.capture());
    assertEquals(5L, captor.getValue());
  }

  @Test
  void testDoThrow(){
    Avaliacao avaliacao = getAvaliacao();
    avaliacao.setPerguntas(getPerguntas());
    doThrow(IllegalArgumentException.class).when(perguntaRepository).salvarPerguntas(anyList());
    assertThrows(IllegalArgumentException.class, () -> {
      service.salvar(avaliacao);
    });
  }

  @Test
  void testDoAnswer(){
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());
   // when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());

    doAnswer(invocation -> {
      Long id = invocation.getArgument(0);
      return id == 5L ? getPerguntas() : Collections.emptyList();
    }).when(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());

    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Matemática");
    assertEquals(7, avaliacao.getPerguntas().size());
    assertEquals(5L, avaliacao.getId());
    assertTrue(avaliacao.getPerguntas().contains("Java"));
    assertEquals("Matemática", avaliacao.getNome());

    verify(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());
  }

  @Test
  void testDoAnswerSalvarAvaliacao(){
    // GIVEN
    Avaliacao novaAvaliacao = getAvaliacao();
    novaAvaliacao.setPerguntas(getPerguntas());

  doAnswer(new Answer<Avaliacao>() {

    Long sequenciaId = 9L;

    @Override
    public Avaliacao answer(InvocationOnMock invocationOnMock) throws Throwable{
      Avaliacao avaliacao = invocationOnMock.getArgument(0);
      avaliacao.setId(sequenciaId++);
      return avaliacao;
    }
  }).when(repository).salvar(any(Avaliacao.class));

    // WHEN
    Avaliacao avaliacao = service.salvar(novaAvaliacao);

    // THEN
    assertNotNull(avaliacao);
    assertEquals(9L, avaliacao.getId());
    assertEquals("Scratch", avaliacao.getNome());
    verify(repository).salvar(any(Avaliacao.class));
    verify(perguntaRepository).salvarPerguntas(anyList());
  }

  @Test
  void testDoCallRealMethod(){
    // GIVEN
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    //when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());
    doCallRealMethod().when(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());

    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Matemática");
    assertEquals(5L, avaliacao.getId());
    assertEquals("Matemática", avaliacao.getNome());
  }

  @Test
  void testSpy(){
    AvaliacaoRepository avaliacaoRepository = spy(AvaliacaoRepositoryImpl.class);
    PerguntaRepository perguntaRepository = spy(PerguntaRepositoryImpl.class);
    AvaliacaoService avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository, perguntaRepository);

    Avaliacao avaliacao = avaliacaoService.findAvaliacaoPorNomeComPerguntas("Matemática");

    assertEquals(7, avaliacao.getPerguntas().size());
    assertEquals(5L, avaliacao.getId());
    assertTrue(avaliacao.getPerguntas().contains("Java"));
    assertEquals("Matemática", avaliacao.getNome());

    verify(avaliacaoRepository).findAll();
    verify(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());

  }
}