package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.dao.BookDAO;
import Project1.springCourse_alishev.dao.PersonDAO;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO=bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", personDAO.showAllPeople());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", personDAO.showOnePerson(person_id));
        model.addAttribute("books", bookDAO.showOneBookOfPerson(person_id));
        return "people/onePerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        /*personValidator.validate(person,bindingResult);*/
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int person_id) {
        model.addAttribute("person", personDAO.showOnePerson(person_id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int person_id) {
        /*personValidator.validate(person,bindingResult);*/
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}
