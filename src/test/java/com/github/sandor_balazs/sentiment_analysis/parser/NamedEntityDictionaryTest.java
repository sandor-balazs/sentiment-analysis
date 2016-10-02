package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Constants;

public class NamedEntityDictionaryTest {

    private static final Logger logger = LoggerFactory
            .getLogger(NamedEntityDictionaryTest.class);

    @Test
    public void getSignificantPhrases() throws JAXBException, SAXException,
            ParserConfigurationException, IOException {
        Map<String, List<String>> phrases = NamedEntityDictionary
                .getSignificantPhrases(
                        ReviewCorpusParser.parse(Constants.RESTAURANT_TRAINER_FILE));
        printDebugInfo("Before pruning:", phrases);
        NamedEntityDictionary.prune(phrases);
        printDebugInfo("After pruning:", phrases);
    }

    private static void printDebugInfo(String phrase,
            Map<String, List<String>> words) {
        logger.debug(phrase);
        for (Entry<String, List<String>> entry : words.entrySet()) {
            logger.debug("{}>> {}", entry.getKey(), entry.getValue());
        }
    }
}