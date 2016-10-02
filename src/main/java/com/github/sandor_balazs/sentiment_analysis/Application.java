package com.github.sandor_balazs.sentiment_analysis;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.aliasi.chunk.CharLmHmmChunker;
import com.github.sandor_balazs.sentiment_analysis.classifier.EntityAttributeClassifier;
import com.github.sandor_balazs.sentiment_analysis.classifier.PolarityClassifier;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.parser.NamedEntityChunkParser;
import com.github.sandor_balazs.sentiment_analysis.parser.ReviewCorpusParser;

public class Application {
    public static void main(String[] args) throws JAXBException, SAXException,
            ParserConfigurationException, IOException, ClassNotFoundException {
        if (args.length != 2) {
            printUsage();
            return;
        }

        String trainerFile = args[0];
        ReviewCorpus trainerCorpus = ReviewCorpusParser
                .parse(new InputSource(trainerFile));
        EntityAttributeClassifier entityClassifier = new EntityAttributeClassifier(
                trainerCorpus);
        NamedEntityChunkParser<CharLmHmmChunker> chunkParser = new NamedEntityChunkParser<CharLmHmmChunker>(
                trainerCorpus);
        PolarityClassifier polarityClassifier = new PolarityClassifier(
                trainerCorpus);

        String evaluatorFile = args[1];
        ReviewCorpus evaluatorCorpus = ReviewCorpusParser
                .parse(new InputSource(evaluatorFile));
        entityClassifier.evaluate(evaluatorCorpus);
        chunkParser.evaluate(evaluatorCorpus);
        polarityClassifier.evaluate(evaluatorCorpus);
    }

    static void printUsage() {
        System.out.println("Usage: # java " + Application.class.getName()
                + " TRAINER_FILE EVALUATOR_FILE");
    }
}