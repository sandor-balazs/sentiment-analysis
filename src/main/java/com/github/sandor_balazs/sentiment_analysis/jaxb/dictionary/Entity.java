//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.24 at 02:30:31 PM CET 
//


package com.github.sandor_balazs.sentiment_analysis.jaxb.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Entity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Entity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="phrase" type="{http://github.com/sandor-balazs/sentiment-analysis/jaxb/dictionary}Phrase" maxOccurs="unbounded"/>
 *         &lt;element name="attribute" type="{http://github.com/sandor-balazs/sentiment-analysis/jaxb/dictionary}Attribute" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entity", propOrder = {
    "phrases",
    "attributes"
})
public class Entity {

    @XmlElement(name = "phrase", required = true)
    protected List<Phrase> phrases;
    @XmlElement(name = "attribute", required = true)
    protected List<Attribute> attributes;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the phrases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phrases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhrases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Phrase }
     * 
     * 
     */
    public List<Phrase> getPhrases() {
        if (phrases == null) {
            phrases = new ArrayList<Phrase>();
        }
        return this.phrases;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     * 
     * 
     */
    public List<Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<Attribute>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
