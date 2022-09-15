package Project1.springCourse_alishev.dao;

import Project1.springCourse_alishev.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    public List<Book> showAllBooks(){
        return jdbcTemplate.query("SELECT*FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year_of_production) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYearOfProduction());
    }
    public Book show(int book_id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny()
                .orElse(null);
    }
    public void update(int book_id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year_of_production=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(),updatedBook.getYearOfProduction(),book_id);

    }
    public Book showOneBook(int book_id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public List<Book> showOneBookOfPerson(int person_id){
        return jdbcTemplate.query("SELECT*FROM Book WHERE person_id=?",
                new Object[]{person_id}, new BeanPropertyRowMapper<>(Book.class));
    }
    public void delete(int book_id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }
    //назначаем или освобождаем книгу в зависимости от того свободна она или нет.
    public void assingToPerson(int book_id, int person_id){
        if (person_id!=0){
            jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                    person_id, book_id);
        } else jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", null, book_id);
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
