package net.kingkid.SalesPromote.entity;

/**
 * 客户组
 * @author willicy
 *
 */
public class Group extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4698082039053126386L;
	private Integer id;
	private String name;
	private Integer salesId;
	
	public Group() {
		super();
		
	}



	public Group(Integer id, String name, Integer salesId) {
		super();
		this.id = id;
		this.name = name;
		this.salesId = salesId;
	}



	public Integer getSalesId() {
		return salesId;
	}



	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
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
		return "Group [id=" + id + ", name=" + name + ", salesId=" + salesId + "]";
	}


	

	


}
