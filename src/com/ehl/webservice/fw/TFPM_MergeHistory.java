/**
 * TFPM_MergeHistory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFPM_MergeHistory  extends com.ehl.webservice.fw.TFPM_MergedDataResult_Ety  implements java.io.Serializable {
    public TFPM_MergeHistory() {
    }

    public TFPM_MergeHistory(
           int ID,
           java.lang.String ROADNAME,
           java.lang.String STARTMILEAGE,
           java.lang.String ENDMILEAGE,
           int DIRECTION,
           int ROADTYPE,
           java.lang.String AREATROOP,
           int DATASOURCE,
           java.util.Calendar BEGINTIME,
           java.util.Calendar ENDTIME,
           int TRAFFICSTATUS,
           float SPEED,
           int FLOW,
           float OCCUPY,
           java.lang.String REMARK,
           int CONFIDENCE,
           int DATAMODE) {
        super(
            ID,
            ROADNAME,
            STARTMILEAGE,
            ENDMILEAGE,
            DIRECTION,
            ROADTYPE,
            AREATROOP,
            DATASOURCE,
            BEGINTIME,
            ENDTIME,
            TRAFFICSTATUS,
            SPEED,
            FLOW,
            OCCUPY,
            REMARK,
            CONFIDENCE,
            DATAMODE);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFPM_MergeHistory)) return false;
        TFPM_MergeHistory other = (TFPM_MergeHistory) obj;
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
        new org.apache.axis.description.TypeDesc(TFPM_MergeHistory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeHistory"));
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
