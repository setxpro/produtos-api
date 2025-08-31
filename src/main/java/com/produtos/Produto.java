package com.produtos;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(precision = 15, scale = 2, nullable = false)
  private BigDecimal preco;

  @Column(nullable = false)
  private Integer estoque;

  public Produto() {}
  public Produto(Long id, String nome, BigDecimal preco, Integer estoque) {
    this.id = id; this.nome = nome; this.preco = preco; this.estoque = estoque;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }
  public BigDecimal getPreco() { return preco; }
  public void setPreco(BigDecimal preco) { this.preco = preco; }
  public Integer getEstoque() { return estoque; }
  public void setEstoque(Integer estoque) { this.estoque = estoque; }
}
