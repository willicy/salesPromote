package net.kingkid.SalesPromote.entity;

/**
 * 用户
 * @author willicy
 *
 */
public class Customer extends BaseEntity {

	
	private static final long serialVersionUID = -8773763719555283529L;
	private Integer id; 
	private String name;
	private String nickname;
	private String password;
	private String phone;
	private String country;
	private String district;
	private Integer salesId;
	private String code;
	public Customer() {
		super();
		
	}
	
	



	public Customer(Integer id, String name, String nickname, String password, String phone, String country,
			String district, Integer salesId,String code) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.phone = phone;
		this.country = country;
		this.district = district;
		this.salesId = salesId;
		this.code=code;
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


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}
 

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public Integer getSalesId() {
		return salesId;
	}


	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}





	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", nickname=" + nickname + ", password=" + password
				+ ", phone=" + phone + ", country=" + country + ", district=" + district + ", salesId=" + salesId
				+ ", code=" + code + "]";
	}

	
	
	
	

}
