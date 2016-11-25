package org.craftsmenlabs.stories.api.models.validatorentry;

public interface Rankable extends Scorable, Comparable<Rankable>{
    String getRank();

    ValidatorEntryType getType();

    @Override
    default int compareTo(Rankable r){
        return getRank().compareTo(r.getRank());
    }
}
