/**
 * TFRI_MergeDataResultQueryCondition_Ety.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class TFRI_MergeDataResultQueryCondition_Ety  implements java.io.Serializable {
    private java.lang.String roadName;

    private java.lang.String startMileage;

    private java.lang.String endMileage;

    private java.util.Calendar beginTime;

    private java.util.Calendar endTime;

    private java.lang.String direction;

    private java.lang.String raodType;

    private java.lang.String areaTroop;

    private com.ehl.webservice.fw.TrafficStatus trafficStatus;

    public TFRI_MergeDataResultQueryCondition_Ety() {
    }

    public TFRI_MergeDataResultQueryCondition_Ety(
           java.lang.String roadName,
           java.lang.String startMileage,
           java.lang.String endMileage,
           java.util.Calendar beginTime,
           java.util.Calendar endTime,
           java.lang.String direction,
           java.lang.String raodType,
           java.lang.String areaTroop,
           com.ehl.webservice.fw.TrafficStatus trafficStatus) {
           this.roadName = roadName;
           this.startMileage = startMileage;
           this.endMileage = endMileage;
           this.beginTime = beginTime;
           this.endTime = endTime;
           this.direction = direction;
           this.raodType = raodType;
           this.areaTroop = areaTroop;
           this.trafficStatus = trafficStatus;
    }


    /**
     * Gets the roadName value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return roadName
     */
    public java.lang.String getRoadName() {
        return roadName;
    }


    /**
     * Sets the roadName value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param roadName
     */
    public void setRoadName(java.lang.String roadName) {
        this.roadName = roadName;
    }


    /**
     * Gets the startMileage value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return startMileage
     */
    public java.lang.String getStartMileage() {
        return startMileage;
    }


    /**
     * Sets the startMileage value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param startMileage
     */
    public void setStartMileage(java.lang.String startMileage) {
        this.startMileage = startMileage;
    }


    /**
     * Gets the endMileage value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return endMileage
     */
    public java.lang.String getEndMileage() {
        return endMileage;
    }


    /**
     * Sets the endMileage value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param endMileage
     */
    public void setEndMileage(java.lang.String endMileage) {
        this.endMileage = endMileage;
    }


    /**
     * Gets the beginTime value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return beginTime
     */
    public java.util.Calendar getBeginTime() {
        return beginTime;
    }


    /**
     * Sets the beginTime value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param beginTime
     */
    public void setBeginTime(java.util.Calendar beginTime) {
        this.beginTime = beginTime;
    }


    /**
     * Gets the endTime value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return endTime
     */
    public java.util.Calendar getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param endTime
     */
    public void setEndTime(java.util.Calendar endTime) {
        this.endTime = endTime;
    }


    /**
     * Gets the direction value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return direction
     */
    public java.lang.String getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param direction
     */
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }


    /**
     * Gets the raodType value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return raodType
     */
    public java.lang.String getRaodType() {
        return raodType;
    }


    /**
     * Sets the raodType value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param raodType
     */
    public void setRaodType(java.lang.String raodType) {
        this.raodType = raodType;
    }


    /**
     * Gets the areaTroop value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return areaTroop
     */
    public java.lang.String getAreaTroop() {
        return areaTroop;
    }


    /**
     * Sets the areaTroop value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param areaTroop
     */
    public void setAreaTroop(java.lang.String areaTroop) {
        this.areaTroop = areaTroop;
    }


    /**
     * Gets the trafficStatus value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @return trafficStatus
     */
    public com.ehl.webservice.fw.TrafficStatus getTrafficStatus() {
        return trafficStatus;
    }


    /**
     * Sets the trafficStatus value for this TFRI_MergeDataResultQueryCondition_Ety.
     * 
     * @param trafficStatus
     */
    public void setTrafficStatus(com.ehl.webservice.fw.TrafficStatus trafficStatus) {
        this.trafficStatus = trafficStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TFRI_MergeDataResultQueryCondition_Ety)) return false;
        TFRI_MergeDataResultQueryCondition_Ety other = (TFRI_MergeDataResultQueryCondition_Ety) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.roadName==null && other.getRoadName()==null) || 
             (this.roadName!=null &&
              this.roadName.equals(other.getRoadName()))) &&
            ((this.startMileage==null && other.getStartMileage()==null) || 
             (this.startMileage!=null &&
              this.startMileage.equals(other.getStartMileage()))) &&
            ((this.endMileage==null && other.getEndMileage()==null) || 
             (this.endMileage!=null &&
              this.endMileage.equals(other.getEndMileage()))) &&
            ((this.beginTime==null && other.getBeginTime()==null) || 
             (this.beginTime!=null &&
              this.beginTime.equals(other.getBeginTime()))) &&
            ((this.endTime==null && other.getEndTime()==null) || 
             (this.endTime!=null &&
              this.endTime.equals(other.getEndTime()))) &&
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.raodType==null && other.getRaodType()==null) || 
             (this.raodType!=null &&
              this.raodType.equals(other.getRaodType()))) &&
            ((this.areaTroop==null && other.getAreaTroop()==null) || 
             (this.areaTroop!=null &&
              this.areaTroop.equals(other.getAreaTroop()))) &&
            ((this.trafficStatus==null && other.getTrafficStatus()==null) || 
             (this.trafficStatus!=null &&
              this.trafficStatus.equals(other.getTrafficStatus())));
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
        if (getRoadName() != null) {
            _hashCode += getRoadName().hashCode();
        }
        if (getStartMileage() != null) {
            _hashCode += getStartMileage().hashCode();
        }
        if (getEndMileage() != null) {
            _hashCode += getEndMileage().hashCode();
        }
        if (getBeginTime() != null) {
            _hashCode += getBeginTime().hashCode();
        }
        if (getEndTime() != null) {
            _hashCode += getEndTime().hashCode();
        }
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getRaodType() != null) {
            _hashCode += getRaodType().hashCode();
        }
        if (getAreaTroop() != null) {
            _hashCode += getAreaTroop().hashCode();
        }
        if (getTrafficStatus() != null) {
            _hashCode += getTrafficStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TFRI_MergeDataResultQueryCondition_Ety.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_MergeDataResultQueryCondition_Ety"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roadName");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "RoadName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("beginTime");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "BeginTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTime");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "EndTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("raodType");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "RaodType"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TrafficStatus"));
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
