package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.dao.BookDAO;
import Project1.springCourse_alishev.dao.PersonDAO;
import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO){
        this.bookDAO=bookDAO;
        this.personDAO=personDAO;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.showAllBooks());
        return "books/allBookPages";
    }
    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int book_id, Model model,
                              @ModelAttribute("emptyPerson") Person person){
        model.addAttribute("book", bookDAO.showOneBook(book_id));
        model.addAttribute("personByBook", personDAO.showOnePersonByBook_id(book_id));
        model.addAttribute("people", personDAO.showAllPeople());
        return "books/oneBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int book_id){
        model.addAttribute("book", bookDAO.show(book_id));
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
                         @PathVariable("id") int book_id){
        bookDAO.update(book_id, book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/add")
    public String assignToPerson(@PathVariable("id") int book_id,
                                 @ModelAttribute("personToAssign") Person person) {
        bookDAO.assingToPerson(book_id, person.getPerson_id());
        return "redirect:/books/"+book_id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int book_id){
        bookDAO.delete(book_id);
        return "redirect:/books";
    }
}
