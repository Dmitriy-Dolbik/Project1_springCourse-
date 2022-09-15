package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.services.BookService;
import Project1.springCourse_alishev.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService){
        this.bookService = bookService;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books/allBookPages";
    }
    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int book_id, Model model,
                              @ModelAttribute("emptyPerson") Person person){
        model.addAttribute("book", bookService.findOneBook(book_id));
        model.addAttribute("personByBook", bookService.findOwner(book_id));
        model.addAttribute("people", peopleService.findAllPeople());
        return "books/oneBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int book_id){
        model.addAttribute("book", bookService.findOneBook(book_id));
        return "books/edit";
    }
    @PostMapping()
    public String creat(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult){
        bookService.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int book_id){
        bookService.update(book_id, book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/add")
    public String assignToPerson(@PathVariable("id") int book_id,
                                 @ModelAttribute("personToAssign") Person person) {
        bookService.assingToPerson(book_id, person.getPerson_id());
        return "redirect:/books/"+book_id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int book_id){
        bookService.delete(book_id);
        return "redirect:/books";
    }
}
