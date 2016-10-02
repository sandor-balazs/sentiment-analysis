package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.xml.sax.SAXException;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Constants;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.parser.ReviewCorpusParser;

public abstract class ClassifierTest {

    protected static ReviewCorpus laptopTrainerCorpus;
    protected static ReviewCorpus laptopEvaluatorCorpus;
    protected static ReviewCorpus restaurantTrainerCorpus;
    protected static ReviewCorpus restaurantEvaluatorCorpus;

    @BeforeClass
    public static void init() throws JAXBException, SAXException,
            ParserConfigurationException, IOException {
        laptopTrainerCorpus = ReviewCorpusParser
                .parse(Constants.LAPTOP_TRAINER_FILE);
        laptopEvaluatorCorpus = ReviewCorpusParser
                .parse(Constants.LAPTOP_EVALUATOR_FILE);
        restaurantTrainerCorpus = ReviewCorpusParser
                .parse(Constants.RESTAURANT_TRAINER_FILE);
        restaurantEvaluatorCorpus = ReviewCorpusParser
                .parse(Constants.RESTAURANT_EVALUATOR_FILE);
    }
}