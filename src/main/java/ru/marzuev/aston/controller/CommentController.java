package ru.marzuev.aston.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.marzuev.aston.model.dto.CommentDto;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @GetMapping
    public void saveComments(@RequestBody CommentDto commentDto){

    }
}
