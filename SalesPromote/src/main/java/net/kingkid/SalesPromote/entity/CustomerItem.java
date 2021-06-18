package net.kingkid.SalesPromote.entity;

import java.util.Date;

public class CustomerItem extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8658269898492129734L;
	private Integer customerId;
	private Integer itemId;
	private Date createTime;
	private String createBy;
	public CustomerItem(Integer customerId, Integer itemId, Date createTime, String createBy) {
		super();
		this.customerId = customerId;
		this.itemId = itemId;
		this.createTime = createTime;
		this.createBy = createBy;
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
		return "FolderItem [customerId=" + customerId + ", itemId=" + itemId + ", createTime=" + createTime + ", createBy="
				+ createBy + "]";
	}
	
	
}
