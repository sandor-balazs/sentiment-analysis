package com.github.sandor_balazs.sentiment_analysis.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.util.ScoredObject;
import com.github.sandor_balazs.sentiment_analysis.classifier.Categorizer;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;

public class NamedEntityDictionary {

    private static final Logger logger = LoggerFactory
            .getLogger(NamedEntityDictionary.class);

    private static int NGRAM = 3;

    public static Map<String, List<String>> getSignificantPhrases(
            ReviewCorpus corpus) {
        Map<String, List<String>> words = new HashMap<String, List<String>>();
        for (Entry<String, List<String>> entry : Categorizer
                .getCategories(corpus, Categorizer.entityExtractor,
                        Categorizer.categoryWithSentenceCollector,
                        new HashMap<String, List<String>>())
                .entrySet()) {
            TokenizedLM model = new TokenizedLM(
                    IndoEuropeanTokenizerFactory.INSTANCE, NGRAM);
            for (String sentence : entry.getValue()) {
                model.handle(sentence);
            }
            model.sequenceCounter().prune(3);
            words.put(entry.getKey(), new ArrayList<String>());
            words.get(entry.getKey()).addAll(getCollocations(model));
            words.get(entry.getKey()).addAll(getFrequentTerms(model));
        }
        return words;
    }

    public static void prune(Map<String, List<String>> phrases) {
        List<String> redundantPhrases = new ArrayList<String>();
        for (Entry<String, List<String>> entry : phrases.entrySet()) {
            for (Entry<String, List<String>> validatingEntry : phrases.entrySet()) {
                if (validatingEntry.getKey().equals(entry.getKey())) {
                    continue;
                }
                for (String phrase : validatingEntry.getValue()) {
                    if (entry.getValue().contains(phrase)) {
                        redundantPhrases.add(phrase);
                    }
                }
            }
        }
        for (Entry<String, List<String>> entry : phrases.entrySet()) {
            for (String phrase : redundantPhrases) {
                if (entry.getValue().contains(phrase)) {
                    entry.getValue().remove(phrase);
                }
            }
        }
    }

    private static List<String> getCollocations(TokenizedLM model) {
        List<String> list = new ArrayList<String>();
        for (int i = 10; i > 1; i--) {
            SortedSet<ScoredObject<String[]>> collocations = model.collocationSet(i,
                    1, 1000);
            if (collocations.size() > 0) {
                logger.debug("Collocations in Order of Significance [{}]:", i);
                list.addAll(capitalizedPhrase(collocations));
            }
        }
        return list;
    }

    private static List<String> getFrequentTerms(TokenizedLM model) {
        logger.debug("Frequent terms in Order of Significance:");
        SortedSet<ScoredObject<String[]>> frequentTerms = model.frequentTermSet(1,
                1000);
        return capitalizedPhrase(frequentTerms);
    }

    private static List<String> capitalizedPhrase(
            SortedSet<ScoredObject<String[]>> nGrams) {
        List<String> result = new ArrayList<String>();
        for (ScoredObject<String[]> nGram : nGrams) {
            String phrase = capitalizedPhrase(nGram.getObject());
            if (!"".equals(phrase)) {
                result.add(phrase);
                logger.debug("Score: {}, phrase: {}", nGram.score(), phrase);
            }
        }
        return result;
    }

    private static String capitalizedPhrase(String[] words) {
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            if (!isCapitalized(word)) {
                return "";
            }
            builder.append(" ").append(word);
        }
        return builder.toString();
    }

    private static boolean isCapitalized(String word) {
        if (!Character.isUpperCase(word.charAt(0))) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (!Character.isLowerCase(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}