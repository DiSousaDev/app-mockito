package br.com.diego.appmockito.exemplos.services;

import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getAvaliacoesList;
import static br.com.diego.appmockito.exemplos.mother.AvaliacaoMother.getPerguntas;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import br.com.diego.appmockito.exemplos.repositories.AvaliacaoRepository;
import br.com.diego.appmockito.exemplos.repositories.PerguntaRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvaliacaoServiceImplTest {

  AvaliacaoService service;
  AvaliacaoRepository repository;
  PerguntaRepository perguntaRepository;

  @BeforeEach
  void setUp() {
     repository = mock(AvaliacaoRepository.class);
     perguntaRepository = mock(PerguntaRepository.class);
     service = new AvaliacaoServiceImpl(repository, perguntaRepository);
  }

  @Test
  void findAvaliacaoByName() {
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    Optional<Avaliacao> avaliacao = service.findAvaliacaoByName("Matemática");

    assertTrue(avaliacao.isPresent());
    assertEquals(5L, avaliacao.orElseThrow().getId());
    assertEquals("Matemática", avaliacao.get().getNome());
  }

  @Test
  void findAvaliacaoByNameEmptyList() {
    when(repository.findAll()).thenReturn(Collections.emptyList());
    Optional<Avaliacao> avaliacao = service.findAvaliacaoByName("Matemática");

    assertFalse(avaliacao.isPresent());
  }

  @Test
  void findAvaliacaoComPerguntas() {
    when(repository.findAll()).thenReturn(getAvaliacoesList());
    when(perguntaRepository.findPerguntasByAvaliacaoId(anyLong())).thenReturn(getPerguntas());

    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Programação");

    assertEquals(7, avaliacao.getPerguntas().size());
    assertTrue(avaliacao.getPerguntas().contains("Java"));

  }

}