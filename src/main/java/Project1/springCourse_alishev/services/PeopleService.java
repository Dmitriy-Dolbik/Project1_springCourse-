package Project1.springCourse_alishev.services;

import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import Project1.springCourse_alishev.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAllPeople() {
        return peopleRepository.findAll();
    }

    public Person findOnePerson(int person_id) {
        Optional<Person> foundPerson = peopleRepository.findById(person_id);
        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int person_id, Person updatedPerson) {
        updatedPerson.setPerson_id(person_id);
        peopleRepository.save(updatedPerson);

    }
    @Transactional
    public void delete(int person_id) {
        peopleRepository.deleteById(person_id);
    }
    public List<Book> getBooksByPersonId(int person_id) {
        Optional<Person> person = peopleRepository.findById(person_id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

}
