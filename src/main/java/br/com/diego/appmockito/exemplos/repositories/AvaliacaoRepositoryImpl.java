package br.com.diego.appmockito.exemplos.repositories;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import br.com.diego.appmockito.exemplos.mother.AvaliacaoMother;

import java.util.List;

public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    @Override
    public List<Avaliacao> findAll(){
        System.out.println("AvaliacaoRepositoryImpl.findAll");
        return AvaliacaoMother.getAvaliacoesList();
    }

    @Override
    public Avaliacao salvar(Avaliacao avaliacao){
        System.out.println("AvaliacaoRepositoryImpl.salvar");
        return AvaliacaoMother.getAvaliacao();
    }
}
