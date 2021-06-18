package net.kingkid.SalesPromote.entity;

/**
 * 款下拉框
 * @author willicy
 *
 */
public class ItemDropdown extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3282021700901355926L;
	private Integer id;
	private String data;
	private Integer num;
	public ItemDropdown() {
		super();
		
	}

  

	public ItemDropdown(Integer id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	public ItemDropdown(Integer id, String data,Integer num) {
		super(); 
		this.id = id;
		this.data = data;
		this.num = num;
	}



	public Integer getNum() {
		return num;
	}



	public void setNum(Integer num) {
		this.num = num;
	}



	public void setData(String data) {
		this.data = data;
	}



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getData() {
		return data;
	}


	public void setName(String data) {
		this.data = data;
	}



	@Override
	public String toString() {
		return "ItemDropdown [id=" + id + ", data=" + data + ", num=" + num + "]";
	}





	

	


}
