package br.com.diego.appmockito.exemplos.repositories;

import br.com.diego.appmockito.exemplos.mother.AvaliacaoMother;

import java.util.List;

public class PerguntaRepositoryImpl implements PerguntaRepository {

    @Override
    public List<String> findPerguntasByAvaliacaoId(Long id){
        System.out.println("PerguntaRepositoryImpl.findPerguntasByAvaliacaoId");
        return AvaliacaoMother.getPerguntas();
    }

    @Override
    public void salvarPerguntas(List<String> perguntas){
        System.out.println("PerguntaRepositoryImpl.salvarPerguntas");
    }
}
