/**
 * WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub extends org.apache.axis.client.Stub implements com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[17];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTMSTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergedDataResult_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetTMSTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergedDataResult_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_MergeDataResultQueryCondition_Ety"), com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAllRealTimeTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergedDataResult_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetAllRealTimeTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergedDataResult_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetHistoryTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeHistory"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergeHistory[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetHistoryTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeHistory"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetHistoryTrafficInfoForCompress");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_MergeDataResultQueryCondition_Ety"), com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        oper.setReturnClass(byte[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetHistoryTrafficInfoForCompressResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUnityTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetUnityTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRealTimeTrafficInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "minutes"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRealTimeTrafficInfoXml");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "minutes"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoXmlResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryRealTimeTrafficSourceInfoForCompress");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "QueryRealTimeTrafficSourceInfoCondition"), com.ehl.webservice.fw.QueryRealTimeTrafficSourceInfoCondition.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        oper.setReturnClass(byte[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "QueryRealTimeTrafficSourceInfoForCompressResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetLEDData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_LedData_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFRI_LedData_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetLEDDataResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_LedData_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetElectronicMapData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_ElectronicMapData_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetElectronicMapDataResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ElectronicMapData_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetWebSitesData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_WebSitesData_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFRI_WebSitesData_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetWebSitesDataResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_WebSitesData_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAccidentData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_AccidentData_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFRI_AccidentData_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetAccidentDataResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_AccidentData_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetContructionData");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "query_Ety"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety"), com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "companySign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_ContructionData_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFRI_ContructionData_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetContructionDataResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ContructionData_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetServerTime");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        oper.setReturnClass(java.util.Calendar.class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetServerTimeResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRealTimeTrafficInfoRoadId");
        oper.setReturnType(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_RoadId_Ety"));
        oper.setReturnClass(com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoRoadIdResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_RoadId_Ety"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRealTimeTrafficInfoXmlRoadId");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoXmlRoadIdResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

    }

    public WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergedDataResult_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergedDataResult_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergedDataResult_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeHistory");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeHistory[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeHistory");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeHistory");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFPM_MergeShowResult_RoadId_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_RoadId_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_RoadId_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_AccidentData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_AccidentData_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_AccidentData_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_AccidentData_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_ContructionData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_ContructionData_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ContructionData_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ContructionData_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_ElectronicMapData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ElectronicMapData_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ElectronicMapData_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_LedData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_LedData_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_LedData_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_LedData_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "ArrayOfTFRI_WebSitesData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_WebSitesData_Ety[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_WebSitesData_Ety");
            qName2 = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_WebSitesData_Ety");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "Base_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.Base_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "DataComputingBase_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.DataComputingBase_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "QueryRealTimeTrafficSourceInfoCondition");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.QueryRealTimeTrafficSourceInfoCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergedDataResult_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergedDataResult_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeHistory");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeShowResult_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFPM_MergeShowResult_RoadId_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_AccidentData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_AccidentData_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ContructionData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_ContructionData_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_ElectronicMapData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_LedData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_LedData_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_MergeDataResultQueryCondition_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_QueryCondition_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_QueryCondition_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRI_WebSitesData_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRI_WebSitesData_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TFRIDataBase_Ety");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TFRIDataBase_Ety.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "TrafficStatus");
            cachedSerQNames.add(qName);
            cls = com.ehl.webservice.fw.TrafficStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[] getTMSTrafficInfo(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetTMSTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetTMSTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getTrafficInfo(com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[] getAllRealTimeTrafficInfo(java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetAllRealTimeTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetAllRealTimeTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergeHistory[] getHistoryTrafficInfo(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetHistoryTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetHistoryTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergeHistory[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergeHistory[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergeHistory[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public byte[] getHistoryTrafficInfoForCompress(com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetHistoryTrafficInfoForCompress");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetHistoryTrafficInfoForCompress"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (byte[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (byte[]) org.apache.axis.utils.JavaUtils.convert(_resp, byte[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getUnityTrafficInfo(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetUnityTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetUnityTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getRealTimeTrafficInfo(int minutes, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetRealTimeTrafficInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(minutes), companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getRealTimeTrafficInfoXml(int minutes, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetRealTimeTrafficInfoXml");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoXml"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(minutes), companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public byte[] queryRealTimeTrafficSourceInfoForCompress(com.ehl.webservice.fw.QueryRealTimeTrafficSourceInfoCondition query) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/QueryRealTimeTrafficSourceInfoForCompress");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "QueryRealTimeTrafficSourceInfoForCompress"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (byte[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (byte[]) org.apache.axis.utils.JavaUtils.convert(_resp, byte[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFRI_LedData_Ety[] getLEDData(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetLEDData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetLEDData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFRI_LedData_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFRI_LedData_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFRI_LedData_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[] getElectronicMapData(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetElectronicMapData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetElectronicMapData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFRI_WebSitesData_Ety[] getWebSitesData(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetWebSitesData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetWebSitesData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFRI_WebSitesData_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFRI_WebSitesData_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFRI_WebSitesData_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFRI_AccidentData_Ety[] getAccidentData(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetAccidentData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetAccidentData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFRI_AccidentData_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFRI_AccidentData_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFRI_AccidentData_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFRI_ContructionData_Ety[] getContructionData(com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety, java.lang.String companySign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetContructionData");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetContructionData"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query_Ety, companySign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFRI_ContructionData_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFRI_ContructionData_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFRI_ContructionData_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.util.Calendar getServerTime() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetServerTime");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetServerTime"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.util.Calendar) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.util.Calendar) org.apache.axis.utils.JavaUtils.convert(_resp, java.util.Calendar.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[] getRealTimeTrafficInfoRoadId() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetRealTimeTrafficInfoRoadId");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoRoadId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getRealTimeTrafficInfoXmlRoadId() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("WS.RI.TrafficTransportationInfo/GetRealTimeTrafficInfoXmlRoadId");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo", "GetRealTimeTrafficInfoXmlRoadId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
