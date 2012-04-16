package com.ehl.tfm;
/**
 * @类型说明: 偏移量Bean
 * @创建者：zhaoyu
 * @创建日期：2009-7-10
 */
public class OffsetBean {
    private float offsetUpX;
    private float offsetUpY;
    private float offsetDownX;
    private float offsetDownY;
    public OffsetBean(float offsetUpX,float offsetUpY,float offsetDownX,float offsetDownY){
    	this.offsetDownX = offsetDownX;
    	this.offsetDownY = offsetDownY;
    	this.offsetUpX = offsetUpX;
    	this.offsetUpY = offsetUpY;
    }
	public float getOffsetDownX() {
		return offsetDownX;
	}
	public void setOffsetDownX(float offsetDownX) {
		this.offsetDownX = offsetDownX;
	}
	public float getOffsetDownY() {
		return offsetDownY;
	}
	public void setOffsetDownY(float offsetDownY) {
		this.offsetDownY = offsetDownY;
	}
	public float getOffsetUpX() {
		return offsetUpX;
	}
	public void setOffsetUpX(float offsetUpX) {
		this.offsetUpX = offsetUpX;
	}
	public float getOffsetUpY() {
		return offsetUpY;
	}
	public void setOffsetUpY(float offsetUpY) {
		this.offsetUpY = offsetUpY;
	}
}
