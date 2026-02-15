package com.jha.QuizApp.service;

import com.jha.QuizApp.model.Question;
import com.jha.QuizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    QuestionDao questionDao;
//all question
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

//adding values
public ResponseEntity<List<Question>> saveAllQuestions(List<Question> questions) {
    try {
        List<Question> savedQuestions = questionDao.saveAll(questions);
        return new ResponseEntity<>(savedQuestions, HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
}


    //by category
    public ResponseEntity< List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

}
