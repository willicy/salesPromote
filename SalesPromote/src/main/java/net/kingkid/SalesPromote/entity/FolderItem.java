package net.kingkid.SalesPromote.entity;

import java.util.Date;

public class FolderItem extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8742998963157445536L;
	private Integer folderId;
	private Integer itemId;
	private Date createTime;
	private String createBy;
	public FolderItem(Integer folderId, Integer itemId, Date createTime, String createBy) {
		super();
		this.folderId = folderId;
		this.itemId = itemId;
		this.createTime = createTime;
		this.createBy = createBy;
	}
	public Integer getFolderId() {
		return folderId;
	}
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
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
		return "FolderItem [folderId=" + folderId + ", itemId=" + itemId + ", createTime=" + createTime + ", createBy="
				+ createBy + "]";
	}
	
	
}
