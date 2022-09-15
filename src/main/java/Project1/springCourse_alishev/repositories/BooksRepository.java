package Project1.springCourse_alishev.repositories;


import Project1.springCourse_alishev.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
}
