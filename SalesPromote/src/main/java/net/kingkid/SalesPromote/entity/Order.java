package net.kingkid.SalesPromote.entity;

import java.util.Date;

public class Order extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7663972128182789661L;
	private Integer id;
	private Integer customerId;
	private String  customer;
	private Integer salesId;
	private boolean state;
	private Integer prices;
	private Date createTime;
	
	
	public Order() {
		super();
	}


	


	public Order(Integer id, Integer customerId, String customer, Integer salesId, boolean state, Integer prices,
			Date createTime) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customer = customer;
		this.salesId = salesId;
		this.state = state;
		this.prices = prices;
		this.createTime = createTime;
	}

	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getSalesId() {
		return salesId;
	}


	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public Integer getPrices() {
		return prices;
	}


	public void setPrices(Integer prices) {
		this.prices = prices;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", customer=" + customer + ", salesId=" + salesId
				+ ", state=" + state + ", prices=" + prices + ", createTime=" + createTime + "]";
	}


	

	
	
	
	
}
