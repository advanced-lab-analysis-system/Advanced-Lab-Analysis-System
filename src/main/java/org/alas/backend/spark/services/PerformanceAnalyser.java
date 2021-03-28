package org.alas.backend.spark.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alas.backend.dataobjects.MCQQuestion;
import org.alas.backend.dataobjects.QuestionMCQ;
import org.alas.backend.repositories.ExamRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PerformanceAnalyser {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    private ExamRepository examRepository;

    public void getAnalytics(String examId) {
        List<Tuple2<String, Map<String, Object>>> submissionMap = new ArrayList<>();
        examRepository.findByExamId(examId)
                .subscribe(exam -> exam.getSubmissions()
                        .forEach((candidateId, submission) -> {
                            Tuple2<String, Map<String, Object>> candidateSubmission;
                            candidateSubmission = new Tuple2<>(candidateId, submission);
                            submissionMap.add(candidateSubmission);
                        }));
        JavaPairRDD<String, Map<String, Object>> submissionRDD = sc.parallelizePairs(submissionMap);
        List<MCQQuestion> questionList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        examRepository.findByExamId(examId)
                .subscribe(exam -> questionList.addAll(
                        exam.getQuestionList().stream()
                                .filter(question -> question.getQuestionType().equals("mcq"))
                                .map(question -> {
                                    QuestionMCQ questionMCQ = new QuestionMCQ();
                                    try {
                                        String questionString = objectMapper.writeValueAsString(question.getQuestion());
                                        //System.out.println("In endExamByExamId1"+questionString);
                                        questionMCQ = objectMapper.readValue(questionString, QuestionMCQ.class);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    return new MCQQuestion(question.getQuestionId(), questionMCQ.getAnswer());
                                })
                                .collect(Collectors.toList())));

        JavaRDD<MCQQuestion> questionJavaRDD = sc.parallelize(questionList);

    }
}
