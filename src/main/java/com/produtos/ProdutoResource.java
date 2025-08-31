package com.produtos;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

  private final ProdutoRepository pRepository;

  private ProdutoResource(ProdutoRepository pRepository) {
      this.pRepository = pRepository;
  }

  @GET
  public List<Produto> listar() {
    return pRepository.list();
  }

  @GET @Path("{id}")
  public Response buscar(@PathParam("id") Long id) {
    Produto p = pRepository.get(id);
    return (p == null) ? Response.status(Response.Status.NOT_FOUND).build()
                       : Response.ok(p).build();
  }

  @POST
  public Response criar(Produto p, @Context UriInfo uriInfo) {
    if (p == null || p.getNome() == null || p.getNome().isBlank())
      return Response.status(Response.Status.BAD_REQUEST).entity("nome é obrigatório").build();

    Produto novo = pRepository.save(p);
    URI uri = uriInfo.getAbsolutePathBuilder().path(novo.getId().toString()).build();
    return Response.created(uri).entity(novo).build();
  }

  @PUT @Path("{id}")
  public Response atualizar(@PathParam("id") Long id, Produto p) {
    Produto upd = pRepository.update(id, p);
    return (upd == null) ? Response.status(Response.Status.NOT_FOUND).build()
                         : Response.ok(upd).build();
  }

  @DELETE @Path("{id}")
  public Response remover(@PathParam("id") Long id) {
    return pRepository.delete(id) ? Response.noContent().build()
                                        : Response.status(Response.Status.NOT_FOUND).build();
  }
}
