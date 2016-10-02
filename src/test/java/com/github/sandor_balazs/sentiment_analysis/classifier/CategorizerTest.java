package com.github.sandor_balazs.sentiment_analysis.classifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategorizerTest extends ClassifierTest {

    private static final Logger logger = LoggerFactory
            .getLogger(CategorizerTest.class);

    @Test
    public void getLaptopEntityCategories() {
        HashSet<String> categories = Categorizer
                .getEntityCategories(laptopTrainerCorpus);
        logger.debug("Size: {}", categories.size());
        Assert.assertTrue(categories.size() == 22);
    }

    @Test
    public void getLaptopEntityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getEntityCategoriesWithSentences(laptopTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("BATTERY").size(), 96);
        Assert.assertEquals(categories.get("COMPANY").size(), 88);
        Assert.assertEquals(categories.get("DISPLAY").size(), 126);
        Assert.assertEquals(categories.get("HARD_DISC").size(), 25);
        Assert.assertEquals(categories.get("KEYBOARD").size(), 90);
        Assert.assertEquals(categories.get("LAPTOP").size(), 1914);
        Assert.assertEquals(categories.get("MULTIMEDIA_DEVICES").size(), 45);
        Assert.assertEquals(categories.get("SUPPORT").size(), 146);
    }

    @Test
    public void getLaptopEvaluatorEntityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getEntityCategoriesWithSentences(laptopEvaluatorCorpus);
        Assert.assertEquals(categories.get("BATTERY").size(), 1);
        Assert.assertEquals(categories.get("COMPANY").size(), 3);
        Assert.assertEquals(categories.get("DISPLAY").size(), 2);
        Assert.assertEquals(categories.get("HARD_DISC").size(), 1);
        Assert.assertEquals(categories.get("KEYBOARD").size(), 1);
        Assert.assertEquals(categories.get("LAPTOP").size(), 49);
        Assert.assertEquals(categories.get("MULTIMEDIA_DEVICES").size(), 1);
        Assert.assertEquals(categories.get("SUPPORT").size(), 6);
    }

    @Test
    public void getLaptopAttributeCategories() {
        HashSet<String> categories = Categorizer
                .getAttributeCategories(laptopTrainerCorpus);
        if (logger.isDebugEnabled()) {
            logger.debug("Size: {}", categories.size());
            for (String category : categories) {
                logger.debug(category);
            }
        }
        Assert.assertTrue(categories.size() == 9);
        Assert.assertTrue(categories.contains("CONNECTIVITY"));
        Assert.assertTrue(categories.contains("DESIGN_FEATURES"));
        Assert.assertTrue(categories.contains("GENERAL"));
        Assert.assertTrue(categories.contains("OPERATION_PERFORMANCE"));
        Assert.assertTrue(categories.contains("PORTABILITY"));
        Assert.assertTrue(categories.contains("PRICE"));
        Assert.assertTrue(categories.contains("QUALITY"));
        Assert.assertTrue(categories.contains("USABILITY"));
    }

    @Test
    public void getLaptopAttributeCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getAttributeCategoriesWithSentences(laptopTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("CONNECTIVITY").size(), 55);
        Assert.assertEquals(categories.get("DESIGN_FEATURES").size(), 374);
        Assert.assertEquals(categories.get("GENERAL").size(), 868);
        Assert.assertEquals(categories.get("OPERATION_PERFORMANCE").size(), 469);
        Assert.assertEquals(categories.get("PORTABILITY").size(), 51);
        Assert.assertEquals(categories.get("PRICE").size(), 148);
        Assert.assertEquals(categories.get("QUALITY").size(), 547);
        Assert.assertEquals(categories.get("USABILITY").size(), 230);
    }

    @Test
    public void getLaptopEvaluatorAttributeCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getAttributeCategoriesWithSentences(laptopEvaluatorCorpus);
        Assert.assertEquals(categories.get("CONNECTIVITY").size(), 1);
        Assert.assertEquals(categories.get("DESIGN_FEATURES").size(), 9);
        Assert.assertEquals(categories.get("GENERAL").size(), 24);
        Assert.assertEquals(categories.get("OPERATION_PERFORMANCE").size(), 7);
        Assert.assertEquals(categories.get("PORTABILITY").size(), 1);
        Assert.assertEquals(categories.get("PRICE").size(), 7);
        Assert.assertEquals(categories.get("QUALITY").size(), 9);
        Assert.assertEquals(categories.get("USABILITY").size(), 6);
    }

    @Test
    public void getLaptopPolarityCategories() {
        HashSet<String> categories = Categorizer
                .getPolarityCategories(laptopTrainerCorpus);
        Assert.assertTrue(categories.size() == 3);
        Assert.assertTrue(categories.contains("negative"));
        Assert.assertTrue(categories.contains("neutral"));
        Assert.assertTrue(categories.contains("positive"));
    }

    @Test
    public void getLaptopPolarityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getPolarityCategoriesWithSentences(laptopTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("negative").size(), 1086);
        Assert.assertEquals(categories.get("neutral").size(), 188);
        Assert.assertEquals(categories.get("positive").size(), 1634);
    }

    @Test
    public void getLaptopEvaluatorPolarityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getPolarityCategoriesWithSentences(laptopEvaluatorCorpus);
        Assert.assertEquals(categories.get("negative").size(), 20);
        Assert.assertEquals(categories.get("neutral").size(), 2);
        Assert.assertEquals(categories.get("positive").size(), 42);
    }

    @Test
    public void getRestaurantCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getCategoriesWithSentences(restaurantTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Map.Entry<String, List<String>> category : categories.entrySet()) {
                logger.debug("Category: {}, entries: {}, sentences: ",
                        category.getKey(), category.getValue().size());
            }
        }
        Assert.assertNotNull(categories);
    }

    @Test
    public void getRestaurantEntityCategories() {
        HashSet<String> categories = Categorizer
                .getEntityCategories(restaurantTrainerCorpus);
        Assert.assertTrue(categories.size() == 6);
        Assert.assertTrue(categories.contains("AMBIENCE"));
        Assert.assertTrue(categories.contains("DRINKS"));
        Assert.assertTrue(categories.contains("FOOD"));
        Assert.assertTrue(categories.contains("LOCATION"));
        Assert.assertTrue(categories.contains("RESTAURANT"));
        Assert.assertTrue(categories.contains("SERVICE"));
    }

    @Test
    public void getRestaurantEntityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getEntityCategoriesWithSentences(restaurantTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Map.Entry<String, List<String>> category : categories.entrySet()) {
                logger.debug("Category: {}, entries: {}, sentences: ",
                        category.getKey(), category.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("AMBIENCE").size(), 255);
        Assert.assertEquals(categories.get("DRINKS").size(), 99);
        Assert.assertEquals(categories.get("FOOD").size(), 1075);
        Assert.assertEquals(categories.get("LOCATION").size(), 28);
        Assert.assertEquals(categories.get("RESTAURANT").size(), 600);
        Assert.assertEquals(categories.get("SERVICE").size(), 449);
    }

    @Test
    public void getRestaurantEvaluatorEntityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getEntityCategoriesWithSentences(restaurantEvaluatorCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("AMBIENCE").size(), 9);
        Assert.assertEquals(categories.get("DRINKS").size(), 2);
        Assert.assertEquals(categories.get("FOOD").size(), 32);
        Assert.assertEquals(categories.get("LOCATION").size(), 1);
        Assert.assertEquals(categories.get("RESTAURANT").size(), 10);
        Assert.assertEquals(categories.get("SERVICE").size(), 12);
    }

    @Test
    public void getRestaurantAttributeCategories() {
        HashSet<String> categories = Categorizer
                .getAttributeCategories(restaurantTrainerCorpus);
        logger.debug("Size: {}", categories.size());
        Assert.assertTrue(categories.size() == 5);
        Assert.assertTrue(categories.contains("GENERAL"));
        Assert.assertTrue(categories.contains("PRICES"));
        Assert.assertTrue(categories.contains("QUALITY"));
        Assert.assertTrue(categories.contains("STYLE_OPTIONS"));
    }

    @Test
    public void getRestaurantAttributeCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getAttributeCategoriesWithSentences(restaurantTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("GENERAL").size(), 1155);
        Assert.assertEquals(categories.get("PRICES").size(), 189);
        Assert.assertEquals(categories.get("QUALITY").size(), 897);
        Assert.assertEquals(categories.get("STYLE_OPTIONS").size(), 168);
    }

    @Test
    public void getRestaurantEvaluatorAttributeCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getAttributeCategoriesWithSentences(restaurantEvaluatorCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("GENERAL").size(), 31);
        Assert.assertEquals(categories.get("PRICES").size(), 3);
        Assert.assertEquals(categories.get("QUALITY").size(), 31);
        Assert.assertEquals(categories.get("STYLE_OPTIONS").size(), 1);
    }

    @Test
    public void getRestaurantPolarityCategories() {
        HashSet<String> categories = Categorizer
                .getPolarityCategories(restaurantTrainerCorpus);
        Assert.assertTrue(categories.size() == 3);
        Assert.assertTrue(categories.contains("negative"));
        Assert.assertTrue(categories.contains("neutral"));
        Assert.assertTrue(categories.contains("positive"));
    }

    @Test
    public void getRestaurantPolarityCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getPolarityCategoriesWithSentences(restaurantTrainerCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("negative").size(), 751);
        Assert.assertEquals(categories.get("neutral").size(), 98);
        Assert.assertEquals(categories.get("positive").size(), 1657);
    }

    @Test
    public void getRestaurantPolarityEvaluatorCategoriesWithSentences() {
        HashMap<String, List<String>> categories = Categorizer
                .getPolarityCategoriesWithSentences(restaurantEvaluatorCorpus);
        if (logger.isDebugEnabled()) {
            for (Entry<String, List<String>> entry : categories.entrySet()) {
                logger.debug("Entry: {}, size: {}", entry.getKey(),
                        entry.getValue().size());
            }
        }
        Assert.assertEquals(categories.get("negative").size(), 24);
        Assert.assertEquals(categories.get("neutral").size(), 3);
        Assert.assertEquals(categories.get("positive").size(), 39);
    }
}