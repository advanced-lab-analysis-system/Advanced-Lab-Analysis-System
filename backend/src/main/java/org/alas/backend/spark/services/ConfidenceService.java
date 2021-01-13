package org.alas.backend.spark.services;

import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.repositories.ExamRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfidenceService {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    private ExamRepository examRepository;

    public void getConfidence(String examId) {
        List<Tuple2<String, JavaPairRDD<String, MCQSubmission>>> submissionMap = new ArrayList<>();
        examRepository.findByExamId(examId)
                .subscribe(exam -> exam.getSubmissions()
                        .forEach((candidateId, submission) -> {
                            Tuple2<String, JavaPairRDD<String, MCQSubmission>> candidateSubmission;
                            List<Tuple2<String, MCQSubmission>> mcqSubmissionList = new ArrayList<>();
                            submission.forEach((questionId, mcqSubmission) -> {
                                Tuple2<String, MCQSubmission> currSubmission = new Tuple2<>(questionId, mcqSubmission);
                                mcqSubmissionList.add(currSubmission);
                            });
                            candidateSubmission = new Tuple2<>(candidateId, sc.parallelizePairs(mcqSubmissionList));
                            submissionMap.add(candidateSubmission);
                        }));
        JavaPairRDD<String, JavaPairRDD<String, MCQSubmission>> submissionRDD = sc.parallelizePairs(submissionMap);

    }
}
