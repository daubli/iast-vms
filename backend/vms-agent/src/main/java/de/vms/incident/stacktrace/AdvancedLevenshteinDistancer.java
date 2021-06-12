package de.vms.incident.stacktrace;

import static org.apache.commons.lang3.math.NumberUtils.min;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.vms.incident.model.StackTraceEntity;

public class AdvancedLevenshteinDistancer {

    private final Double weights1Sum;

    private LevenshteinWeightCalculator weightCalculator;

    StackTraceEntity stackTrace1;

    List<Double> weights1;

    Double[][] matrix;

    public AdvancedLevenshteinDistancer(StackTraceEntity stackTrace1, Long numberOfRelevantIncidents,
            Map<String, Long> idf) {
        this.stackTrace1 = stackTrace1;
        weightCalculator = new LevenshteinWeightCalculator(idf, numberOfRelevantIncidents);
        this.weights1 = weightCalculator.calculateWeight(stackTrace1);
        this.weights1Sum = this.weights1.stream().reduce(0.0, (a, b) -> a + b);
    }

    public Double calculateSimilarity(StackTraceEntity stackTrace2) {
        List<Double> weights2 = weightCalculator.calculateWeight(stackTrace2);
        Double max_dist = this.weights1Sum + weights2.stream().reduce(0.0, (a, b) -> a + b);

        if (max_dist == 0.0) {
            return max_dist;
        } else {
            return 1 - (distance(stackTrace2, weights2) / max_dist);
        }
    }

    private Double distance(StackTraceEntity stackTrace2, List<Double> weights2) {

        //init levenshtein matrix
        List<String> stackOfCalls1 = stackTrace1.getStackOfCalls();
        List<String> stackOfCalls2 = stackTrace2.getStackOfCalls();

        matrix = new Double[stackOfCalls2.size() + 1][stackOfCalls1.size() + 1];
        //fill array with 0
        for (Double[] row : matrix) {
            Arrays.fill(row, 0.0);
        }

        Double[] prev_column = matrix[0];

        //maybe this can ported into an init method
        for (int index = 0; index < stackOfCalls1.size(); index++) {
            prev_column[index + 1] = prev_column[index] + weights1.get(index);
        }

        if (stackOfCalls1.size() == 0 || stackOfCalls2.size() == 0) {
            return 0.0;
        }

        Double[] curr_column = matrix[1];
        String tmp1Frame, tmp2Frame;
        Double weight1, weight2;

        for (int i2 = 0; i2 < stackOfCalls2.size(); i2++) {
            tmp2Frame = stackOfCalls2.get(i2);
            weight2 = weights2.get(i2);

            curr_column[0] = prev_column[0] + weight2;

            for (int i1 = 0; i1 < stackOfCalls1.size(); i1++) {
                tmp1Frame = stackOfCalls1.get(i1);
                weight1 = weights1.get(i1);

                if (tmp1Frame.equals(tmp2Frame)) {
                    curr_column[i1 + 1] = prev_column[i1];
                } else {
                    Double change = weight1 + weight2 + prev_column[i1];
                    Double remove = weight2 + prev_column[i1 + 1];
                    Double insert = weight1 + curr_column[i1];

                    curr_column[i1 + 1] = min(change, remove, insert);
                }
            }

            if (i2 != (stackOfCalls2.size() - 1)) {
                prev_column = curr_column;
                curr_column = matrix[i2 + 1];
            }
        }

        return curr_column[stackOfCalls1.size()];
    }
}
