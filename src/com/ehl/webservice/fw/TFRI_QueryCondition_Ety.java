/**
 * TFRI_QueryCondition_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFRI_QueryCondition_Ety  implements java.io.Serializable {
    private java.lang.String ROADNAME;

    private int DIRECTION;

    private java.util.Calendar BEGINTIME;

    private java.util.Calendar ENDTIME;

    private java.lang.String startMileage;

    private java.lang.String endMileage;

    private java.lang.String areaTroop;

    private int trafficStatus;

    private int dataSource;

    public TFRI_QueryCondition_Ety() {
    }

    public TFRI_QueryCondition_Ety(
           java.lang.String ROADNAME,
           int DIRECTION,
           java.util.Calendar BEGINTIME,
           java.util.Calendar ENDTIME,
           java.lang.String startMileage,
           java.lang.String endMileage,
           java.lang.String areaTroop,
           int trafficStatus,
           int dataSource) {
           this.ROADNAME = ROADNAME;
           this.DIRECTION = DIRECTION;
           this.BEGINTIME = BEGINTIME;
           this.ENDTIME = ENDTIME;
           this.startMileage = startMileage;
           this.endMileage = endMileage;
           this.areaTroop = areaTroop;
           this.trafficStatus = trafficStatus;
           this.dataSource = dataSource;
    }


    /**
     * Gets the ROADNAME value for this TFRI_QueryCondition_Ety.
     * 
     * @return ROADNAME
     */
    public java.lang.String getROADNAME() {
        return ROADNAME;
    }


    /**
     * Sets the ROADNAME value for this TFRI_QueryCondition_Ety.
     * 
     * @param ROADNAME
     */
    public void setROADNAME(java.lang.String ROADNAME) {
        this.ROADNAME = ROADNAME;
    }


    /**
     * Gets the DIRECTION value for this TFRI_QueryCondition_Ety.
     * 
     * @return DIRECTION
     */
    public int getDIRECTION() {
        return DIRECTION;
    }


    /**
     * Sets the DIRECTION value for this TFRI_QueryCondition_Ety.
     * 
     * @param DIRECTION
     */
    public void setDIRECTION(int DIRECTION) {
        this.DIRECTION = DIRECTION;
    }


    /**
     * Gets the BEGINTIME value for this TFRI_QueryCondition_Ety.
     * 
     * @return BEGINTIME
     */
    public java.util.Calendar getBEGINTIME() {
        return BEGINTIME;
    }


    /**
     * Sets the BEGINTIME value for this TFRI_QueryCondition_Ety.
     * 
     * @param BEGINTIME
     */
    public void setBEGINTIME(java.util.Calendar BEGINTIME) {
        this.BEGINTIME = BEGINTIME;
    }


    /**
     * Gets the ENDTIME value for this TFRI_QueryCondition_Ety.
     * 
     * @return ENDTIME
     */
    public java.util.Calendar getENDTIME() {
        return ENDTIME;
    }


    /**
     * Sets the ENDTIME value for this TFRI_QueryCondition_Ety.
     * 
     * @param ENDTIME
     */
    public void setENDTIME(java.util.Calendar ENDTIME) {
        this.ENDTIME = ENDTIME;
    }


    /**
     * Gets the startMileage value for this TFRI_QueryCondition_Ety.
     * 
     * @return startMileage
     */
    public java.lang.String getStartMileage() {
        return startMileage;
    }


    /**
     * Sets the startMileage value for this TFRI_QueryCondition_Ety.
     * 
     * @param startMileage
     */
    public void setStartMileage(java.lang.String startMileage) {
        this.startMileage = startMileage;
    }


    /**
     * Gets the endMileage value for this TFRI_QueryCondition_Ety.
     * 
     * @return endMileage
     */
    public java.lang.String getEndMileage() {
        return endMileage;
    }


    /**
     * Sets the endMileage value for this TFRI_QueryCondition_Ety.
     * 
     * @param endMileage
     */
    public void setEndMileage(java.lang.String endMileage) {
        this.endMileage = endMileage;
    }


    /**
     * Gets the areaTroop value for this TFRI_QueryCondition_Ety.
     * 
     * @return areaTroop
     */
    public java.lang.String getAreaTroop() {
        return areaTroop;
    }


    /**
     * Sets the areaTroop value for this TFRI_QueryCondition_Ety.
     * 
     * @param areaTroop
     */
    public void setAreaTroop(java.lang.String areaTroop) {
        this.areaTroop = areaTroop;
    }


    /**
     * Gets the trafficStatus value for this TFRI_QueryCondition_Ety.
     * 
     * @return trafficStatus
     */
    public int getTrafficStatus() {
        return trafficStatus;
    }


    /**
     * Sets the trafficStatus value for this TFRI_QueryCondition_Ety.
     * 
     * @param trafficStatus
     */
    public void setTrafficStatus(int trafficStatus) {
        this.trafficStatus = trafficStatus;
    }


    /**
     * Gets the dataSource value for this TFRI_QueryCondition_Ety.
     * 
     * @return dataSource
     */
    public int getDataSource() {
        return dataSource;
    }


    /**
     * Sets the dataSource value for this TFRI_QueryCondition_Ety.
     * 
     * @param dataSource
     */
    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFRI_QueryCondition_Ety)) return false;
        TFRI_QueryCondition_Ety other = (TFRI_QueryCondition_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ROADNAME==null && other.getROADNAME()==null) || 
             (this.ROADNAME!=null &&
              this.ROADNAME.equals(other.getROADNAME()))) &&
            this.DIRECTION == other.getDIRECTION() &&
            ((this.BEGINTIME==null && other.getBEGINTIME()==null) || 
             (this.BEGINTIME!=null &&
              this.BEGINTIME.equals(other.getBEGINTIME()))) &&
            ((this.ENDTIME==null && other.getENDTIME()==null) || 
             (this.ENDTIME!=null &&
              this.ENDTIME.equals(other.getENDTIME()))) &&
            ((this.startMileage==null && other.getStartMileage()==null) || 
             (this.startMileage!=null &&
              this.startMileage.equals(other.getStartMileage()))) &&
            ((this.endMileage==null && other.getEndMileage()==null) || 
             (this.endMileage!=null &&
              this.endMileage.equals(other.getEndMileage()))) &&
            ((this.areaTroop==null && other.getAreaTroop()==null) || 
             (this.areaTroop!=null &&
              this.areaTroop.equals(other.getAreaTroop()))) &&
            this.trafficStatus == other.getTrafficStatus() &&
            this.dataSource == other.getDataSource();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getROADNAME() != null) {
            _hashCode += getROADNAME().hashCode();
        }
        _hashCode += getDIRECTION();
        if (getBEGINTIME() != null) {
            _hashCode += getBEGINTIME().hashCode();
        }
        if (getENDTIME() != null) {
            _hashCode += getENDTIME().hashCode();
        }
        if (getStartMileage() != null) {
            _hashCode += getStartMileage().hashCode();
        }
        if (getEndMileage() != null) {
            _hashCode += getEndMileage().hashCode();
        }
        if (getAreaTroop() != null) {
            _hashCode += getAreaTroop().hashCode();
        }
        _hashCode += getTrafficStatus();
        _hashCode += getDataSource();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFRI_QueryCondition_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"));
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
        elemField.setFieldName("startMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "StartMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "EndMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaTroop");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "AreaTroop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trafficStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TrafficStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataSource");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "DataSource"));
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
