package Project1.springCourse_alishev.dao;

import Project1.springCourse_alishev.models.Book;
import Project1.springCourse_alishev.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component

public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Book> showAll(){
        return jdbcTemplate.query("SELECT*FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book showOne(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }
    public void update(int book_id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(),updatedBook.getYear(),book_id);

    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id=Person.id" +
                        " WHERE Book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void release(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id=?",id);
    }
    //назначаем или освобождаем книгу в зависимости от того свободна она или нет.
    public void assing(int id, Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?",
                selectedPerson.getId(), id);
    }

    //достаем одно значение одной колонки из таблицы (получаем book_id из запроса, передаем в SQL-запрос
    /*public Integer showPerson_id(int book_id){
        int person_id=0;
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT person_id FROM Book WHERE book_id="+book_id;
            System.out.println(SQL);
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                person_id = resultSet.getInt("person_id");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(person_id);
        return person_id;

    }*/



}
