package net.kingkid.SalesPromote.entity;



public class CustomerGroup extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -415532804288270239L;
	private Integer customerId;
	private Integer groupId;
	public CustomerGroup(Integer customerId, Integer groupId) {
		super();
		this.customerId = customerId;
		this.groupId = groupId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "CustomerGroup [customerId=" + customerId + ", groupId=" + groupId + "]";
	}

	
}
