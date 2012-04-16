/**
 * TFPM_MergeShowResult_RoadId_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFPM_MergeShowResult_RoadId_Ety  extends com.ehl.webservice.fw.TFPM_MergeShowResult_Ety  implements java.io.Serializable {
    private int FWID;

    public TFPM_MergeShowResult_RoadId_Ety() {
    }

    public TFPM_MergeShowResult_RoadId_Ety(
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
           int DATAMODE,
           int FWID) {
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
        this.FWID = FWID;
    }


    /**
     * Gets the FWID value for this TFPM_MergeShowResult_RoadId_Ety.
     * 
     * @return FWID
     */
    public int getFWID() {
        return FWID;
    }


    /**
     * Sets the FWID value for this TFPM_MergeShowResult_RoadId_Ety.
     * 
     * @param FWID
     */
    public void setFWID(int FWID) {
        this.FWID = FWID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFPM_MergeShowResult_RoadId_Ety)) return false;
        TFPM_MergeShowResult_RoadId_Ety other = (TFPM_MergeShowResult_RoadId_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.FWID == other.getFWID();
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
        _hashCode += getFWID();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFPM_MergeShowResult_RoadId_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_RoadId_Ety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FWID");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "FWID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
