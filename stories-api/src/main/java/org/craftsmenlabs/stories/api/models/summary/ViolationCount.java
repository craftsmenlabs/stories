package org.craftsmenlabs.stories.api.models.summary;

import org.craftsmenlabs.stories.api.models.violation.ViolationType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViolationCount extends EnumMap<ViolationType, Integer> {

    public ViolationCount() {
        super(ViolationType.class);
        Arrays.asList(ViolationType.values())
                .forEach(violationType -> this.put(violationType, 0));
    }

    public ViolationCount(Map<ViolationType, Integer> map) {
        super(map);
    }

    public ViolationCount(ViolationCount violationCount) {
        super(ViolationType.class);
    }

    public ViolationCount getDefault(){
        ViolationCount vc = new ViolationCount();
        Arrays.asList(ViolationType.values())
                .forEach(violationType -> vc.put(violationType, 0));
        return vc;
    }

    public ViolationCount add(ViolationCount that){
        return merge(that, (u1, u2) -> u1 + u2);
    }

    public ViolationCount minus(ViolationCount that){
        return merge(that, (u1, u2) -> u1 - u2);
    }

    public ViolationCount divideBy(int denominator){
        if(denominator == 0){
            return new ViolationCount();
        }
        final Map<ViolationType, Integer> collect = this.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> (int) (entry.getValue() / denominator)));
        return new ViolationCount(collect);
    }

    public ViolationCount merge(ViolationCount that, BinaryOperator<Integer> op){
        final Map<ViolationType, Integer> integerMap =
                Stream.of(this, that)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        op)
                );
        return new ViolationCount(integerMap);
    }
}
