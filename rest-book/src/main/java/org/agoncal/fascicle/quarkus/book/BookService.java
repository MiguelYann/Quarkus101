package org.agoncal.fascicle.quarkus.book;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class BookService {
  @Inject
  Logger LOGGER;

  @Inject
  EntityManager em;

  public Book persistBook(@Valid Book book) {
    Book.persist(book);
    return book;
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
