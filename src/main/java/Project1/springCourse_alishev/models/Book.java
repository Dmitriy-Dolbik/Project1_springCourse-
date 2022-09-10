package Project1.springCourse_alishev.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int book_id;
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 1, max = 200, message = "Name should be between 2 and 200 characters")
    private String name;
    @NotEmpty(message = "Author should be not empty")
    @Size(min = 1, max = 100, message = "Author should be between 2 and 200 characters")
    private String author;
    @Min(value = 1920, message = "Year should be greater than 100")
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
