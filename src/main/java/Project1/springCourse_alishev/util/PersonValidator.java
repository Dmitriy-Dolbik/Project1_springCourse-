package Project1.springCourse_alishev.util;

import Project1.springCourse_alishev.dao.PersonDAO;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PersonDAO personDAO, PeopleService peopleService) {
        this.personDAO = personDAO;
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Человек с таким именем уже существует");
        }
    }
}