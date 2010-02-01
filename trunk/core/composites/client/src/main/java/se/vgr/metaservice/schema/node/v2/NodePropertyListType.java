
package se.vgr.metaservice.schema.node.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NodePropertyListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NodePropertyListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NodeProperty" type="{urn:Node.schema.metaservice.vgr.se:v2}NodePropertyType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodePropertyListType", propOrder = {
    "nodeProperty"
})
public class NodePropertyListType {

    @XmlElement(name = "NodeProperty", required = true)
    protected List<NodePropertyType> nodeProperty;

    /**
     * Gets the value of the nodeProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodePropertyType }
     * 
     * 
     */
    public List<NodePropertyType> getNodeProperty() {
        if (nodeProperty == null) {
            nodeProperty = new ArrayList<NodePropertyType>();
        }
        return this.nodeProperty;
    }

}
