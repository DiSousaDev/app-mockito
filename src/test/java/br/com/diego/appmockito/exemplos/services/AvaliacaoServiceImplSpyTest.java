package br.com.diego.appmockito.exemplos.services;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import br.com.diego.appmockito.exemplos.repositories.AvaliacaoRepositoryImpl;
import br.com.diego.appmockito.exemplos.repositories.PerguntaRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceImplSpyTest {

  @InjectMocks
  AvaliacaoServiceImpl service;

  @Spy
  AvaliacaoRepositoryImpl repository;

  @Spy
  PerguntaRepositoryImpl perguntaRepository;

  @Test
  void testSpy(){

    Avaliacao avaliacao = service.findAvaliacaoPorNomeComPerguntas("Matemática");

    assertEquals(7, avaliacao.getPerguntas().size());
    assertEquals(5L, avaliacao.getId());
    assertTrue(avaliacao.getPerguntas().contains("Java"));
    assertEquals("Matemática", avaliacao.getNome());

    verify(repository).findAll();
    verify(perguntaRepository).findPerguntasByAvaliacaoId(anyLong());

  }
}