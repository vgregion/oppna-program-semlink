
package se.vgr.metaservice.schema.document.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FileDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileDocumentType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:Document.schema.metaservice.vgr.se:v1}DocumentType">
 *       &lt;sequence>
 *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputStream" type="{urn:Document.schema.metaservice.vgr.se:v1}InputStreamType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileDocumentType", propOrder = {
    "encoding",
    "filename",
    "inputStream"
})
public class FileDocumentType
    extends DocumentType
{

    protected String encoding;
    protected String filename;
    protected InputStreamType inputStream;

    /**
     * Gets the value of the encoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the inputStream property.
     * 
     * @return
     *     possible object is
     *     {@link InputStreamType }
     *     
     */
    public InputStreamType getInputStream() {
        return inputStream;
    }

    /**
     * Sets the value of the inputStream property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputStreamType }
     *     
     */
    public void setInputStream(InputStreamType value) {
        this.inputStream = value;
    }

}
