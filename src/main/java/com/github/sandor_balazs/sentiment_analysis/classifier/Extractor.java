package com.github.sandor_balazs.sentiment_analysis.classifier;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Opinion;

@FunctionalInterface
public interface Extractor {
    String extract(Opinion opinion);
}