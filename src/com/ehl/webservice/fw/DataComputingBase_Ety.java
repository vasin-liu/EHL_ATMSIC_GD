/**
 * DataComputingBase_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class DataComputingBase_Ety  extends com.ehl.webservice.fw.Base_Ety  implements java.io.Serializable {
    private java.lang.String ROADNAME;

    private java.lang.String STARTMILEAGE;

    private java.lang.String ENDMILEAGE;

    private int DIRECTION;

    private int ROADTYPE;

    private java.lang.String AREATROOP;

    private int DATASOURCE;

    private java.util.Calendar BEGINTIME;

    private java.util.Calendar ENDTIME;

    private int TRAFFICSTATUS;

    private float SPEED;

    private int FLOW;

    private float OCCUPY;

    private java.lang.String REMARK;

    public DataComputingBase_Ety() {
    }

    public DataComputingBase_Ety(
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
           java.lang.String REMARK) {
        super(
            ID);
        this.ROADNAME = ROADNAME;
        this.STARTMILEAGE = STARTMILEAGE;
        this.ENDMILEAGE = ENDMILEAGE;
        this.DIRECTION = DIRECTION;
        this.ROADTYPE = ROADTYPE;
        this.AREATROOP = AREATROOP;
        this.DATASOURCE = DATASOURCE;
        this.BEGINTIME = BEGINTIME;
        this.ENDTIME = ENDTIME;
        this.TRAFFICSTATUS = TRAFFICSTATUS;
        this.SPEED = SPEED;
        this.FLOW = FLOW;
        this.OCCUPY = OCCUPY;
        this.REMARK = REMARK;
    }


    /**
     * Gets the ROADNAME value for this DataComputingBase_Ety.
     * 
     * @return ROADNAME
     */
    public java.lang.String getROADNAME() {
        return ROADNAME;
    }


    /**
     * Sets the ROADNAME value for this DataComputingBase_Ety.
     * 
     * @param ROADNAME
     */
    public void setROADNAME(java.lang.String ROADNAME) {
        this.ROADNAME = ROADNAME;
    }


    /**
     * Gets the STARTMILEAGE value for this DataComputingBase_Ety.
     * 
     * @return STARTMILEAGE
     */
    public java.lang.String getSTARTMILEAGE() {
        return STARTMILEAGE;
    }


    /**
     * Sets the STARTMILEAGE value for this DataComputingBase_Ety.
     * 
     * @param STARTMILEAGE
     */
    public void setSTARTMILEAGE(java.lang.String STARTMILEAGE) {
        this.STARTMILEAGE = STARTMILEAGE;
    }


    /**
     * Gets the ENDMILEAGE value for this DataComputingBase_Ety.
     * 
     * @return ENDMILEAGE
     */
    public java.lang.String getENDMILEAGE() {
        return ENDMILEAGE;
    }


    /**
     * Sets the ENDMILEAGE value for this DataComputingBase_Ety.
     * 
     * @param ENDMILEAGE
     */
    public void setENDMILEAGE(java.lang.String ENDMILEAGE) {
        this.ENDMILEAGE = ENDMILEAGE;
    }


    /**
     * Gets the DIRECTION value for this DataComputingBase_Ety.
     * 
     * @return DIRECTION
     */
    public int getDIRECTION() {
        return DIRECTION;
    }


    /**
     * Sets the DIRECTION value for this DataComputingBase_Ety.
     * 
     * @param DIRECTION
     */
    public void setDIRECTION(int DIRECTION) {
        this.DIRECTION = DIRECTION;
    }


    /**
     * Gets the ROADTYPE value for this DataComputingBase_Ety.
     * 
     * @return ROADTYPE
     */
    public int getROADTYPE() {
        return ROADTYPE;
    }


    /**
     * Sets the ROADTYPE value for this DataComputingBase_Ety.
     * 
     * @param ROADTYPE
     */
    public void setROADTYPE(int ROADTYPE) {
        this.ROADTYPE = ROADTYPE;
    }


    /**
     * Gets the AREATROOP value for this DataComputingBase_Ety.
     * 
     * @return AREATROOP
     */
    public java.lang.String getAREATROOP() {
        return AREATROOP;
    }


    /**
     * Sets the AREATROOP value for this DataComputingBase_Ety.
     * 
     * @param AREATROOP
     */
    public void setAREATROOP(java.lang.String AREATROOP) {
        this.AREATROOP = AREATROOP;
    }


    /**
     * Gets the DATASOURCE value for this DataComputingBase_Ety.
     * 
     * @return DATASOURCE
     */
    public int getDATASOURCE() {
        return DATASOURCE;
    }


    /**
     * Sets the DATASOURCE value for this DataComputingBase_Ety.
     * 
     * @param DATASOURCE
     */
    public void setDATASOURCE(int DATASOURCE) {
        this.DATASOURCE = DATASOURCE;
    }


    /**
     * Gets the BEGINTIME value for this DataComputingBase_Ety.
     * 
     * @return BEGINTIME
     */
    public java.util.Calendar getBEGINTIME() {
        return BEGINTIME;
    }


    /**
     * Sets the BEGINTIME value for this DataComputingBase_Ety.
     * 
     * @param BEGINTIME
     */
    public void setBEGINTIME(java.util.Calendar BEGINTIME) {
        this.BEGINTIME = BEGINTIME;
    }


    /**
     * Gets the ENDTIME value for this DataComputingBase_Ety.
     * 
     * @return ENDTIME
     */
    public java.util.Calendar getENDTIME() {
        return ENDTIME;
    }


    /**
     * Sets the ENDTIME value for this DataComputingBase_Ety.
     * 
     * @param ENDTIME
     */
    public void setENDTIME(java.util.Calendar ENDTIME) {
        this.ENDTIME = ENDTIME;
    }


    /**
     * Gets the TRAFFICSTATUS value for this DataComputingBase_Ety.
     * 
     * @return TRAFFICSTATUS
     */
    public int getTRAFFICSTATUS() {
        return TRAFFICSTATUS;
    }


    /**
     * Sets the TRAFFICSTATUS value for this DataComputingBase_Ety.
     * 
     * @param TRAFFICSTATUS
     */
    public void setTRAFFICSTATUS(int TRAFFICSTATUS) {
        this.TRAFFICSTATUS = TRAFFICSTATUS;
    }


    /**
     * Gets the SPEED value for this DataComputingBase_Ety.
     * 
     * @return SPEED
     */
    public float getSPEED() {
        return SPEED;
    }


    /**
     * Sets the SPEED value for this DataComputingBase_Ety.
     * 
     * @param SPEED
     */
    public void setSPEED(float SPEED) {
        this.SPEED = SPEED;
    }


    /**
     * Gets the FLOW value for this DataComputingBase_Ety.
     * 
     * @return FLOW
     */
    public int getFLOW() {
        return FLOW;
    }


    /**
     * Sets the FLOW value for this DataComputingBase_Ety.
     * 
     * @param FLOW
     */
    public void setFLOW(int FLOW) {
        this.FLOW = FLOW;
    }


    /**
     * Gets the OCCUPY value for this DataComputingBase_Ety.
     * 
     * @return OCCUPY
     */
    public float getOCCUPY() {
        return OCCUPY;
    }


    /**
     * Sets the OCCUPY value for this DataComputingBase_Ety.
     * 
     * @param OCCUPY
     */
    public void setOCCUPY(float OCCUPY) {
        this.OCCUPY = OCCUPY;
    }


    /**
     * Gets the REMARK value for this DataComputingBase_Ety.
     * 
     * @return REMARK
     */
    public java.lang.String getREMARK() {
        return REMARK;
    }


    /**
     * Sets the REMARK value for this DataComputingBase_Ety.
     * 
     * @param REMARK
     */
    public void setREMARK(java.lang.String REMARK) {
        this.REMARK = REMARK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataComputingBase_Ety)) return false;
        DataComputingBase_Ety other = (DataComputingBase_Ety) obj;
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
            ((this.STARTMILEAGE==null && other.getSTARTMILEAGE()==null) || 
             (this.STARTMILEAGE!=null &&
              this.STARTMILEAGE.equals(other.getSTARTMILEAGE()))) &&
            ((this.ENDMILEAGE==null && other.getENDMILEAGE()==null) || 
             (this.ENDMILEAGE!=null &&
              this.ENDMILEAGE.equals(other.getENDMILEAGE()))) &&
            this.DIRECTION == other.getDIRECTION() &&
            this.ROADTYPE == other.getROADTYPE() &&
            ((this.AREATROOP==null && other.getAREATROOP()==null) || 
             (this.AREATROOP!=null &&
              this.AREATROOP.equals(other.getAREATROOP()))) &&
            this.DATASOURCE == other.getDATASOURCE() &&
            ((this.BEGINTIME==null && other.getBEGINTIME()==null) || 
             (this.BEGINTIME!=null &&
              this.BEGINTIME.equals(other.getBEGINTIME()))) &&
            ((this.ENDTIME==null && other.getENDTIME()==null) || 
             (this.ENDTIME!=null &&
              this.ENDTIME.equals(other.getENDTIME()))) &&
            this.TRAFFICSTATUS == other.getTRAFFICSTATUS() &&
            this.SPEED == other.getSPEED() &&
            this.FLOW == other.getFLOW() &&
            this.OCCUPY == other.getOCCUPY() &&
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
        if (getSTARTMILEAGE() != null) {
            _hashCode += getSTARTMILEAGE().hashCode();
        }
        if (getENDMILEAGE() != null) {
            _hashCode += getENDMILEAGE().hashCode();
        }
        _hashCode += getDIRECTION();
        _hashCode += getROADTYPE();
        if (getAREATROOP() != null) {
            _hashCode += getAREATROOP().hashCode();
        }
        _hashCode += getDATASOURCE();
        if (getBEGINTIME() != null) {
            _hashCode += getBEGINTIME().hashCode();
        }
        if (getENDTIME() != null) {
            _hashCode += getENDTIME().hashCode();
        }
        _hashCode += getTRAFFICSTATUS();
        _hashCode += new Float(getSPEED()).hashCode();
        _hashCode += getFLOW();
        _hashCode += new Float(getOCCUPY()).hashCode();
        if (getREMARK() != null) {
            _hashCode += getREMARK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataComputingBase_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "DataComputingBase_Ety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROADNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ROADNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STARTMILEAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "STARTMILEAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ENDMILEAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ENDMILEAGE"));
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
        elemField.setFieldName("ROADTYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ROADTYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("DATASOURCE");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "DATASOURCE"));
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
        elemField.setFieldName("TRAFFICSTATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TRAFFICSTATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPEED");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "SPEED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FLOW");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "FLOW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OCCUPY");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "OCCUPY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
