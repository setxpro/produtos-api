package com.produtos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository {
  @PersistenceContext(unitName = "produtosPU")
  EntityManager em;

  public List<Produto> list() {
    return em.createQuery("select p from Produto p order by p.id", Produto.class).getResultList();
  }

  public Produto get(Long id) {
    return em.find(Produto.class, id);
  }

  @Transactional
  public Produto save(Produto p) {
    em.persist(p);
    return p;
  }

  @Transactional
  public Produto update(Long id, Produto p) {
    Produto cur = em.find(Produto.class, id);
    if (cur == null) return null;
    cur.setNome(p.getNome());
    cur.setPreco(p.getPreco());
    cur.setEstoque(p.getEstoque());
    return cur;
  }

  @Transactional
  public boolean delete(Long id) {
    Produto cur = em.find(Produto.class, id);
    if (cur == null) return false;
    em.remove(cur);
    return true;
  }
}