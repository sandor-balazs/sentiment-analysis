package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary.Dictionary;
import com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary.Entity;
import com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary.Phrase;

public class DictionaryParser extends AbstractXmlParser {

    private static Logger logger = LoggerFactory.getLogger(DictionaryParser.class);
    public static final String CONTEXT_PATH = "com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary";
    public static final String NAMESPACE = "http://github.com/sandor-balazs/sentiment-analysis/jaxb/dictionary";

    private DictionaryParser() {
        throw new AssertionError("Use the static factory method instead.");
    }

    public static Dictionary parse(String corpusPath) throws JAXBException,
            SAXException, ParserConfigurationException, IOException {
        return (Dictionary) unmarshal(corpusPath, CONTEXT_PATH, NAMESPACE);
    }

    public static void marshal(Dictionary dictionary, File file)
            throws JAXBException {
        marshal(dictionary, file, CONTEXT_PATH);
    }

    public static Dictionary createDictionary(String name,
            Map<String, Set<String>> collection) {
        Dictionary dictionary = new Dictionary();
        dictionary.setName(name);
        for (Entry<String, Set<String>> entry : collection.entrySet()) {
            logger.debug("\tdictionary item: {}", entry.getKey());
            Entity entity = new Entity();
            entity.setName(entry.getKey());
            for (String text : entry.getValue()) {
                logger.debug("\t\tphrase: {}", text);
                Phrase phrase = new Phrase();
                phrase.setText(text);
                entity.getPhrases().add(phrase);
            }
            dictionary.getEntities().add(entity);
        }
        return dictionary;
    }
}