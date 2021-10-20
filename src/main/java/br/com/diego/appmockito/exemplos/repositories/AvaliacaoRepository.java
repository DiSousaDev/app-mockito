package br.com.diego.appmockito.exemplos.repositories;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import java.util.List;

public interface AvaliacaoRepository {

  List<Avaliacao> findAll();

  Avaliacao salvar(Avaliacao avaliacao);

}
