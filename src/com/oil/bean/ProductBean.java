package com.oil.bean;

import java.io.Serializable;
import java.util.ArrayList;

//供求详情
public class ProductBean implements Serializable{
public String sdid,tb,addTime,refreshTime,corpId,corpName,cpName,cpId,model,stock,price,name,phone,remark,areaId,lastCallTime;
public boolean ordered;
public String orderId; //如果已下单，对应的订单id
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
	remark=""; //备注
	provincesName="";
	areaId=""; // 1:全国 0:其他
	orderId="";
	ordered=false;
	provincesId=new ArrayList<String>();
}
/**
 * "stock":数量 如果为 0 ，则表示未知            
 *  "tb":11代表 供应  10 代表 求购
 */
}
