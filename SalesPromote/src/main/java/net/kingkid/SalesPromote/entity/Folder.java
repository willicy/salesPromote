package net.kingkid.SalesPromote.entity;

import java.sql.Timestamp;

/**
 * 资料夹
 * @author willicy
 *
 */
public class Folder extends BaseEntity {

	
	public Folder() {
	
	}

	private static final long serialVersionUID = -67427939265985686L;
	private Integer id;
	private String name;
	private Timestamp updateTime;
	private String updateBy;
	private Timestamp createTime;
	private String createBy;
	
	
	
	public Folder(Integer id, String name, Timestamp updateTime, String updateBy, Timestamp createTime,
			String createBy) {
		super();
		this.id = id;
		this.name = name;
		this.updateTime = updateTime;
		this.updateBy = updateBy;
		this.createTime = createTime;
		this.createBy = createBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", createTime=" + createTime + ", createBy=" + createBy + "]";
	}
	
	
	

}
