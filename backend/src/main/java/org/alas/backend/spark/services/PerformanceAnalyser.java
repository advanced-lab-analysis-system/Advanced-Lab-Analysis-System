package org.alas.backend.spark.services;

import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.Question;
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

@Service
public class PerformanceAnalyser {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    private ExamRepository examRepository;

    public void getAnalytics(String examId) {
        List<Tuple2<String, Map<String, MCQSubmission>>> submissionMap = new ArrayList<>();
        examRepository.findByExamId(examId)
                .subscribe(exam -> exam.getSubmissions()
                        .forEach((candidateId, submission) -> {
                            Tuple2<String, Map<String, MCQSubmission>> candidateSubmission;
                            candidateSubmission = new Tuple2<>(candidateId, submission);
                            submissionMap.add(candidateSubmission);
                        }));
        JavaPairRDD<String, Map<String, MCQSubmission>> submissionRDD = sc.parallelizePairs(submissionMap);
        List<Question> questionList = new ArrayList<>();
        examRepository.findByExamId(examId).subscribe(exam -> questionList.addAll(exam.getQuestions()));

        JavaRDD<Question> questionJavaRDD = sc.parallelize(questionList);


    }
}
