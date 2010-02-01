
package se.vgr.metaservice.schema.request.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.vgr.metaservice.schema.request.v1 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.vgr.metaservice.schema.request.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OptionsType.IncludeSourceIds }
     * 
     */
    public OptionsType.IncludeSourceIds createOptionsTypeIncludeSourceIds() {
        return new OptionsType.IncludeSourceIds();
    }

    /**
     * Create an instance of {@link OptionsType.IncludeSourceIds.Entry }
     * 
     */
    public OptionsType.IncludeSourceIds.Entry createOptionsTypeIncludeSourceIdsEntry() {
        return new OptionsType.IncludeSourceIds.Entry();
    }

    /**
     * Create an instance of {@link OptionsType }
     * 
     */
    public OptionsType createOptionsType() {
        return new OptionsType();
    }

    /**
     * Create an instance of {@link IncludeSourceIdsListType }
     * 
     */
    public IncludeSourceIdsListType createIncludeSourceIdsListType() {
        return new IncludeSourceIdsListType();
    }

    /**
     * Create an instance of {@link IdentificationType }
     * 
     */
    public IdentificationType createIdentificationType() {
        return new IdentificationType();
    }

}
