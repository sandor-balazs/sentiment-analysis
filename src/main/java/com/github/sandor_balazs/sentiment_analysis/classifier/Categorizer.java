package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.ChunkFactory;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.ChunkingImpl;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Opinion;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Review;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Sentence;

public class Categorizer {

    public static final Logger logger = LoggerFactory.getLogger(Categorizer.class);

    public static final Extractor entityExtractor = (
            Opinion opinion) -> opinion.getCategory().split("#")[0];

    public static final Extractor attributeExtractor = (
            Opinion opinion) -> opinion.getCategory().split("#")[1];

    public static final Extractor polarityExtractor = (Opinion opinion) -> opinion
            .getPolarity();

    public static final Extractor categoryExtractor = (Opinion opinion) -> opinion
            .getCategory();

    public static final Collector<HashSet<String>> categoryCollector = (
            Sentence sentence, Extractor extractor, HashSet<String> categories) -> {
        for (Opinion opinion : sentence.getOpinionContainer().getOpinions()) {
            categories.add(extractor.extract(opinion));
        }
    };

    public static final Collector<HashMap<String, List<String>>> categoryWithSentenceCollector = (
            Sentence sentence, Extractor extractor,
            HashMap<String, List<String>> categories) -> {
        for (Opinion opinion : sentence.getOpinionContainer().getOpinions()) {
            String category = extractor.extract(opinion);
            if (!categories.containsKey(category)) {
                categories.put(category, new ArrayList<String>());
            }
            categories.get(category).add(sentence.getText());
        }
    };

    private static boolean isValid(Opinion opinion, Sentence sentence) {
        int start = opinion.getFrom().intValue();
        int end = opinion.getTo().intValue();
        int length = sentence.getText().length();
        if (start > length || end > length || start == end
                || "NULL".equals(opinion.getTarget())) {
            return false;
        }
        return true;
    }

    public static final Collector<SortedMap<String, Set<String>>> dictionaryCollector = (
            Sentence sentence, Extractor extractor,
            SortedMap<String, Set<String>> dictionary) -> {
        for (Opinion opinion : sentence.getOpinionContainer().getOpinions()) {
            if (!isValid(opinion, sentence)) {
                continue;
            }
            String entity = extractor.extract(opinion);
            String phrase = opinion.getTarget();
            logger.debug("Dictionary item - entity: {} phrase: {}", entity, phrase);
            if (!dictionary.containsKey(entity)) {
                dictionary.put(entity, new TreeSet<String>());
            }
            dictionary.get(entity).add(phrase);
        }
    };

    public static final Collector<List<Chunking>> chunkingCollector = (
            Sentence sentence, Extractor extractor, List<Chunking> chunkings) -> {
        List<Chunk> chunks = new ArrayList<Chunk>();
        for (Opinion opinion : sentence.getOpinionContainer().getOpinions()) {
            if (!isValid(opinion, sentence)) {
                continue;
            }
            Chunk chunk = ChunkFactory.createChunk(opinion.getFrom().intValue(),
                    opinion.getTo().intValue(), extractor.extract(opinion));
            logger.debug("Chunk: {}, target: {}", chunk, opinion.getTarget());
            chunks.add(chunk);
        }
        if (!chunks.isEmpty()) {
            ChunkingImpl chunking = new ChunkingImpl(sentence.getText());
            chunking.addAll(chunks);
            chunkings.add(chunking);

        }
    };

    public static <T> T getCategories(ReviewCorpus corpus, Extractor extractor,
            Collector<T> collector, T categories) {
        for (Review review : corpus.getReviews()) {
            for (Sentence sentence : review.getSentenceContainer().getSentences()) {
                if (sentence.getOpinionContainer() != null) {
                    collector.collect(sentence, extractor, categories);
                }
            }
        }
        return categories;
    }

    public static HashSet<String> getCategories(ReviewCorpus corpus) {
        return getCategories(corpus, categoryExtractor, categoryCollector,
                new HashSet<String>());
    }

    public static HashMap<String, List<String>> getCategoriesWithSentences(
            ReviewCorpus corpus) {
        return getCategories(corpus, categoryExtractor,
                categoryWithSentenceCollector, new HashMap<String, List<String>>());
    }

    public static HashSet<String> getEntityCategories(ReviewCorpus corpus) {
        return getCategories(corpus, entityExtractor, categoryCollector,
                new HashSet<String>());
    }

    public static HashMap<String, List<String>> getEntityCategoriesWithSentences(
            ReviewCorpus corpus) {
        return getCategories(corpus, entityExtractor, categoryWithSentenceCollector,
                new HashMap<String, List<String>>());
    }

    public static HashSet<String> getAttributeCategories(ReviewCorpus corpus) {
        return getCategories(corpus, attributeExtractor, categoryCollector,
                new HashSet<String>());
    }

    public static HashMap<String, List<String>> getAttributeCategoriesWithSentences(
            ReviewCorpus corpus) {
        return getCategories(corpus, attributeExtractor,
                categoryWithSentenceCollector, new HashMap<String, List<String>>());
    }

    public static HashSet<String> getPolarityCategories(ReviewCorpus corpus) {
        return getCategories(corpus, polarityExtractor, categoryCollector,
                new HashSet<String>());
    }

    public static HashMap<String, List<String>> getPolarityCategoriesWithSentences(
            ReviewCorpus corpus) {
        return getCategories(corpus, polarityExtractor,
                categoryWithSentenceCollector, new HashMap<String, List<String>>());
    }
}