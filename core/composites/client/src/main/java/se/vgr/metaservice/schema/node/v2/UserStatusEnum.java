
package se.vgr.metaservice.schema.node.v2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="userStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Tagged"/>
 *     &lt;enumeration value="Bookmarked"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "userStatusEnum")
@XmlEnum
public enum UserStatusEnum {

    @XmlEnumValue("Tagged")
    TAGGED("Tagged"),
    @XmlEnumValue("Bookmarked")
    BOOKMARKED("Bookmarked");
    private final String value;

    UserStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserStatusEnum fromValue(String v) {
        for (UserStatusEnum c: UserStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
