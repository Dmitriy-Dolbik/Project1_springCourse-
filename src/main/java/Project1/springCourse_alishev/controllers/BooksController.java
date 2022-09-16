package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.dao.BookDAO;
import Project1.springCourse_alishev.dao.PersonDAO;
import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.services.BooksService;
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
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, PeopleService peopleService, BooksService booksService){
        this.bookDAO=bookDAO;
        this.personDAO=personDAO;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }
    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/allBookPages";
    }
    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int id, Model model,
                              @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));
        Person bookOwner = booksService.getBookOwner(id);

        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
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
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PostMapping()
    public String creat(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book updatedBook,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, updatedBook);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/"+id;
    }
    //у selectedPerson назначено только поле id, остальные поля null
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                                 @ModelAttribute("personToAssign") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/"+id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
