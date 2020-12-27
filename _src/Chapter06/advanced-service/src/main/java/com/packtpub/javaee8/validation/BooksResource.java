package com.packtpub.javaee8.validation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@Path("/validation/books")
@Produces(MediaType.APPLICATION_JSON)
public class BooksResource {

    private Map<String, Book> books = new HashMap<>();

    @PostConstruct
    public void initialize() {
        books.put("0345391802", new Book("0345391802", "The Hitchhiker's Guide to the Galaxy"));
    }

    @GET
    public Response books() {
        return Response.ok(books.values()).build();
    }

    @GET
    @Path("/{isbn}")
    public Response book(@PathParam("isbn") @Pattern(regexp = "[0-9]{10}") String isbn) {
        Book book = Optional.ofNullable(books.get(isbn)).orElseThrow(NotFoundException::new);
        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Book book, @Context UriInfo uriInfo) {
        books.put(book.isbn, book);

        URI uri = uriInfo.getBaseUriBuilder().path(BooksResource.class).path(book.isbn).build();
        return Response.created(uri).build();
    }

    public static class Book {

        @Pattern(regexp = "[0-9]{10}")
        private String isbn;

        @NotBlank
        private String title;

        public Book() {
        }

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
