package br.com.diego.appmockito.exemplos.models;

import java.util.ArrayList;
import java.util.List;

public class Avaliacao {

  private Long id;
  private String nome;
  private List<String> perguntas;

  public Avaliacao(Long id, String nome) {
    this.id = id;
    this.nome = nome;
    perguntas = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<String> getPerguntas() {
    return perguntas;
  }

  public void setPerguntas(List<String> perguntas) {
    this.perguntas = perguntas;
  }

}
