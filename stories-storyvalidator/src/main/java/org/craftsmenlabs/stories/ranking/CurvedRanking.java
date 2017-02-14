package org.craftsmenlabs.stories.ranking;

import org.craftsmenlabs.stories.api.models.items.types.BacklogItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CurvedRanking implements Ranking {
    public static final int SMOOTH_CURVE = 2;
    private List<? extends BacklogItem> entries;


    /**
     * Returns a normalised range of points associated with the position.
     *
     * @param entries
     * @return
     */
    @Override
    public List<Double> getRanking(List<? extends BacklogItem> entries) {
        this.entries = entries;
        final List<Double> absoluteCurve = IntStream.range(0, entries.size())
                .mapToObj(this::curvedQuotient)
                .collect(Collectors.toList());
        final double sum = absoluteCurve.stream().mapToDouble(f -> f).sum();

        return absoluteCurve.stream().map(f -> f / sum).collect(Collectors.toList());
    }

    private double curvedQuotient(int position) {
        return curvedQuotient(position, entries.size());
    }

    private double curvedQuotient(int position, int size) {
        return 1.0 - Math.pow(((double) position) / size, SMOOTH_CURVE);
    }
}
