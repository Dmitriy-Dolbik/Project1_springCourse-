package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.dao.BookDAO;
import Project1.springCourse_alishev.dao.PersonDAO;
import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, PeopleService peopleService){
        this.bookDAO=bookDAO;
        this.personDAO=personDAO;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.showAll());
        return "books/allBookPages";
    }
    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int id, Model model,
                              @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.showOne(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        System.out.println(bookDAO.getBookOwner(id));

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/oneBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.showOne(id));
        return "books/edit";
    }
    @PostMapping()
    public String creat(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult){
        bookDAO.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                                 @ModelAttribute("personToAssign") Person selectedPerson) {
        bookDAO.assing(id, selectedPerson);
        return "redirect:/books/"+id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
