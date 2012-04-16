/**
 * QueryRealTimeTrafficSourceInfoCondition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class QueryRealTimeTrafficSourceInfoCondition  implements java.io.Serializable {
    private java.lang.String startMileage;

    private java.lang.String endMileage;

    private java.lang.String roadName;

    private int direction;

    private int trafficstatus;

    public QueryRealTimeTrafficSourceInfoCondition() {
    }

    public QueryRealTimeTrafficSourceInfoCondition(
           java.lang.String startMileage,
           java.lang.String endMileage,
           java.lang.String roadName,
           int direction,
           int trafficstatus) {
           this.startMileage = startMileage;
           this.endMileage = endMileage;
           this.roadName = roadName;
           this.direction = direction;
           this.trafficstatus = trafficstatus;
    }


    /**
     * Gets the startMileage value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @return startMileage
     */
    public java.lang.String getStartMileage() {
        return startMileage;
    }


    /**
     * Sets the startMileage value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @param startMileage
     */
    public void setStartMileage(java.lang.String startMileage) {
        this.startMileage = startMileage;
    }


    /**
     * Gets the endMileage value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @return endMileage
     */
    public java.lang.String getEndMileage() {
        return endMileage;
    }


    /**
     * Sets the endMileage value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @param endMileage
     */
    public void setEndMileage(java.lang.String endMileage) {
        this.endMileage = endMileage;
    }


    /**
     * Gets the roadName value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @return roadName
     */
    public java.lang.String getRoadName() {
        return roadName;
    }


    /**
     * Sets the roadName value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @param roadName
     */
    public void setRoadName(java.lang.String roadName) {
        this.roadName = roadName;
    }


    /**
     * Gets the direction value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @return direction
     */
    public int getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }


    /**
     * Gets the trafficstatus value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @return trafficstatus
     */
    public int getTrafficstatus() {
        return trafficstatus;
    }


    /**
     * Sets the trafficstatus value for this QueryRealTimeTrafficSourceInfoCondition.
     * 
     * @param trafficstatus
     */
    public void setTrafficstatus(int trafficstatus) {
        this.trafficstatus = trafficstatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryRealTimeTrafficSourceInfoCondition)) return false;
        QueryRealTimeTrafficSourceInfoCondition other = (QueryRealTimeTrafficSourceInfoCondition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.startMileage==null && other.getStartMileage()==null) || 
             (this.startMileage!=null &&
              this.startMileage.equals(other.getStartMileage()))) &&
            ((this.endMileage==null && other.getEndMileage()==null) || 
             (this.endMileage!=null &&
              this.endMileage.equals(other.getEndMileage()))) &&
            ((this.roadName==null && other.getRoadName()==null) || 
             (this.roadName!=null &&
              this.roadName.equals(other.getRoadName()))) &&
            this.direction == other.getDirection() &&
            this.trafficstatus == other.getTrafficstatus();
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
        if (getStartMileage() != null) {
            _hashCode += getStartMileage().hashCode();
        }
        if (getEndMileage() != null) {
            _hashCode += getEndMileage().hashCode();
        }
        if (getRoadName() != null) {
            _hashCode += getRoadName().hashCode();
        }
        _hashCode += getDirection();
        _hashCode += getTrafficstatus();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryRealTimeTrafficSourceInfoCondition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "QueryRealTimeTrafficSourceInfoCondition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("roadName");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "RoadName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trafficstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "Trafficstatus"));
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
