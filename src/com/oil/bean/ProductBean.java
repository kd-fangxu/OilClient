package com.oil.bean;

import java.io.Serializable;
import java.util.ArrayList;

//��������
public class ProductBean implements Serializable{
public String sdid,tb,addTime,refreshTime,corpId,corpName,cpName,cpId,model,stock,price,name,phone,remark,areaId,lastCallTime;
public boolean ordered;
public String orderId; //������µ�����Ӧ�Ķ���id
public ArrayList<String >provincesId;
public String provincesName;
public ProductBean(){
	lastCallTime="";
	cpId="";
	sdid="";
	tb="";
	addTime="";
	refreshTime="";
	corpId="";
	corpName="";
	cpName="";
	model="";
	stock="";
	price="";
	name="";
	phone="";
	remark=""; //��ע
	provincesName="";
	areaId=""; // 1:ȫ�� 0:����
	orderId="";
	ordered=false;
	provincesId=new ArrayList<String>();
}
/**
 * "stock":���� ���Ϊ 0 �����ʾδ֪            
 *  "tb":11���� ��Ӧ  10 ���� ��
 */
}
