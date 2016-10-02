package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.aliasi.chunk.CharLmHmmChunker;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.ChunkerEvaluator;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.HmmChunker;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.hmm.HmmCharLmEstimator;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;
import com.github.sandor_balazs.sentiment_analysis.classifier.Categorizer;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Opinion;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Review;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Sentence;

public class NamedEntityChunkParser<H extends ObjectHandler<Chunking>> {

    private static final Logger logger = LoggerFactory
            .getLogger(NamedEntityChunkParser.class);

    private CharLmHmmChunker chunkerEstimator;

    private static final int NGRAM = 8;
    private static final int CHARS = 256;
    private static final double INTERPOLATION = NGRAM;

    public NamedEntityChunkParser(ReviewCorpus trainerCorpus) throws JAXBException,
            SAXException, ParserConfigurationException, IOException {
        TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
        HmmCharLmEstimator estimator = new HmmCharLmEstimator(NGRAM, CHARS,
                INTERPOLATION);
        chunkerEstimator = new CharLmHmmChunker(factory, estimator);
        train(trainerCorpus);
    }

    public NamedEntityChunkParser(String fileName)
            throws ClassNotFoundException, IOException {
        chunkerEstimator = (CharLmHmmChunker) parseModel(fileName);
    }

    public void writeModel(String fileName) throws IOException {
        AbstractExternalizable.compileTo(chunkerEstimator, new File(fileName));
    }

    public static Chunker parseModel(String fileName)
            throws ClassNotFoundException, IOException {
        File modelFile = new File(fileName);
        Chunker chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
        return chunker;
    }

    public void train(ReviewCorpus trainerCorpus) throws JAXBException, SAXException,
            ParserConfigurationException, IOException {
        List<Chunking> chunkings = Categorizer.getCategories(trainerCorpus,
                Categorizer.entityExtractor, Categorizer.chunkingCollector,
                new ArrayList<Chunking>());
        for (Chunking chunking : chunkings) {
            chunkerEstimator.handle(chunking);
        }
    }

    public void evaluate(ReviewCorpus evaluatorCorpus)
            throws ClassNotFoundException, IOException {
        if (logger.isDebugEnabled()) {
            printDebugInfo(evaluatorCorpus);
        }
        long start = System.currentTimeMillis();
        ChunkerEvaluator evaluator = new ChunkerEvaluator(
                (HmmChunker) AbstractExternalizable.compile(chunkerEstimator));
        evaluator.setVerbose(true);
        evaluator.setMaxNBest(128);
        evaluator.setMaxNBestReport(8);
        evaluator.setMaxConfidenceChunks(16);
        List<Chunking> chunkings = Categorizer.getCategories(evaluatorCorpus,
                Categorizer.entityExtractor, Categorizer.chunkingCollector,
                new ArrayList<Chunking>());
        for (Chunking chunking : chunkings) {
            evaluator.handle(chunking);
        }
        logger.info("Evaluation (elapsed: " + (System.currentTimeMillis() - start)
                + ")\n{}", evaluator);
    }

    private void printDebugInfo(ReviewCorpus corpus) {
        for (Review review : corpus.getReviews()) {
            for (Sentence sentence : review.getSentenceContainer().getSentences()) {
                Chunking chunking = chunkerEstimator.chunk(sentence.getText());
                logger.debug("Chunking={}", chunking);
                if (sentence.getOpinionContainer() != null) {
                    for (Opinion opinion : sentence.getOpinionContainer()
                            .getOpinions()) {
                        logger.debug("\tOpinions: [{}-{}:{}], target: {}",
                                opinion.getFrom(), opinion.getTo(),
                                Categorizer.entityExtractor.extract(opinion),
                                opinion.getTarget());
                    }
                }
            }
        }
    }
}