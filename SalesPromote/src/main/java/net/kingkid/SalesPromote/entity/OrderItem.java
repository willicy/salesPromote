package net.kingkid.SalesPromote.entity;



/**
 * 资料夹
 * @author willicy
 *
 */
public class OrderItem extends BaseEntity {

	
	public OrderItem() {
	
	}

	private static final long serialVersionUID = -67427939265985686L;
	private Integer id;
	private Integer orderId;
	private Integer orderAmount;
	private Integer orderTotal;
	private Integer orderPrice;
	private String photoLocation;
	private String name;
	private String type;
	private String color;
	private String size;
	private Integer price1;
	private Integer price2;
	private Integer price3;
	private Integer price4;
	private String state;
	private String remark;
	
	
	
	
	







	






	public OrderItem(Integer id, Integer orderId, Integer orderAmount, Integer orderTotal, Integer orderPrice,
			String photoLocation, String name, String type, String color, String size, Integer price1, Integer price2,
			Integer price3, Integer price4, String state, String remark) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderAmount = orderAmount;
		this.orderTotal = orderTotal;
		this.orderPrice = orderPrice;
		this.photoLocation = photoLocation;
		this.name = name;
		this.type = type;
		this.color = color;
		this.size = size;
		this.price1 = price1;
		this.price2 = price2;
		this.price3 = price3;
		this.price4 = price4;
		this.state = state;
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}






	public void setId(Integer id) {
		this.id = id;
	}





	public Integer getOrderId() {
		return orderId;
	}






	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}






	public Integer getOrderAmount() {
		return orderAmount;
	}






	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}






	public Integer getOrderTotal() {
		return orderTotal;
	}






	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}






	public Integer getOrderPrice() {
		return orderPrice;
	}






	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
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
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", orderId=" + orderId + ", orderAmount=" + orderAmount + ", orderTotal="
				+ orderTotal + ", orderPrice=" + orderPrice + ", photoLocation=" + photoLocation + ", name=" + name
				+ ", type=" + type + ", color=" + color + ", size=" + size + ", price1=" + price1 + ", price2=" + price2
				+ ", price3=" + price3 + ", price4=" + price4 + ", state=" + state + ", remark=" + remark + "]";
	}






	
	
	
	
	
	
	

}
