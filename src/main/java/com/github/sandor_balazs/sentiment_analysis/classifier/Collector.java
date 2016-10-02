package com.github.sandor_balazs.sentiment_analysis.classifier;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Sentence;

@FunctionalInterface
public interface Collector<T> {
    void collect(Sentence sentence, Extractor extractor, T collector);
}