package ru.marzuev.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.service.CommentService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto postComment(@RequestBody CommentDto commentDto,
                                   @RequestParam(defaultValue = "0") long bookId) throws SQLException {
        if (bookId == 0) {
            throw new IllegalArgumentException("Book Id Invalid");
        }
        return commentService.addComment(commentDto, bookId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto putComment(@RequestBody CommentDto commentDto,
                                 @PathVariable long commentId) throws SQLException {
        return commentService.updateComment(commentDto, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId) throws SQLException {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/{bookId}")
    public List<CommentDto> getCommentsByBookId(@PathVariable long bookId) throws SQLException {
        return commentService.getCommentsByBookId(bookId);
    }
}
