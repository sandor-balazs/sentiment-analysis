package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;

public class EntityAttributeClassifier extends AbstractClassifier
        implements Classifier {

    public EntityAttributeClassifier(ReviewCorpus trainerCorpus)
            throws FileNotFoundException, IOException {
        super(trainerCorpus);
    }

    @Override
    protected String[] getCategories(ReviewCorpus corpus) {
        return Categorizer.getCategories(corpus).toArray(new String[0]);
    }

    @Override
    protected HashMap<String, List<String>> getCategoriesWithSentences(
            ReviewCorpus corpus) {
        return Categorizer.getCategoriesWithSentences(corpus);
    }
}