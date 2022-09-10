package Project1.springCourse_alishev.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int book_id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 1, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    private String name;
    @NotEmpty(message = "Имя автора не должен быть пустым")
    @Size(min = 1, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    private String author;
    @Min(value = 1920, message = "Год должен быть больше, чем 100")
    private int yearOfProduction;

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

}
