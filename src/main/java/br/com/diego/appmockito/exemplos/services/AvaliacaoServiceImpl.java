package br.com.diego.appmockito.exemplos.services;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import br.com.diego.appmockito.exemplos.repositories.AvaliacaoRepository;
import br.com.diego.appmockito.exemplos.repositories.PerguntaRepository;
import java.util.List;
import java.util.Optional;

public class AvaliacaoServiceImpl implements AvaliacaoService {

  private AvaliacaoRepository avaliacaoRepository;
  private PerguntaRepository perguntaRepository;

  public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository, PerguntaRepository perguntaRepository) {
    this.avaliacaoRepository = avaliacaoRepository;
    this.perguntaRepository = perguntaRepository;
  }

  @Override
  public Optional<Avaliacao> findAvaliacaoByName(String nome) {
    return avaliacaoRepository.findAll()
        .stream()
        .filter(avaliacao -> avaliacao.getNome().equals(nome))
        .findFirst();
  }

  @Override
  public Avaliacao findAvaliacaoPorNomeComPerguntas(String nome) {
    Optional<Avaliacao> avaliacaoOptional = findAvaliacaoByName(nome);

    Avaliacao avaliacao = null;

    if(avaliacaoOptional.isPresent()) {
      avaliacao = avaliacaoOptional.get();
      List<String> perguntas = perguntaRepository.findPerguntasByAvaliacaoId(avaliacao.getId());
      avaliacao.setPerguntas(perguntas);
    }
    return avaliacao;
  }

  @Override
  public Avaliacao salvar(Avaliacao avaliacao){
    if(!avaliacao.getPerguntas().isEmpty()) {
      perguntaRepository.salvarPerguntas(avaliacao.getPerguntas());
    }
    return avaliacaoRepository.salvar(avaliacao);
  }

}
