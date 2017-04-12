package org.craftsmenlabs.stories.api.models.items.types;

public interface Rankable extends Comparable<Rankable> {
    String getRank();

    @Override
    default int compareTo(Rankable r){
        return getRank().compareTo(r.getRank());
    }
}
