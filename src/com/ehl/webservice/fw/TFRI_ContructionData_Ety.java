/**
 * TFRI_ContructionData_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFRI_ContructionData_Ety  extends com.ehl.webservice.fw.TFRI_AccidentData_Ety  implements java.io.Serializable {
    public TFRI_ContructionData_Ety() {
    }

    public TFRI_ContructionData_Ety(
           int ID,
           java.lang.String ROADNAME,
           int DIRECTION,
           int STARTMILEAGE,
           int ENDMILEAGE,
           java.util.Calendar BEGINTIME,
           java.util.Calendar ENDTIME,
           java.lang.String AREATROOP,
           java.lang.String CONTENT,
           int CLOSEDLANENUM,
           java.util.Calendar CREATETIME,
           java.lang.String REMARK) {
        super(
            ID,
            ROADNAME,
            DIRECTION,
            STARTMILEAGE,
            ENDMILEAGE,
            BEGINTIME,
            ENDTIME,
            AREATROOP,
            CONTENT,
            CLOSEDLANENUM,
            CREATETIME,
            REMARK);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFRI_ContructionData_Ety)) return false;
        TFRI_ContructionData_Ety other = (TFRI_ContructionData_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFRI_ContructionData_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ContructionData_Ety"));
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
