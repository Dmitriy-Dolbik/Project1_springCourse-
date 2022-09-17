package Project1.springCourse_alishev.services;

import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;
    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    public Person getBookOwner(int id){
        Optional<Book> book = booksRepository.findById(id);
        return book.get().getOwner();
    }
    @Transactional
    public void release(int id){
        Book book = booksRepository.findById(id).get();
        book.setDateOfAssignment(null);
        book.setOwner(null);
    }
    @Transactional
    public void assign(int id, Person selectedPerson){
        Book book = booksRepository.findById(id).get();
        Person person = peopleService.findOne(selectedPerson.getId());
        book.setDateOfAssignment(Calendar.getInstance());
        book.setOwner(person);
    }
    public List<Book> findAllByPagination(Integer page, Integer booksPerPage){
        return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }
    public List<Book> findAllBySort(){
        return booksRepository.findAll(Sort.by("year"));
    }
    public List<Book> findAllBySortAndPagination(Integer page, Integer booksPerPage){
        return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("year"))).getContent();
    }
    public List<Book> findByTitleStartingWith(String startingWith){
        return booksRepository.findByTitleStartingWith(startingWith);
    }
    public boolean isAssigned(int id){
        Book book = booksRepository.findById(id).get();
        Calendar dayOfAssignment = book.getDateOfAssignment();
        Calendar tenDaysAfterAssignment = dayOfAssignment;
        tenDaysAfterAssignment.add(Calendar.DAY_OF_MONTH, 10);
        Calendar currentDay = Calendar.getInstance();
        return currentDay.after(tenDaysAfterAssignment);
    }

}
