package Project1.springCourse_alishev.controllers;

import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.services.PeopleService;
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
    private final PersonValidator personValidator;
    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", peopleService.findAllPeople());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", peopleService.findOnePerson(person_id));
        model.addAttribute("books", peopleService.getBooksByPersonId(person_id));
        return "people/onePerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int person_id) {
        model.addAttribute("person", peopleService.findOnePerson(person_id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int person_id) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int person_id) {
        peopleService.delete(person_id);
        return "redirect:/people";
    }
}
