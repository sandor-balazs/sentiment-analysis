package com.github.sandor_balazs.sentiment_analysis.classifier;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;

public interface Classifier {
    public void evaluate(ReviewCorpus corpus);
}