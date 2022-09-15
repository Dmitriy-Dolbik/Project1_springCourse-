package Project1.springCourse_alishev.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Person")
public class Person {
    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @Column(name="name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной от 2 до 100 символов")
    private String name;
    @Column(name="year_of_birth")
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;
    @OneToMany(mappedBy="owner", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person(){}

    public Person(int person_id, String name, int yearOfBirth) {
        this.person_id = person_id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return person_id == person.person_id && yearOfBirth == person.yearOfBirth && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, name, yearOfBirth);
    }
}
