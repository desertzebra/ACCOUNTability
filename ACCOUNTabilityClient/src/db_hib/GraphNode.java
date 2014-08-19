package db_hib;

import org.json.simple.JSONObject;

public class GraphNode{
	private String name;
	private int level;
	private GraphNode parentNode;
	private float Amount;
	private String Testimonial;
	private Long itemID;
	private String type = "";
	private int parsed = 0;
	private Long Donor;
	private Long Recv;
	
	public JSONObject ConvertToJSON(){
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("level",level );
		obj.put("amount",Amount );
		obj.put("parentNode",parentNode );
		obj.put("testimonial",Testimonial);
		obj.put("type",type );
		obj.put("donor",Donor );
		obj.put("recv",Recv );
		return obj;
	}
	
	public float getAmount() {
		return Amount;
	}
	public Long getDonor() {
		return Donor;
	}
	public void setDonor(Long donor) {
		Donor = donor;
	}
	public Long getRecv() {
		return Recv;
	}
	public void setRecv(Long recv) {
		Recv = recv;
	}
	public String getType() {
		return type;
	}
	public int getParsed() {
		return parsed;
	}
	public void setParsed(int parsed) {
		this.parsed = parsed;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAmount(float amount) {
		Amount = amount;
	}
	public String getTestimonial() {
		return Testimonial;
	}
	public void setTestimonial(String testimonial) {
		Testimonial = testimonial;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public GraphNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(GraphNode parentNode) {
		this.parentNode = parentNode;
	}
	public String toString(){
		String ret_str = "name="+this.getName()+
				",id="+this.getItemID()+
				",level="+this.getLevel()+
				",Type="+this.getType();
		if(getParentNode()!=null){
			ret_str += ",parentNode="+getParentNode().getName();
		}
		else {
			ret_str += ",parentNode=null";
		}
		ret_str += ",amount="+this.getAmount()+
				",testimonial="+this.getTestimonial()+";\r\n";
		return ret_str;
	}
	public Long getItemID() {
		return itemID;
	}
	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}
	
	
	
}
