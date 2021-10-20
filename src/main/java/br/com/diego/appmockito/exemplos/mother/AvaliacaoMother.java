package br.com.diego.appmockito.exemplos.mother;

import br.com.diego.appmockito.exemplos.models.Avaliacao;
import java.util.Arrays;
import java.util.List;

public class AvaliacaoMother {

  public static List<Avaliacao> getAvaliacoesList() {
    return  Arrays.asList(
            new Avaliacao(5L, "Matemática"),
            new Avaliacao(6L, "Português"),
            new Avaliacao(7L, "História"),
            new Avaliacao(8L, "Programação")
    );
  }

  public static List<Avaliacao> getAvaliacoesListIdNull() {
    return  Arrays.asList(
            new Avaliacao(null, "Matemática"),
            new Avaliacao(null, "Português"),
            new Avaliacao(null, "História"),
            new Avaliacao(null, "Programação")
    );
  }

  public static List<Avaliacao> getAvaliacoesListIdNegativo() {
    return  Arrays.asList(
            new Avaliacao(-5L, "Matemática"),
            new Avaliacao(-6L, "Português"),
            new Avaliacao(-7L, "História"),
            new Avaliacao(null, "Programação")
    );
  }

  public static List<String> getPerguntas() {
    return Arrays.asList("Java", "Phyton", "C#", "Cobol", "CSS", "HTML", "JavaScript");
  }

  public static Avaliacao getAvaliacao() {
    return new Avaliacao(null, "Scratch");
  }

}
