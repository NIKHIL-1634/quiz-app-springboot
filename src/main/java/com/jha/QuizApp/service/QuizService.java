package com.jha.QuizApp.service;

import com.jha.QuizApp.dao.QuestionDao;
import com.jha.QuizApp.dao.QuizDao;
import com.jha.QuizApp.model.Question;
import com.jha.QuizApp.model.QuestionWrapper;
import com.jha.QuizApp.model.Quiz;
import com.jha.QuizApp.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    // 🔹 Create Quiz
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions =
                questionDao.findRandomQuestionByCategory(category, numQ);

        if (questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for category", HttpStatus.NOT_FOUND);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz Created Successfully", HttpStatus.CREATED);
    }

    // 🔹 Get Quiz Questions (Hide correct answer)
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quizOptional = quizDao.findById(id);

        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

        List<Question> questionsFromDB = quizOptional.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );
            questionForUser.add(qw);
        }

        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    // 🔹 Calculate Result (Correct Logic Using ID Matching)
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Optional<Quiz> quizOptional = quizDao.findById(id);

        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quizOptional.get().getQuestions();

        // Create Map for fast lookup
        Map<Integer, String> correctAnswers = new HashMap<>();

        for (Question q : questions) {
            correctAnswers.put(q.getId(), q.getRightAnswer());
        }

        int right = 0;

        for (Response r : responses) {
            if (correctAnswers.containsKey(r.getId())) {

                if (correctAnswers.get(r.getId()).equals(r.getResponse())) {
                    right++;
                }
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
