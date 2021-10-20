package br.com.diego.appmockito.exemplos.repositories;

import java.util.List;

public interface PerguntaRepository {

  List<String> findPerguntasByAvaliacaoId(Long id);

  void salvarPerguntas(List<String> perguntas);
}
