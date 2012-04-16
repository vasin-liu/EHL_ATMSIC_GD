/**
 * TFRI_AccidentData_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFRI_AccidentData_Ety  extends com.ehl.webservice.fw.Base_Ety  implements java.io.Serializable {
    private java.lang.String ROADNAME;

    private int DIRECTION;

    private int STARTMILEAGE;

    private int ENDMILEAGE;

    private java.util.Calendar BEGINTIME;

    private java.util.Calendar ENDTIME;

    private java.lang.String AREATROOP;

    private java.lang.String CONTENT;

    private int CLOSEDLANENUM;

    private java.util.Calendar CREATETIME;

    private java.lang.String REMARK;

    public TFRI_AccidentData_Ety() {
    }

    public TFRI_AccidentData_Ety(
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
            ID);
        this.ROADNAME = ROADNAME;
        this.DIRECTION = DIRECTION;
        this.STARTMILEAGE = STARTMILEAGE;
        this.ENDMILEAGE = ENDMILEAGE;
        this.BEGINTIME = BEGINTIME;
        this.ENDTIME = ENDTIME;
        this.AREATROOP = AREATROOP;
        this.CONTENT = CONTENT;
        this.CLOSEDLANENUM = CLOSEDLANENUM;
        this.CREATETIME = CREATETIME;
        this.REMARK = REMARK;
    }


    /**
     * Gets the ROADNAME value for this TFRI_AccidentData_Ety.
     * 
     * @return ROADNAME
     */
    public java.lang.String getROADNAME() {
        return ROADNAME;
    }


    /**
     * Sets the ROADNAME value for this TFRI_AccidentData_Ety.
     * 
     * @param ROADNAME
     */
    public void setROADNAME(java.lang.String ROADNAME) {
        this.ROADNAME = ROADNAME;
    }


    /**
     * Gets the DIRECTION value for this TFRI_AccidentData_Ety.
     * 
     * @return DIRECTION
     */
    public int getDIRECTION() {
        return DIRECTION;
    }


    /**
     * Sets the DIRECTION value for this TFRI_AccidentData_Ety.
     * 
     * @param DIRECTION
     */
    public void setDIRECTION(int DIRECTION) {
        this.DIRECTION = DIRECTION;
    }


    /**
     * Gets the STARTMILEAGE value for this TFRI_AccidentData_Ety.
     * 
     * @return STARTMILEAGE
     */
    public int getSTARTMILEAGE() {
        return STARTMILEAGE;
    }


    /**
     * Sets the STARTMILEAGE value for this TFRI_AccidentData_Ety.
     * 
     * @param STARTMILEAGE
     */
    public void setSTARTMILEAGE(int STARTMILEAGE) {
        this.STARTMILEAGE = STARTMILEAGE;
    }


    /**
     * Gets the ENDMILEAGE value for this TFRI_AccidentData_Ety.
     * 
     * @return ENDMILEAGE
     */
    public int getENDMILEAGE() {
        return ENDMILEAGE;
    }


    /**
     * Sets the ENDMILEAGE value for this TFRI_AccidentData_Ety.
     * 
     * @param ENDMILEAGE
     */
    public void setENDMILEAGE(int ENDMILEAGE) {
        this.ENDMILEAGE = ENDMILEAGE;
    }


    /**
     * Gets the BEGINTIME value for this TFRI_AccidentData_Ety.
     * 
     * @return BEGINTIME
     */
    public java.util.Calendar getBEGINTIME() {
        return BEGINTIME;
    }


    /**
     * Sets the BEGINTIME value for this TFRI_AccidentData_Ety.
     * 
     * @param BEGINTIME
     */
    public void setBEGINTIME(java.util.Calendar BEGINTIME) {
        this.BEGINTIME = BEGINTIME;
    }


    /**
     * Gets the ENDTIME value for this TFRI_AccidentData_Ety.
     * 
     * @return ENDTIME
     */
    public java.util.Calendar getENDTIME() {
        return ENDTIME;
    }


    /**
     * Sets the ENDTIME value for this TFRI_AccidentData_Ety.
     * 
     * @param ENDTIME
     */
    public void setENDTIME(java.util.Calendar ENDTIME) {
        this.ENDTIME = ENDTIME;
    }


    /**
     * Gets the AREATROOP value for this TFRI_AccidentData_Ety.
     * 
     * @return AREATROOP
     */
    public java.lang.String getAREATROOP() {
        return AREATROOP;
    }


    /**
     * Sets the AREATROOP value for this TFRI_AccidentData_Ety.
     * 
     * @param AREATROOP
     */
    public void setAREATROOP(java.lang.String AREATROOP) {
        this.AREATROOP = AREATROOP;
    }


    /**
     * Gets the CONTENT value for this TFRI_AccidentData_Ety.
     * 
     * @return CONTENT
     */
    public java.lang.String getCONTENT() {
        return CONTENT;
    }


    /**
     * Sets the CONTENT value for this TFRI_AccidentData_Ety.
     * 
     * @param CONTENT
     */
    public void setCONTENT(java.lang.String CONTENT) {
        this.CONTENT = CONTENT;
    }


    /**
     * Gets the CLOSEDLANENUM value for this TFRI_AccidentData_Ety.
     * 
     * @return CLOSEDLANENUM
     */
    public int getCLOSEDLANENUM() {
        return CLOSEDLANENUM;
    }


    /**
     * Sets the CLOSEDLANENUM value for this TFRI_AccidentData_Ety.
     * 
     * @param CLOSEDLANENUM
     */
    public void setCLOSEDLANENUM(int CLOSEDLANENUM) {
        this.CLOSEDLANENUM = CLOSEDLANENUM;
    }


    /**
     * Gets the CREATETIME value for this TFRI_AccidentData_Ety.
     * 
     * @return CREATETIME
     */
    public java.util.Calendar getCREATETIME() {
        return CREATETIME;
    }


    /**
     * Sets the CREATETIME value for this TFRI_AccidentData_Ety.
     * 
     * @param CREATETIME
     */
    public void setCREATETIME(java.util.Calendar CREATETIME) {
        this.CREATETIME = CREATETIME;
    }


    /**
     * Gets the REMARK value for this TFRI_AccidentData_Ety.
     * 
     * @return REMARK
     */
    public java.lang.String getREMARK() {
        return REMARK;
    }


    /**
     * Sets the REMARK value for this TFRI_AccidentData_Ety.
     * 
     * @param REMARK
     */
    public void setREMARK(java.lang.String REMARK) {
        this.REMARK = REMARK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFRI_AccidentData_Ety)) return false;
        TFRI_AccidentData_Ety other = (TFRI_AccidentData_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ROADNAME==null && other.getROADNAME()==null) || 
             (this.ROADNAME!=null &&
              this.ROADNAME.equals(other.getROADNAME()))) &&
            this.DIRECTION == other.getDIRECTION() &&
            this.STARTMILEAGE == other.getSTARTMILEAGE() &&
            this.ENDMILEAGE == other.getENDMILEAGE() &&
            ((this.BEGINTIME==null && other.getBEGINTIME()==null) || 
             (this.BEGINTIME!=null &&
              this.BEGINTIME.equals(other.getBEGINTIME()))) &&
            ((this.ENDTIME==null && other.getENDTIME()==null) || 
             (this.ENDTIME!=null &&
              this.ENDTIME.equals(other.getENDTIME()))) &&
            ((this.AREATROOP==null && other.getAREATROOP()==null) || 
             (this.AREATROOP!=null &&
              this.AREATROOP.equals(other.getAREATROOP()))) &&
            ((this.CONTENT==null && other.getCONTENT()==null) || 
             (this.CONTENT!=null &&
              this.CONTENT.equals(other.getCONTENT()))) &&
            this.CLOSEDLANENUM == other.getCLOSEDLANENUM() &&
            ((this.CREATETIME==null && other.getCREATETIME()==null) || 
             (this.CREATETIME!=null &&
              this.CREATETIME.equals(other.getCREATETIME()))) &&
            ((this.REMARK==null && other.getREMARK()==null) || 
             (this.REMARK!=null &&
              this.REMARK.equals(other.getREMARK())));
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
        if (getROADNAME() != null) {
            _hashCode += getROADNAME().hashCode();
        }
        _hashCode += getDIRECTION();
        _hashCode += getSTARTMILEAGE();
        _hashCode += getENDMILEAGE();
        if (getBEGINTIME() != null) {
            _hashCode += getBEGINTIME().hashCode();
        }
        if (getENDTIME() != null) {
            _hashCode += getENDTIME().hashCode();
        }
        if (getAREATROOP() != null) {
            _hashCode += getAREATROOP().hashCode();
        }
        if (getCONTENT() != null) {
            _hashCode += getCONTENT().hashCode();
        }
        _hashCode += getCLOSEDLANENUM();
        if (getCREATETIME() != null) {
            _hashCode += getCREATETIME().hashCode();
        }
        if (getREMARK() != null) {
            _hashCode += getREMARK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFRI_AccidentData_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_AccidentData_Ety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROADNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ROADNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DIRECTION");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "DIRECTION"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STARTMILEAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "STARTMILEAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ENDMILEAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ENDMILEAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BEGINTIME");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "BEGINTIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ENDTIME");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ENDTIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AREATROOP");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "AREATROOP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTENT");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "CONTENT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLOSEDLANENUM");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "CLOSEDLANENUM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CREATETIME");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "CREATETIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REMARK");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "REMARK"));
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
