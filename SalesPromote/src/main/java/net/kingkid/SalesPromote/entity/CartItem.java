package net.kingkid.SalesPromote.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CartItem extends Item{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8869201631228023557L;
	private Integer customerId;
	private Integer itemId;
	private Integer number;
	private Integer id;
	private String photoLocation;
	private String name;
	private String type;
	private String color;
	private String size;
	private Integer sizeNum;
	private Integer price1;
	private Integer price2;
	private Integer price3;
	private Integer price4;
	private String state;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private String updateBy;
	private Date createTime;
	private String createBy;
	
	
	
	public CartItem(Integer customerId, Integer itemId, Integer number, Integer id, String photoLocation, String name,
			String type, String color, String size,Integer sizeNum ,Integer price1, Integer price2, Integer price3, Integer price4,
			String state, String remark, Date updateTime, String updateBy, Date createTime, String createBy) {
		super();
		this.customerId = customerId;
		this.itemId = itemId;
		this.number = number;
		this.id = id;
		this.photoLocation = photoLocation;
		this.name = name;
		this.type = type;
		this.color = color;
		this.size = size;
		this.sizeNum = sizeNum;
		this.price1 = price1;
		this.price2 = price2;
		this.price3 = price3;
		this.price4 = price4;
		this.state = state;
		this.remark = remark;
		this.updateTime = updateTime;
		this.updateBy = updateBy;
		this.createTime = createTime;
		this.createBy = createBy;
	}
	public Integer getSizeNum() {
		return sizeNum;
	}
	public void setSizeNum(Integer sizeNum) {
		this.sizeNum = sizeNum;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoLocation() {
		return photoLocation;
	}
	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getPrice1() {
		return price1;
	}
	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}
	public Integer getPrice2() {
		return price2;
	}
	public void setPrice2(Integer price2) {
		this.price2 = price2;
	}
	public Integer getPrice3() {
		return price3;
	}
	public void setPrice3(Integer price3) {
		this.price3 = price3;
	}
	public Integer getPrice4() {
		return price4;
	}
	public void setPrice4(Integer price4) {
		this.price4 = price4;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Override
	public String toString() {
		return "CartItem [customerId=" + customerId + ", itemId=" + itemId + ", number=" + number + ", id=" + id
				+ ", photoLocation=" + photoLocation + ", name=" + name + ", type=" + type + ", color=" + color
				+ ", size=" + size + ", sizeNum=" + sizeNum + ", price1=" + price1 + ", price2=" + price2 + ", price3="
				+ price3 + ", price4=" + price4 + ", state=" + state + ", remark=" + remark + ", updateTime="
				+ updateTime + ", updateBy=" + updateBy + ", createTime=" + createTime + ", createBy=" + createBy + "]";
	}
	
	
}
