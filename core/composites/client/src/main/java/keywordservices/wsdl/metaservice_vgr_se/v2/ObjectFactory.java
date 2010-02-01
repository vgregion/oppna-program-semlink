
package keywordservices.wsdl.metaservice_vgr_se.v2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import se.vgr.metaservice.schema.response.v1.NodeListResponseObjectType;
import se.vgr.metaservice.schema.response.v1.ResponseObjectType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the keywordservices.wsdl.metaservice_vgr_se.v2 package. 
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

    private final static QName _BookmarkKeywordsResponse_QNAME = new QName("urn:KeywordServices:wsdl:metaservice.vgr.se:v2", "BookmarkKeywordsResponse");
    private final static QName _GetKeywordsResponse_QNAME = new QName("urn:KeywordServices:wsdl:metaservice.vgr.se:v2", "GetKeywordsResponse");
    private final static QName _TagKeywordsResponse_QNAME = new QName("urn:KeywordServices:wsdl:metaservice.vgr.se:v2", "TagKeywordsResponse");
    private final static QName _GetNodeByInternalIdResponse_QNAME = new QName("urn:KeywordServices:wsdl:metaservice.vgr.se:v2", "GetNodeByInternalIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: keywordservices.wsdl.metaservice_vgr_se.v2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TagKeywordsRequest }
     * 
     */
    public TagKeywordsRequest createTagKeywordsRequest() {
        return new TagKeywordsRequest();
    }

    /**
     * Create an instance of {@link BookmarkKeywordsRequest }
     * 
     */
    public BookmarkKeywordsRequest createBookmarkKeywordsRequest() {
        return new BookmarkKeywordsRequest();
    }

    /**
     * Create an instance of {@link GetNodeByInternalIdRequest }
     * 
     */
    public GetNodeByInternalIdRequest createGetNodeByInternalIdRequest() {
        return new GetNodeByInternalIdRequest();
    }

    /**
     * Create an instance of {@link GetKeywordsRequest }
     * 
     */
    public GetKeywordsRequest createGetKeywordsRequest() {
        return new GetKeywordsRequest();
    }

    /**
     * Create an instance of {@link TagKeywordsRequest.KeywordIds }
     * 
     */
    public TagKeywordsRequest.KeywordIds createTagKeywordsRequestKeywordIds() {
        return new TagKeywordsRequest.KeywordIds();
    }

    /**
     * Create an instance of {@link BookmarkKeywordsRequest.KeywordIds }
     * 
     */
    public BookmarkKeywordsRequest.KeywordIds createBookmarkKeywordsRequestKeywordIds() {
        return new BookmarkKeywordsRequest.KeywordIds();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:KeywordServices:wsdl:metaservice.vgr.se:v2", name = "BookmarkKeywordsResponse")
    public JAXBElement<ResponseObjectType> createBookmarkKeywordsResponse(ResponseObjectType value) {
        return new JAXBElement<ResponseObjectType>(_BookmarkKeywordsResponse_QNAME, ResponseObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeListResponseObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:KeywordServices:wsdl:metaservice.vgr.se:v2", name = "GetKeywordsResponse")
    public JAXBElement<NodeListResponseObjectType> createGetKeywordsResponse(NodeListResponseObjectType value) {
        return new JAXBElement<NodeListResponseObjectType>(_GetKeywordsResponse_QNAME, NodeListResponseObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:KeywordServices:wsdl:metaservice.vgr.se:v2", name = "TagKeywordsResponse")
    public JAXBElement<ResponseObjectType> createTagKeywordsResponse(ResponseObjectType value) {
        return new JAXBElement<ResponseObjectType>(_TagKeywordsResponse_QNAME, ResponseObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeListResponseObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:KeywordServices:wsdl:metaservice.vgr.se:v2", name = "GetNodeByInternalIdResponse")
    public JAXBElement<NodeListResponseObjectType> createGetNodeByInternalIdResponse(NodeListResponseObjectType value) {
        return new JAXBElement<NodeListResponseObjectType>(_GetNodeByInternalIdResponse_QNAME, NodeListResponseObjectType.class, null, value);
    }

}
