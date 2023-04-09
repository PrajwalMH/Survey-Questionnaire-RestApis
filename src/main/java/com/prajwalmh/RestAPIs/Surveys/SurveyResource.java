package com.prajwalmh.RestAPIs.Surveys;


import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

@RestController
public class SurveyResource {



    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping("/getAllSurveys")
    public List<Survey> retrieveAllSurveys(){
        return surveyService.retrieveAllSurveys();
    }

    @RequestMapping("/getSurveyById/{id}")
    public Survey retrieveSurveyByid(@PathVariable String id){


        Survey survey = surveyService.retrieveSurveyById(id);
        if(survey==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        return survey;
    }


    @RequestMapping("/getSurveyById/{id}/questions")
    public List<Question> retrieveSurveyQuestions(@PathVariable String id){


        List<Question> questions = surveyService.retrieveAllSurveyQuestions(id);
        if(questions==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        return questions;
    }

    @RequestMapping("/getSurveyById/{surveyId}/questions/{questionId}")
    public Question retrieveSpecificSurveyQuestionsById(@PathVariable String surveyId,@PathVariable String questionId){


        Question question = surveyService.retrieveSpecificSurveyQuestions
                (surveyId, questionId);

        if(question==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return question;
    }


    @RequestMapping(value = "/getSurveyById/{surveyId}/questions",method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){

        String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        // /surveys/{surveyId}/questions/{questionId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location ).build();


    }


    @RequestMapping(value = "/deleteSurveyById/{surveyId}/questions/{questionId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId){


        surveyService.deleteSurveyQuestion
                (surveyId, questionId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/updateSurveyById/{surveyId}/questions/{questionId}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId,
                                                       @RequestBody Question question){


        surveyService.updateSurveyQuestion
                (surveyId, questionId,question);
        return ResponseEntity.noContent().build();
    }





}
