
package se.vgr.metaservice.schema.node.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="namespaceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="internalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sourceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="synonyms" type="{urn:Node.schema.metaservice.vgr.se:v2}SynonymsListType" minOccurs="0"/>
 *         &lt;element name="nodeProperties" type="{urn:Node.schema.metaservice.vgr.se:v2}NodePropertyListType" minOccurs="0"/>
 *         &lt;element name="parents" type="{urn:Node.schema.metaservice.vgr.se:v2}NodeListType" minOccurs="0"/>
 *         &lt;element name="userStatus" type="{urn:Node.schema.metaservice.vgr.se:v2}UserStatusListType" minOccurs="0"/>
 *         &lt;element name="hasChildren" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeType", propOrder = {
    "name",
    "namespaceId",
    "internalId",
    "sourceId",
    "synonyms",
    "nodeProperties",
    "parents",
    "userStatus",
    "hasChildren"
})
public class NodeType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String namespaceId;
    @XmlElement(required = true)
    protected String internalId;
    @XmlElement(required = true)
    protected String sourceId;
    protected SynonymsListType synonyms;
    protected NodePropertyListType nodeProperties;
    protected NodeListType parents;
    protected UserStatusListType userStatus;
    protected boolean hasChildren;

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

    /**
     * Gets the value of the namespaceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespaceId() {
        return namespaceId;
    }

    /**
     * Sets the value of the namespaceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespaceId(String value) {
        this.namespaceId = value;
    }

    /**
     * Gets the value of the internalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalId() {
        return internalId;
    }

    /**
     * Sets the value of the internalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalId(String value) {
        this.internalId = value;
    }

    /**
     * Gets the value of the sourceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets the value of the sourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceId(String value) {
        this.sourceId = value;
    }

    /**
     * Gets the value of the synonyms property.
     * 
     * @return
     *     possible object is
     *     {@link SynonymsListType }
     *     
     */
    public SynonymsListType getSynonyms() {
        return synonyms;
    }

    /**
     * Sets the value of the synonyms property.
     * 
     * @param value
     *     allowed object is
     *     {@link SynonymsListType }
     *     
     */
    public void setSynonyms(SynonymsListType value) {
        this.synonyms = value;
    }

    /**
     * Gets the value of the nodeProperties property.
     * 
     * @return
     *     possible object is
     *     {@link NodePropertyListType }
     *     
     */
    public NodePropertyListType getNodeProperties() {
        return nodeProperties;
    }

    /**
     * Sets the value of the nodeProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodePropertyListType }
     *     
     */
    public void setNodeProperties(NodePropertyListType value) {
        this.nodeProperties = value;
    }

    /**
     * Gets the value of the parents property.
     * 
     * @return
     *     possible object is
     *     {@link NodeListType }
     *     
     */
    public NodeListType getParents() {
        return parents;
    }

    /**
     * Sets the value of the parents property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeListType }
     *     
     */
    public void setParents(NodeListType value) {
        this.parents = value;
    }

    /**
     * Gets the value of the userStatus property.
     * 
     * @return
     *     possible object is
     *     {@link UserStatusListType }
     *     
     */
    public UserStatusListType getUserStatus() {
        return userStatus;
    }

    /**
     * Sets the value of the userStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserStatusListType }
     *     
     */
    public void setUserStatus(UserStatusListType value) {
        this.userStatus = value;
    }

    /**
     * Gets the value of the hasChildren property.
     * 
     */
    public boolean isHasChildren() {
        return hasChildren;
    }

    /**
     * Sets the value of the hasChildren property.
     * 
     */
    public void setHasChildren(boolean value) {
        this.hasChildren = value;
    }

}
