package com.jha.QuizApp.controller;

import com.jha.QuizApp.model.Question;
import com.jha.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // I want Accept the request
@RequestMapping("question")// accept request from question path
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestion")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<List<Question>> addAllQuestions(@RequestBody List<Question> questions) {
        return questionService.saveAllQuestions(questions);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

}
