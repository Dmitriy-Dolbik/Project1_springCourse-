package Project1.springCourse_alishev.services;

import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.repositories.BooksRepository;
import Project1.springCourse_alishev.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAllBooks() {
        return booksRepository.findAll();
    }

    public Book findOneBook(int book_id) {
        Optional<Book> soundBook = booksRepository.findById(book_id);
        return soundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int book_id, Book updatedBook) {
        updatedBook.setBook_id(book_id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int book_id) {
        booksRepository.deleteById(book_id);
    }
    @Transactional
    public void assingToPerson(int book_id, int person_id){
        Book book = booksRepository.findById(book_id).orElse(null);
        if (person_id != 0){
            Person person = peopleRepository.findById(person_id).orElse(null);
            if (book!=null && person!=null){
                book.setOwner(person);
            }
        } else {
            if (book!=null){
                book.setOwner(null);
            }
        }
    }
    public Person findOwner(int book_id){
        Book book = booksRepository.findById(book_id).orElse(null);
        return book.getOwner();
    }
}
