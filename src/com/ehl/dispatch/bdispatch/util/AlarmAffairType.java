package com.ehl.dispatch.bdispatch.util;

public enum AlarmAffairType
{
	accident("001001"),	// �¹�
	trafficjam("001002"),  // ӵ��
	breakoutCase("001003"),	//ͻ���¼�
	assembleCase("001004"),	//Ⱥ�����¼�
	suspicionCar("001005"),		//���ɳ���
	disasterWeather("001006"),	//�ֺ�����
	socialCase("001007"),	//�ΰ��¼�
	troubleCargo("001008"),	//���ͳ�����
	otherElse("001009"),		//�����¼�
	geologicalDisaster("001010"),		//�����ֺ�
	citizenCase("001011"),		//�����¼�
	euqipment("001018"), //�豸����
	fireAndBoom("001012") ;		//���ֱ�ը
	
	private String typeValue;
	AlarmAffairType(String typeValue)
	{
		this.typeValue = typeValue;
	}
	//��ȡ����ֵ
	public String getTypeValue()
	{
		return this.typeValue;
	}
	
		
}