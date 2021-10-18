package br.com.diego.appmockito.exemplos.services;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import java.util.Optional;

public interface AvaliacaoService {

  Optional<Avaliacao> findAvaliacaoByName(String nome);

  Avaliacao findAvaliacaoPorNomeComPerguntas(String nome);

}
