package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;

public class ReviewCorpusParser extends AbstractXmlParser {

    public static final String CONTEXT_PATH = "com.github.sandor_balazs.sentiment_analysis.jaxb.corpus";
    public static final String NAMESPACE = "http://github.com/sandor-balazs/sentiment-analysis/jaxb/corpus";

    private ReviewCorpusParser() {
        throw new AssertionError("Use the static factory method instead.");
    }

    public static ReviewCorpus parse(String corpusPath) throws JAXBException,
            SAXException, ParserConfigurationException, IOException {
        return (ReviewCorpus) unmarshal(corpusPath, CONTEXT_PATH, NAMESPACE);
    }

    public static ReviewCorpus parse(InputSource inputSource) throws JAXBException,
            SAXException, ParserConfigurationException, IOException {
        return (ReviewCorpus) unmarshal(inputSource, CONTEXT_PATH, NAMESPACE);
    }
}