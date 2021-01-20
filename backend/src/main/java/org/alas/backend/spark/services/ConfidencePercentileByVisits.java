package org.alas.backend.spark.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.Question;
import org.alas.backend.repositories.PerformanceAnalysisRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CandidateVisitConfidence {
    String candidateId;
    Boolean correctAnswer;
    int totalVisits;
}

@Service
public class ConfidencePercentileByVisits {

    @Autowired
    private PerformanceAnalysisRepository performanceAnalysisRepository;

    public void getConfidenceByVisits(JavaRDD<Question> questionJavaRDD,
                                      JavaPairRDD<String, Map<String, MCQSubmission>> submissionRDD) {

        JavaRDD<Object> temp = questionJavaRDD.map(question -> submissionRDD
                .filter(submissionTuple -> submissionTuple._2
                        .get(question.getQuestionId()).getFinalAnswer()
                        .equals(question.getAnswer()))
                .map(Tuple2::swap)
                .sortBy(submission -> submission._1.get(question.getQuestionId()).getTotalVisits(), false, 1));
    }
}
