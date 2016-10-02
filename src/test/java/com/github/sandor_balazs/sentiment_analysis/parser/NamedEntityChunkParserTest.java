package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.aliasi.chunk.CharLmHmmChunker;
import com.aliasi.chunk.Chunker;
import com.github.sandor_balazs.sentiment_analysis.classifier.Categorizer;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Constants;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary.Dictionary;

public class NamedEntityChunkParserTest {

    private static final Logger logger = LoggerFactory
            .getLogger(NamedEntityChunkParserTest.class);
    private static ReviewCorpus restaurantTrainerCorpus;
    private static ReviewCorpus restaurantEvaluatorCorpus;
    private static final String MODEL_FILE_NAME = "models/RestaurantReviewEntity.HmmChunker";
    private static final String DICTIONARY_NAME = "NamedEntityChunkParserDictionary";

    @BeforeClass
    public static void init() throws JAXBException, SAXException,
            ParserConfigurationException, IOException {
        restaurantTrainerCorpus = ReviewCorpusParser
                .parse(Constants.RESTAURANT_TRAINER_FILE);
        restaurantEvaluatorCorpus = ReviewCorpusParser
                .parse(Constants.RESTAURANT_EVALUATOR_FILE);
    }

    @Test
    public void train() throws JAXBException, SAXException,
            ParserConfigurationException, IOException {
        NamedEntityChunkParser<CharLmHmmChunker> parser = new NamedEntityChunkParser<CharLmHmmChunker>(
                restaurantTrainerCorpus);
        Assert.assertNotNull(parser);
    }

    @Test
    public void exportAndImportModel() throws JAXBException, SAXException,
            ParserConfigurationException, IOException, ClassNotFoundException {
        NamedEntityChunkParser<CharLmHmmChunker> parser = new NamedEntityChunkParser<CharLmHmmChunker>(
                restaurantTrainerCorpus);
        parser.writeModel(MODEL_FILE_NAME);
        Chunker chunker = NamedEntityChunkParser.parseModel(MODEL_FILE_NAME);
        Assert.assertNotNull(chunker);
    }

    @Test
    public void trainAndEvaluate() throws ClassNotFoundException, IOException,
            JAXBException, SAXException, ParserConfigurationException {
        logger.debug("Training");
        NamedEntityChunkParser<CharLmHmmChunker> parser = new NamedEntityChunkParser<CharLmHmmChunker>(
                restaurantTrainerCorpus);
        logger.debug("Evaluating");
        parser.evaluate(restaurantEvaluatorCorpus);
    }

    @Test
    public void marshalDictionary() throws JAXBException {
        SortedMap<String, Set<String>> collection = Categorizer.getCategories(
                restaurantTrainerCorpus, Categorizer.entityExtractor,
                Categorizer.dictionaryCollector, new TreeMap<String, Set<String>>());
        Dictionary dictionary = DictionaryParser.createDictionary(DICTIONARY_NAME,
                collection);
        DictionaryParser.marshal(dictionary, new File(DICTIONARY_NAME + ".xml"));
    }
}