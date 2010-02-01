
package se.vgr.metaservice.schema.node.v2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="listTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WHITELIST"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="BLACKLIST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listTypeEnum")
@XmlEnum
public enum ListTypeEnum {

    WHITELIST,
    NONE,
    BLACKLIST;

    public String value() {
        return name();
    }

    public static ListTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
