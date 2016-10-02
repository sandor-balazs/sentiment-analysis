package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassifierEvaluator;
import com.aliasi.lm.NGramProcessLM;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;

public abstract class AbstractClassifier {

    private static final Logger logger = LoggerFactory
            .getLogger(AbstractClassifier.class);

    protected DynamicLMClassifier<NGramProcessLM> classifier;

    protected final int NGRAM = 8;

    protected AbstractClassifier(ReviewCorpus trainerCorpus)
            throws FileNotFoundException, IOException {
        classifier = DynamicLMClassifier
                .createNGramProcess(getCategories(trainerCorpus), NGRAM);
        train(trainerCorpus);
    }

    protected abstract String[] getCategories(ReviewCorpus corpus);

    protected abstract HashMap<String, List<String>> getCategoriesWithSentences(
            ReviewCorpus corpus);

    public void writeModel(String fileName)
            throws FileNotFoundException, IOException {
        classifier.compileTo(new ObjectOutputStream(new FileOutputStream(fileName)));
    }

    protected void train(ReviewCorpus trainerCorpus)
            throws FileNotFoundException, IOException {
        HashMap<String, List<String>> categoriesWithSentences = getCategoriesWithSentences(
                trainerCorpus);
        int numberOfCategories = 0;
        int numberOfSentences = 0;
        for (String category : classifier.categories()) {
            logger.debug("Training: {}", category);
            Classification classification = new Classification(category);
            numberOfCategories++;
            numberOfSentences = 0;
            for (String sentence : categoriesWithSentences.get(category)) {
                logger.debug("\t sentence: {}", sentence);
                Classified<CharSequence> classified = new Classified<CharSequence>(
                        sentence, classification);
                classifier.handle(classified);
                numberOfSentences++;
            }
            logger.debug("Training of {} finished. Number of sentences used: {}",
                    category, numberOfSentences);
        }
        logger.debug("Training finished. Number of categories: {}.",
                numberOfCategories);
    }

    public void evaluate(ReviewCorpus evaluatorCorpus) {
        JointClassifierEvaluator<CharSequence> evaluator = new JointClassifierEvaluator<CharSequence>(
                classifier, classifier.categories(), false);
        HashMap<String, List<String>> categoriesWithSentences = getCategoriesWithSentences(
                evaluatorCorpus);
        int numberOfCategories = 0;
        int numberOfSentences = 0;
        for (String category : getCategories(evaluatorCorpus)) {
            Classification classification = new Classification(category);
            numberOfCategories++;
            numberOfSentences = 0;
            for (String sentence : categoriesWithSentences.get(category)) {
                Classified<CharSequence> classified = new Classified<CharSequence>(
                        sentence, classification);
                evaluator.handle(classified);
                numberOfSentences++;
            }
            logger.debug("Evaluation of {} finished. Number of sentences used: {}",
                    category, numberOfSentences);
        }
        logger.debug(
                "Evaluation of {} finished. Number of categories: {}. Details: {}",
                this.getClass(), numberOfCategories, evaluator);
    }
}