package com.quizsite.quiz;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CsvGeneratorUtil {
    private static final String CSV_HEADER = "Name,ID,Subject,Score,Percent,Grade\n";

    public String generateCsv(List<Csv> csvQuiz) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (Csv csv : csvQuiz) {
            csvContent.append(csv.getName()).append(",")
                      .append(csv.getStudentID()).append(",")
                      .append(csv.getSubject()).append(",")
                      .append(csv.getScore()).append(",")
                      .append(csv.getPercent()).append(",")
                      .append(csv.getGrade()).append("\n");
        }

        return csvContent.toString();
    }
}