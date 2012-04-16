/**
 * TFRI_ElectronicMapData_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFRI_ElectronicMapData_Ety  extends com.ehl.webservice.fw.TFRIDataBase_Ety  implements java.io.Serializable {
    private java.lang.String JAMRELIEVESINFO;

    public TFRI_ElectronicMapData_Ety() {
    }

    public TFRI_ElectronicMapData_Ety(
           int ID,
           java.lang.String ROADNAME,
           java.lang.String STARTMILEAGE,
           java.lang.String ENDMILEAGE,
           int DIRECTION,
           int ROADTYPE,
           java.util.Calendar BEGINTIME,
           java.util.Calendar ENDTIME,
           int TRAFFICSTATUS,
           float SPEED,
           int FLOW,
           float OCCUPY,
           java.lang.String JAMROADACCESS,
           java.util.Calendar JAMHAPPENTIME,
           java.util.Calendar JAMSUPPORTTIME,
           java.lang.String AREATROOP,
           java.lang.String REMARK,
           java.lang.String JAMRELIEVESINFO) {
        super(
            ID,
            ROADNAME,
            STARTMILEAGE,
            ENDMILEAGE,
            DIRECTION,
            ROADTYPE,
            BEGINTIME,
            ENDTIME,
            TRAFFICSTATUS,
            SPEED,
            FLOW,
            OCCUPY,
            JAMROADACCESS,
            JAMHAPPENTIME,
            JAMSUPPORTTIME,
            AREATROOP,
            REMARK);
        this.JAMRELIEVESINFO = JAMRELIEVESINFO;
    }


    /**
     * Gets the JAMRELIEVESINFO value for this TFRI_ElectronicMapData_Ety.
     * 
     * @return JAMRELIEVESINFO
     */
    public java.lang.String getJAMRELIEVESINFO() {
        return JAMRELIEVESINFO;
    }


    /**
     * Sets the JAMRELIEVESINFO value for this TFRI_ElectronicMapData_Ety.
     * 
     * @param JAMRELIEVESINFO
     */
    public void setJAMRELIEVESINFO(java.lang.String JAMRELIEVESINFO) {
        this.JAMRELIEVESINFO = JAMRELIEVESINFO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFRI_ElectronicMapData_Ety)) return false;
        TFRI_ElectronicMapData_Ety other = (TFRI_ElectronicMapData_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.JAMRELIEVESINFO==null && other.getJAMRELIEVESINFO()==null) || 
             (this.JAMRELIEVESINFO!=null &&
              this.JAMRELIEVESINFO.equals(other.getJAMRELIEVESINFO())));
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
        if (getJAMRELIEVESINFO() != null) {
            _hashCode += getJAMRELIEVESINFO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFRI_ElectronicMapData_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ElectronicMapData_Ety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("JAMRELIEVESINFO");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "JAMRELIEVESINFO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
