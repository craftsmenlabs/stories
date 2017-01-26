package org.craftsmenlabs.stories.api.models.summary;

public interface Summarizable<T extends Summarizable> {
    T divideBy(int denominator);

    T plus(T that);

    T minus(T that);
}
