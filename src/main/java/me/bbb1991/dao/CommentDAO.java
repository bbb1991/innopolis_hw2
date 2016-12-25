package me.bbb1991.dao;

import me.bbb1991.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bbb1991 on 12/25/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface CommentDAO extends JpaRepository<Comment, Long> {

    List<Comment> getByBook_Id(Long id);
}
