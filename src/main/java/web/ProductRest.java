package web;

import model.AddProductParameters;
import model.Product;
import model.UpdateProductParameters;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/products")
@Stateless
public class ProductRest {

    @PersistenceContext
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findAllProducts(
            @QueryParam("priceFrom") Double priceFrom,
            @QueryParam("priceTo") Double priceTo,
            @QueryParam("name") String name,
            @QueryParam("category") String category
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        CriteriaQuery<Product> query = criteriaQuery.select(productRoot);

        final List<Predicate> predicates = new ArrayList<>();

        if (priceFrom != null) {
            predicates.add(criteriaBuilder.greaterThan(productRoot.get("price"), priceFrom));
        }

        if (priceTo != null) {
            predicates.add(criteriaBuilder.lessThan(productRoot.get("price"), priceTo));
        }

        if (name != null) {
            predicates.add(criteriaBuilder.like(productRoot.get("name"), "%" + name + "%"));
        }

        if (category != null) {
            predicates.add(criteriaBuilder.like(productRoot.get("category"), category));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProductById(@PathParam("id") int id) {
        Product product = entityManager.createNamedQuery("product.id", Product.class)
                .setParameter("id", id)
                .getSingleResult();

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(product).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(AddProductParameters request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        entityManager.persist(product);
        return Response.ok(product.getId()).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, UpdateProductParameters request) {
        Product product = entityManager.createNamedQuery("product.id", Product.class)
                .setParameter("id", id)
                .getSingleResult();

        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        entityManager.persist(product);

        return Response.ok().build();
    }
}