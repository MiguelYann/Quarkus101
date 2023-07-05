package org.agoncal.fascicle.quarkus.book;

import org.agoncal.fascicle.quarkus.book.client.IsbnNumbers;
import org.agoncal.fascicle.quarkus.book.client.NumberProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class BookService {
  @Inject
  Logger LOGGER;

  @Inject
  EntityManager em;

  @Inject
  @RestClient
  NumberProxy numberProxy;

  @Fallback(fallbackMethod = "fallbackPersistBook")
  public Book persistBook(@Valid Book book) {
    // The Book microservice invokes the Number microservice
    IsbnNumbers isbnNumbers = numberProxy.generateNumbers();
    book.isbn13 = isbnNumbers.isbn13;
    book.isbn10 = isbnNumbers.isbn10;
    Book.persist(book);
    return book;
  }

  private Book fallbackPersistBook(Book book) throws FileNotFoundException {
    LOGGER.warn("Falling back on persisting a book");
    book.id = 0L;
    book.isbn13 = "to be fixed";
    book.isbn10 = "to be fixed";
    String bookJson = JsonbBuilder.create().toJson(book);
    try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() +
      ".json")) {
      out.println(bookJson);
    }
    throw new IllegalStateException();
  }

  @Transactional(Transactional.TxType.SUPPORTS)
  public List<Book> findAllBooks() {
    return Book.listAll();
  }

  @Transactional(Transactional.TxType.SUPPORTS)
  public Optional<Book> findBookById(Long id) {
    return Book.findByIdOptional(id);
  }

  @Transactional(Transactional.TxType.SUPPORTS)
  public Book findRandomBook() {
    Book randomBook = null;
    while (randomBook == null) {
      randomBook = Book.findRandom();
    }
    return randomBook;
  }

  public Book updateBook(@Valid Book book) {
    Book entity = em.merge(book);
    return entity;
  }

  public void deleteBook(Long id) {
    Book.deleteById(id);
  }
}
