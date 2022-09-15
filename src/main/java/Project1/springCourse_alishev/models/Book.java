package Project1.springCourse_alishev.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="Book")
public class Book {
    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @Column(name="name")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 1, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Имя автора не должен быть пустым")
    @Size(min = 1, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    private String author;
    @Column(name="year_of_production")
    @Min(value = 1920, message = "Год должен быть больше, чем 100")
    private int yearOfProduction;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
    public Book(){}
    public Book(int book_id, String name, String author, int yearOfProduction) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public int getYearOfProduction(){
        return yearOfProduction;
    }
    public void setYearOfProduction (int yearOfProduction){
        this.yearOfProduction=yearOfProduction;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_id == book.book_id && yearOfProduction == book.yearOfProduction && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, name, author, yearOfProduction);
    }
}
