
package keywordservices.wsdl.metaservice_vgr_se.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import se.vgr.metaservice.schema.request.v1.IdentificationType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identification" type="{urn:Request.schema.metaservice.vgr.se:v1}IdentificationType"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keywordIds">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="keywordIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identification",
    "requestId",
    "userId",
    "keywordIds"
})
@XmlRootElement(name = "BookmarkKeywordsRequest")
public class BookmarkKeywordsRequest {

    @XmlElement(required = true)
    protected IdentificationType identification;
    @XmlElement(required = true)
    protected String requestId;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected BookmarkKeywordsRequest.KeywordIds keywordIds;

    /**
     * Gets the value of the identification property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationType }
     *     
     */
    public IdentificationType getIdentification() {
        return identification;
    }

    /**
     * Sets the value of the identification property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationType }
     *     
     */
    public void setIdentification(IdentificationType value) {
        this.identification = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the keywordIds property.
     * 
     * @return
     *     possible object is
     *     {@link BookmarkKeywordsRequest.KeywordIds }
     *     
     */
    public BookmarkKeywordsRequest.KeywordIds getKeywordIds() {
        return keywordIds;
    }

    /**
     * Sets the value of the keywordIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookmarkKeywordsRequest.KeywordIds }
     *     
     */
    public void setKeywordIds(BookmarkKeywordsRequest.KeywordIds value) {
        this.keywordIds = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="keywordIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "keywordIds"
    })
    public static class KeywordIds {

        @XmlElement(required = true)
        protected List<String> keywordIds;

        /**
         * Gets the value of the keywordIds property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the keywordIds property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKeywordIds().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getKeywordIds() {
            if (keywordIds == null) {
                keywordIds = new ArrayList<String>();
            }
            return this.keywordIds;
        }

    }

}
