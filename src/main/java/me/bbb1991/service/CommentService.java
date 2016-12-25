package me.bbb1991.service;

import me.bbb1991.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bbb1991 on 12/26/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Service
public interface CommentService {

    void saveOrUpdateComment(Comment comment);

    List<Comment> getCommentsByBookId(Long id);
}
