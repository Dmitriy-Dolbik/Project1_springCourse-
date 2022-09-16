package Project1.springCourse_alishev.repositories;

import Project1.springCourse_alishev.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    List<Person> findByFullName(String fullName);
}
