package db_hib;

import java.util.ArrayList;

public class GraphDataset {
	public String name="";
	public ArrayList<GraphNode> nodes=null;
	
	public GraphDataset(){
		
		nodes = new ArrayList();
		
	}
	public void convertToJSON(){
		for(int i=0; i<nodes.size();i++){
			System.out.println(nodes.get(i).ConvertToJSON().toJSONString());
		}
	}
	public void addNode(Long ItemID, String type,Long Donor, Long Recv, String name, GraphNode parent, float f, String testimonial){
		
		GraphNode new_node = new GraphNode();
		new_node.setItemID(ItemID);
		new_node.setType(type);
		new_node.setDonor(Donor);
		new_node.setRecv(Recv);
		if(parent==null){
			new_node.setLevel(0);
		}
		else{
			new_node.setLevel(parent.getLevel()+1);
		}
		new_node.setParentNode(parent);
		new_node.setAmount(f);
		new_node.setName(name);
		new_node.setTestimonial(testimonial);
		new_node.setParsed(0);
		nodes.add(new_node);
		
	}
	public int itemExists(Long itemID) {
		for(int i=0; i<nodes.size();i++){
			if(nodes.get(i).getItemID()==itemID){
				return 1;
			}
		}
		return 0;
	}
	public int getGraphSize(){
		return nodes.size();
	}
	public String toString(){
		String graphAsStr = "";
		for(int i=0; i<nodes.size();i++){
			graphAsStr += nodes.get(i).toString();
		}
		return graphAsStr;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<GraphNode> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<GraphNode> nodes) {
		this.nodes = nodes;
	}
	public GraphNode getNode(int i) {
		// TODO Auto-generated method stub
		if(i<nodes.size() && i>=0){
			return nodes.get(i);
		}
		else{
			return null;
		}
	}
	
	
	
}
