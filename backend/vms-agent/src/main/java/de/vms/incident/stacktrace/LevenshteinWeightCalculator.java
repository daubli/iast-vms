package de.vms.incident.stacktrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import de.vms.incident.model.StackTraceEntity;

public class LevenshteinWeightCalculator {
    private final Map<String, Long> stackFrameFrequency;
    private final Long numberOfRelevantIncidents;

    public LevenshteinWeightCalculator(Map<String, Long> stackFrameFrequency, Long numberOfRelevantIncidents) {
        this.stackFrameFrequency = stackFrameFrequency;
        this.numberOfRelevantIncidents = numberOfRelevantIncidents;
    }

    public List<Double> calculateWeight(StackTraceEntity stackTrace) {
        List<String> stackOfCalls = stackTrace.getStackOfCalls();

        List<Double> weight = new ArrayList<>();

        Double localWeight = 0.0;
        Double globalWeight = 0.0;

        Function<String, Double> idf =
                frame -> {
                    Long sfFrequency = this.stackFrameFrequency.get(frame);

                    if (sfFrequency == null) {
                        sfFrequency = 1l;
                    } else {
                        //consider not persisted stack frame
                        sfFrequency++;
                    }
                    
                    return Math.log(Double.valueOf(numberOfRelevantIncidents+1 / sfFrequency));
                };

        for (int frameIndex = 0; frameIndex < stackOfCalls.size(); frameIndex++) {
            localWeight = (1.0 / Math.pow(frameIndex + 1, 20));
            globalWeight = 1.0 / (1.0 + Math.exp(-(idf.apply(stackOfCalls.get(frameIndex)))));
            weight.add(localWeight*globalWeight);
        }

        return weight;
    }
}
