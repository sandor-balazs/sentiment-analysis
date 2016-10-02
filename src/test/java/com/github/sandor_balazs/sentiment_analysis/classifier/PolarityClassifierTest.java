package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class PolarityClassifierTest extends ClassifierTest {

    @Test
    public void classifyLaptopReviews() throws FileNotFoundException, IOException {
        PolarityClassifier classifier = new PolarityClassifier(laptopTrainerCorpus);
        classifier.evaluate(laptopEvaluatorCorpus);
    }

    @Test
    public void classifyRestaurantReviews()
            throws FileNotFoundException, IOException {
        PolarityClassifier classifier = new PolarityClassifier(
                restaurantTrainerCorpus);
        classifier.evaluate(restaurantEvaluatorCorpus);
    }

    @Test
    public void writeModel() throws FileNotFoundException, IOException {
        PolarityClassifier classifier = new PolarityClassifier(laptopTrainerCorpus);
        String fileName = "polarity.model";
        classifier.writeModel(fileName);
    }
}