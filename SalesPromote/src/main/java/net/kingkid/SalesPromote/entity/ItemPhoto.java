package net.kingkid.SalesPromote.entity;

/**
 * 款细节图
 * @author willicy
 *
 */
public class ItemPhoto extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3282021700901355926L;
	private Integer itemId;
	private String photoLocation;
	private Integer priority;
	public ItemPhoto() {
		super();
		
	}

  


	public ItemPhoto(Integer itemId, String photoLocation,Integer priority) {
		super(); 
		this.itemId = itemId;
		this.photoLocation = photoLocation;
		this.priority = priority;
	}



	public Integer getPriority() {
		return priority;
	}



	public void setPriority(Integer priority) {
		this.priority = priority;
	}



	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
	public String getPhotoLocation() {
		return photoLocation;
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	





	@Override
	public String toString() {
		return "ItemPhoto [itemId=" + itemId + ", photoLocation=" + photoLocation + ", priority=" + priority + "]";
	}





	

	


}
