package net.kingkid.SalesPromote.entity;


public class Cart extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6182636310908149313L;
	private Integer customerId;
	private Integer itemId;
	private Integer number;
	
	public Cart(Integer customerId, Integer itemId,  Integer number) {
		super();
		this.customerId = customerId;
		this.itemId = itemId;
		this.number = number;
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
	@Override   
	public String toString() {
		return "Cart [customerId=" + customerId + ", itemId=" + itemId +", number=" + number + "]";
	}
	
	
	
}
