package me.bbb1991.service;

import me.bbb1991.dao.CommentDAO;
import me.bbb1991.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by bbb1991 on 12/26/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class CommentServiceImpl implements CommentService {

    private CommentDAO commentDAO;

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    public List<Comment> getCommentsByBookId(Long id) {
        return commentDAO.getByBook_Id(id);
    }

    public void saveOrUpdateComment(Comment comment) {
        commentDAO.save(comment);
    }

    @Autowired
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
}
