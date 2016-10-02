package com.github.sandor_balazs.sentiment_analysis.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;

import com.github.sandor_balazs.sentiment_analysis.util.NamespaceFilter;

public abstract class AbstractXmlParser {

    public static Object unmarshal(String path, String contextPath, String namespace)
            throws JAXBException, SAXException, ParserConfigurationException,
            IOException {
        UnmarshallerHandler unmarshallerHandler = JAXBContext
                .newInstance(contextPath).createUnmarshaller()
                .getUnmarshallerHandler();
        XMLFilter xmlFilter = new NamespaceFilter(namespace);
        xmlFilter.setParent(
                SAXParserFactory.newInstance().newSAXParser().getXMLReader());
        xmlFilter.setContentHandler(unmarshallerHandler);
        xmlFilter.parse(new InputSource(Thread.currentThread()
                .getContextClassLoader().getResource(path).getPath()));
        return unmarshallerHandler.getResult();
    }

    public static Object unmarshal(InputSource inputSource, String contextPath,
            String namespace) throws JAXBException, SAXException,
                    ParserConfigurationException, IOException {
        UnmarshallerHandler unmarshallerHandler = JAXBContext
                .newInstance(contextPath).createUnmarshaller()
                .getUnmarshallerHandler();
        XMLFilter xmlFilter = new NamespaceFilter(namespace);
        xmlFilter.setParent(
                SAXParserFactory.newInstance().newSAXParser().getXMLReader());
        xmlFilter.setContentHandler(unmarshallerHandler);
        xmlFilter.parse(inputSource);
        return unmarshallerHandler.getResult();
    }

    public static void marshal(Object object, File file, String contextPath)
            throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(contextPath);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(object, file);
    }
}