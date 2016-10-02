package com.github.sandor_balazs.sentiment_analysis.jaxb.corpus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;

import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Opinion;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.OpinionContainer;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Review;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.ReviewCorpus;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.Sentence;
import com.github.sandor_balazs.sentiment_analysis.jaxb.corpus.SentenceContainer;
import com.github.sandor_balazs.sentiment_analysis.parser.ReviewCorpusParser;
import com.github.sandor_balazs.sentiment_analysis.util.NamespaceFilter;

public final class ReviewCorpusTest {

    private static JAXBContext context;
    private static Unmarshaller unmarshaller;
    private static UnmarshallerHandler unmarshallerHandler;
    private static XMLFilter xmlFilter;
    private static Marshaller marshaller;

    private static final String[] polarities = { "positive", "neutral", "negative" };
    private static final String[] categories = { "LAPTOP#GENERAL", "LAPTOP#QUALITY",
            "LAPTOP#PRICE" };

    @BeforeClass
    public static void init()
            throws JAXBException, SAXException, ParserConfigurationException {
        context = JAXBContext.newInstance(ReviewCorpusParser.CONTEXT_PATH);
        marshaller = context.createMarshaller();
        unmarshaller = context.createUnmarshaller();
        unmarshallerHandler = unmarshaller.getUnmarshallerHandler();
        xmlFilter = new NamespaceFilter(ReviewCorpusParser.NAMESPACE);
        xmlFilter.setParent(
                SAXParserFactory.newInstance().newSAXParser().getXMLReader());
        xmlFilter.setContentHandler(unmarshallerHandler);
    }

    public ReviewCorpus createReviewCorpus() {
        ReviewCorpus corpus = new ReviewCorpus();
        List<Review> reviews = corpus.getReviews();
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setId("rid" + i);
            review.setSentenceContainer(new SentenceContainer());
            List<Sentence> sentences = review.getSentenceContainer().getSentences();
            for (int j = 0; j < 5; j++) {
                Sentence s = new Sentence();
                s.setId(review.getId() + ":" + j);
                s.setText("text_" + s.getId());
                s.setOpinionContainer(new OpinionContainer());
                List<Opinion> opinions = s.getOpinionContainer().getOpinions();
                for (int k = 0; k < polarities.length; k++) {
                    Opinion o = new Opinion();
                    o.setCategory(categories[k]);
                    o.setPolarity(polarities[k]);
                    opinions.add(o);
                }
                sentences.add(s);
            }
            reviews.add(review);
        }
        return corpus;
    }

    @Test
    public void testMarshalling() throws JAXBException {
        File marshalledFile = new File("marshalling_test.xml");
        marshaller.marshal(createReviewCorpus(), marshalledFile);
        Assert.assertTrue(marshalledFile.length() > 0);
    }

    @Test
    public void testUnmarshallingLaptopReviews()
            throws JAXBException, IOException, SAXException {
        String path = Thread.currentThread().getContextClassLoader()
                .getResource(Constants.LAPTOP_EVALUATOR_FILE).getPath();
        xmlFilter.parse(new InputSource(path));
        ReviewCorpus corpus = (ReviewCorpus) unmarshallerHandler.getResult();
        Assert.assertNotNull(corpus);
    }

    @Test
    public void testUnmarshallingLaptopReviewsWithNamespace() throws JAXBException {
        String path = Thread.currentThread().getContextClassLoader()
                .getResource(Constants.LAPTOP_EVALUATOR_FILE_NS).getPath();
        ReviewCorpus corpus = (ReviewCorpus) unmarshaller.unmarshal(new File(path));
        Assert.assertNotNull(corpus);
    }

    @Test
    public void testUnmarshallingRestaurantReviews()
            throws IOException, SAXException, IllegalStateException, JAXBException {
        String path = Thread.currentThread().getContextClassLoader()
                .getResource(Constants.RESTAURANT_EVALUATOR_FILE).getPath();
        xmlFilter.parse(new InputSource(path));
        ReviewCorpus corpus = (ReviewCorpus) unmarshallerHandler.getResult();
        Assert.assertNotNull(corpus);
    }
}