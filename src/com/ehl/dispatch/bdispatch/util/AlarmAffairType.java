package com.ehl.dispatch.bdispatch.util;

public enum AlarmAffairType
{
	accident("001001"),	// 事故
	trafficjam("001002"),  // 拥堵
	breakoutCase("001003"),	//突发事件
	assembleCase("001004"),	//群体性事件
	suspicionCar("001005"),		//嫌疑车辆
	disasterWeather("001006"),	//灾害天气
	socialCase("001007"),	//治安事件
	troubleCargo("001008"),	//大型车故障
	otherElse("001009"),		//其他事件
	geologicalDisaster("001010"),		//地质灾害
	citizenCase("001011"),		//市政事件
	euqipment("001018"), //设备故障
	fireAndBoom("001012") ;		//火灾爆炸
	
	private String typeValue;
	AlarmAffairType(String typeValue)
	{
		this.typeValue = typeValue;
	}
	//获取类型值
	public String getTypeValue()
	{
		return this.typeValue;
	}
	
		
}