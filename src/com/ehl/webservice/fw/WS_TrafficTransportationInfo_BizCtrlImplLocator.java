/**
 * WS_TrafficTransportationInfo_BizCtrlImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ehl.webservice.fw;

public class WS_TrafficTransportationInfo_BizCtrlImplLocator extends
		org.apache.axis.client.Service implements
		com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImpl {

	public WS_TrafficTransportationInfo_BizCtrlImplLocator() {
	}

	public WS_TrafficTransportationInfo_BizCtrlImplLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public WS_TrafficTransportationInfo_BizCtrlImplLocator(
			java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for WS_TrafficTransportationInfo_BizCtrlImplSoap
	private java.lang.String WS_TrafficTransportationInfo_BizCtrlImplSoap_address = "http://10.40.30.38/ri/WS_TrafficTransportationInfo_BizCtrlImpl.asmx";

	public java.lang.String getWS_TrafficTransportationInfo_BizCtrlImplSoapAddress() {
		return WS_TrafficTransportationInfo_BizCtrlImplSoap_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String WS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName = "WS_TrafficTransportationInfo_BizCtrlImplSoap";

	public java.lang.String getWS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName() {
		return WS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName;
	}

	public void setWS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName(
			java.lang.String name) {
		WS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName = name;
	}

	public com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType getWS_TrafficTransportationInfo_BizCtrlImplSoap()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(
					WS_TrafficTransportationInfo_BizCtrlImplSoap_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getWS_TrafficTransportationInfo_BizCtrlImplSoap(endpoint);
	}

	public com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType getWS_TrafficTransportationInfo_BizCtrlImplSoap(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_BindingStub _stub = new com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_BindingStub(
					portAddress, this);
			_stub
					.setPortName(getWS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setWS_TrafficTransportationInfo_BizCtrlImplSoapEndpointAddress(
			java.lang.String address) {
		WS_TrafficTransportationInfo_BizCtrlImplSoap_address = address;
	}

	// Use to get a proxy class for
	// WS_TrafficTransportationInfo_BizCtrlImplSoap12
	private java.lang.String WS_TrafficTransportationInfo_BizCtrlImplSoap12_address = "http://10.40.30.38/ri/WS_TrafficTransportationInfo_BizCtrlImpl.asmx";

	public java.lang.String getWS_TrafficTransportationInfo_BizCtrlImplSoap12Address() {
		return WS_TrafficTransportationInfo_BizCtrlImplSoap12_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String WS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName = "WS_TrafficTransportationInfo_BizCtrlImplSoap12";

	public java.lang.String getWS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName() {
		return WS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName;
	}

	public void setWS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName(
			java.lang.String name) {
		WS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName = name;
	}

	public com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType getWS_TrafficTransportationInfo_BizCtrlImplSoap12()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(
					WS_TrafficTransportationInfo_BizCtrlImplSoap12_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getWS_TrafficTransportationInfo_BizCtrlImplSoap12(endpoint);
	}

	public com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType getWS_TrafficTransportationInfo_BizCtrlImplSoap12(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub _stub = new com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub(
					portAddress, this);
			_stub
					.setPortName(getWS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setWS_TrafficTransportationInfo_BizCtrlImplSoap12EndpointAddress(
			java.lang.String address) {
		WS_TrafficTransportationInfo_BizCtrlImplSoap12_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown. This
	 * service has multiple ports for a given interface; the proxy
	 * implementation returned may be indeterminate.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_BindingStub _stub = new com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_BindingStub(
						new java.net.URL(
								WS_TrafficTransportationInfo_BizCtrlImplSoap_address),
						this);
				_stub
						.setPortName(getWS_TrafficTransportationInfo_BizCtrlImplSoapWSDDServiceName());
				return _stub;
			}
			if (com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap_PortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub _stub = new com.ehl.webservice.fw.WS_TrafficTransportationInfo_BizCtrlImplSoap12Stub(
						new java.net.URL(
								WS_TrafficTransportationInfo_BizCtrlImplSoap12_address),
						this);
				_stub
						.setPortName(getWS_TrafficTransportationInfo_BizCtrlImplSoap12WSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("WS_TrafficTransportationInfo_BizCtrlImplSoap"
				.equals(inputPortName)) {
			return getWS_TrafficTransportationInfo_BizCtrlImplSoap();
		} else if ("WS_TrafficTransportationInfo_BizCtrlImplSoap12"
				.equals(inputPortName)) {
			return getWS_TrafficTransportationInfo_BizCtrlImplSoap12();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("WS.RI.TrafficTransportationInfo",
				"WS_TrafficTransportationInfo_BizCtrlImpl");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"WS.RI.TrafficTransportationInfo",
					"WS_TrafficTransportationInfo_BizCtrlImplSoap"));
			ports.add(new javax.xml.namespace.QName(
					"WS.RI.TrafficTransportationInfo",
					"WS_TrafficTransportationInfo_BizCtrlImplSoap12"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("WS_TrafficTransportationInfo_BizCtrlImplSoap".equals(portName)) {
			setWS_TrafficTransportationInfo_BizCtrlImplSoapEndpointAddress(address);
		} else if ("WS_TrafficTransportationInfo_BizCtrlImplSoap12"
				.equals(portName)) {
			setWS_TrafficTransportationInfo_BizCtrlImplSoap12EndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
