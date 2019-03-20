//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.21 at 12:16:54 AM EET 
//


package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.xml.parsing.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransitionLabelKind.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransitionLabelKind"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="comments"/&gt;
 *     &lt;enumeration value="select"/&gt;
 *     &lt;enumeration value="guard"/&gt;
 *     &lt;enumeration value="synchronisation"/&gt;
 *     &lt;enumeration value="assignment"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TransitionLabelKind")
@XmlEnum
public enum TransitionLabelKind {

    @XmlEnumValue("comments")
    COMMENTS("comments"),
    @XmlEnumValue("select")
    SELECT("select"),
    @XmlEnumValue("guard")
    GUARD("guard"),
    @XmlEnumValue("synchronisation")
    SYNCHRONISATION("synchronisation"),
    @XmlEnumValue("assignment")
    ASSIGNMENT("assignment");
    private final String value;

    TransitionLabelKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransitionLabelKind fromValue(String v) {
        for (TransitionLabelKind c: TransitionLabelKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
