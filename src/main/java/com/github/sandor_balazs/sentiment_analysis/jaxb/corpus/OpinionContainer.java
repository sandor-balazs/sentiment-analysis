//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.10 at 10:56:43 AM CET 
//


package com.github.sandor_balazs.sentiment_analysis.jaxb.corpus;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OpinionContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpinionContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Opinion" type="{http://github.com/sandor-balazs/sentiment-analysis/jaxb/corpus}Opinion" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpinionContainer", propOrder = {
    "opinions"
})
public class OpinionContainer {

    @XmlElement(name = "Opinion", required = true)
    protected List<Opinion> opinions;

    /**
     * Gets the value of the opinions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the opinions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpinions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Opinion }
     * 
     * 
     */
    public List<Opinion> getOpinions() {
        if (opinions == null) {
            opinions = new ArrayList<Opinion>();
        }
        return this.opinions;
    }

}
