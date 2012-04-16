/**
 * WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public interface WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType extends
		java.rmi.Remote {
	public com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[] getTMSTrafficInfo(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getTrafficInfo(
			com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergedDataResult_Ety[] getAllRealTimeTrafficInfo(
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergeHistory[] getHistoryTrafficInfo(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public byte[] getHistoryTrafficInfoForCompress(
			com.ehl.webservice.fw.TFRI_MergeDataResultQueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getUnityTrafficInfo(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergeShowResult_Ety[] getRealTimeTrafficInfo(
			int minutes, java.lang.String companySign)
			throws java.rmi.RemoteException;

	public java.lang.String getRealTimeTrafficInfoXml(int minutes,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public byte[] queryRealTimeTrafficSourceInfoForCompress(
			com.ehl.webservice.fw.QueryRealTimeTrafficSourceInfoCondition query)
			throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFRI_LedData_Ety[] getLEDData(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFRI_ElectronicMapData_Ety[] getElectronicMapData(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFRI_WebSitesData_Ety[] getWebSitesData(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFRI_AccidentData_Ety[] getAccidentData(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFRI_ContructionData_Ety[] getContructionData(
			com.ehl.webservice.fw.TFRI_QueryCondition_Ety query_Ety,
			java.lang.String companySign) throws java.rmi.RemoteException;

	public java.util.Calendar getServerTime() throws java.rmi.RemoteException;

	public com.ehl.webservice.fw.TFPM_MergeShowResult_RoadId_Ety[] getRealTimeTrafficInfoRoadId()
			throws java.rmi.RemoteException;

	public java.lang.String getRealTimeTrafficInfoXmlRoadId()
			throws java.rmi.RemoteException;
}
