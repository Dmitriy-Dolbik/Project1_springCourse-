package Project1.springCourse_alishev.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Table(name="Book")
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="title")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 1, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    private String title;
    @Column(name="author")
    @NotEmpty(message = "Имя автора не должен быть пустым")
    @Size(min = 1, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    private String author;
    @Column(name="year")
    @Min(value = 100, message = "Год должен быть больше, чем 100")
    private int year;

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName="id")
    private Person owner;

    @Column(name="date_of_assignment")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Calendar dateOfAssignment;

    @Transient
    private boolean overdue;
    public Book(){}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Calendar getDateOfAssignment() {
        return dateOfAssignment;
    }

    public void setDateOfAssignment(Calendar dateOfAssignment) {
        this.dateOfAssignment = dateOfAssignment;
    }

    public boolean isOverdue() {
        Calendar tenDaysAfterAssignment = dateOfAssignment;
        tenDaysAfterAssignment.add(Calendar.DAY_OF_MONTH, 10);
        Calendar currentDay = Calendar.getInstance();
        return currentDay.after(tenDaysAfterAssignment);
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }

}
