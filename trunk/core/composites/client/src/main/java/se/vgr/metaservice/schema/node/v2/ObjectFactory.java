
package se.vgr.metaservice.schema.node.v2;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.vgr.metaservice.schema.node.v2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.vgr.metaservice.schema.node.v2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SynonymsListType }
     * 
     */
    public SynonymsListType createSynonymsListType() {
        return new SynonymsListType();
    }

    /**
     * Create an instance of {@link UserStatusListType }
     * 
     */
    public UserStatusListType createUserStatusListType() {
        return new UserStatusListType();
    }

    /**
     * Create an instance of {@link NodeListType }
     * 
     */
    public NodeListType createNodeListType() {
        return new NodeListType();
    }

    /**
     * Create an instance of {@link NodePropertyType }
     * 
     */
    public NodePropertyType createNodePropertyType() {
        return new NodePropertyType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link NodePropertyListType }
     * 
     */
    public NodePropertyListType createNodePropertyListType() {
        return new NodePropertyListType();
    }

}
