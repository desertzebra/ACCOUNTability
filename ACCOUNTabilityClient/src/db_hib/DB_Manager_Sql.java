package db_hib;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



import org.hibernate.Query;
import org.hibernate.Session;

import persistence.HibernateUtil;

/**
 * @author desertzebra
 *
 */
public class DB_Manager_Sql {

	private String Conn_user = "root";
	private String Conn_host = "localhost:3306";
	private String Conn_DB = "accountability";
	private String Conn_pass = "root";
	private String query="";
	/*DB_Manager_Sql(){
		
		public String Conn_str ="jdbc:mysql://"+Conn_host+"/"+Conn_DB+"?user="+Conn_user+"&password="+Conn_pass;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(Con_str);
		statement = connect.createStatement();
		
	}
	*/
	private Connection connect;
	private Statement statement;
	 
	public ResultSet execute_select_query(String query){
		String Conn_str ="jdbc:mysql://"+Conn_host+"/"+Conn_DB+"?user="+Conn_user+"&password="+Conn_pass;
		
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(Conn_str);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			statement = connect.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				System.out.println(query);

		    try {
				rs = statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				System.out.println(rs.getFetchSize());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    return rs;
	}
	public String getAllData_sql() {
				    
			query= "Select * from data";
			ResultSet rs = execute_select_query(query);
			
			String ret_str="";
			try {
				while(rs.next()){
					ret_str += rs.getLong("donor")+",";
					ret_str += rs.getLong("recv")+",";
					ret_str += rs.getFloat("amount")+",";
					ret_str += rs.getLong("testimonial_id")+",";
					ret_str += rs.getDate("date")+"\r\n";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ret_str;
		
	}
	
	public List getTransactions(int IdValue, long Donor, long Recv, float amount,
			String startdate, String enddate,int offset, int limit) {  
		/*
		 * Build query
		 */
		String queryString = builtQuery_sql( IdValue, Donor, Recv, amount, startdate, enddate);
		ResultSet rs = execute_select_query(queryString);
		String ret_str="";
		ArrayList data_res = new ArrayList();
		try {
			while(rs.next()){
				Data new_row = new Data();
				new_row.setAmount(rs.getFloat("amount"));
				new_row.setDate(rs.getDate("date"));
				new_row.setDonor(rs.getLong("donor"));
				new_row.setRecv(rs.getLong("recv"));
				new_row.setTestimonialId(rs.getLong("testimonial_id"));
				new_row.setId(rs.getLong("id"));
				data_res.add(new_row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return data_res;  
	   
    }

	
	public String builtQuery_sql(int IdValue, long Donor, long Recv, float amount,
			String startdate, String enddate){
		
		int mul_queries = 0;
		
		String queryString = "Select * from Data ";
		if(IdValue!=0){
			queryString += " where id="+IdValue+" ";
			mul_queries++;
		}
		if(mul_queries>0 && Donor!=0){
			queryString += " and donor ="+Donor+" ";
			mul_queries++;
		}
		else if(Donor!=0){
			queryString += " where donor ="+Donor+" ";
			mul_queries++;
		}
		if(mul_queries>0 && Recv!=0){
			queryString += " and recv ="+Recv+" ";
			mul_queries++;
		}
		else if(Recv!=0){
			queryString += " where recv ="+Recv+" ";
			mul_queries++;
		}
		if(mul_queries>0 && amount!=0){
			queryString += " and amount ="+amount+" ";
			mul_queries++;
		}
		else if(amount!=0){
			queryString += " where amount ="+amount+" ";
			mul_queries++;
		}
		if(mul_queries>0 && startdate!=null){
			queryString += " and date >"+startdate+" ";
			mul_queries++;
		}
		else if(startdate!=null){
			queryString += " where date >"+startdate+" ";
			mul_queries++;
		}
		if(mul_queries>0 && enddate!=null){
			queryString += " and date <"+enddate+" ";
			mul_queries++;
		}
		else if(enddate!=null){
			queryString += " where date <"+enddate+" ";
			mul_queries++;
		}
		
		return queryString;
		
	}

	public String getMetaName(long Item){
			  
		query = "select * from Meta where id="+Item;
		ResultSet rs = execute_select_query(query);
		
		String ret_str="";
		try {
			while(rs.next()){
				ret_str = rs.getString("name");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret_str;
			
	}
	public String getTestimonial(long Id){
		  
		query = "select * from Testimonial where id="+Id;
		ResultSet rs = execute_select_query(query);
		
		String ret_str="";
		try {
			while(rs.next()){
				ret_str += rs.getString("proof")+"";
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret_str;
}

	public void printResults(List results){
		System.out.println("ID,From,To,Amount,Testimonial,Date");
		  for (int i = 0; i < results.size(); i++) {  
		   Data single_trans = (Data) results.get(i);
		   System.out.println(single_trans.getId()+","+ single_trans.getDonor()+","+single_trans.getRecv()+","+
		   single_trans.getAmount()+","+single_trans.getTestimonialId()
		   +","+single_trans.getDate());
		  }
	}
	public void analyseResults(long donorID,long RecID,String datestring_s,String datestring_e){
		if(donorID>0){
			fetchDonData(donorID,datestring_s,datestring_e);
		}
		else if(RecID>0){
			fetchRecData(RecID,datestring_s,datestring_e);
		}
			  
	}
	private void fetchRecData(long recID, String datestring_s,
			String datestring_e) {

		GraphDataset gd = new GraphDataset();
		
		List secResults = null;
		List baseResults = null;
		
		System.out.println("-------------------------------------");
		System.out.println("DONORS Begin.");
		System.out.println("-------------------------------------");
		
		baseResults = getTransactions(0,0,recID,0,datestring_s,datestring_e,-1,0);
		for (int i = 0; i < baseResults.size(); i++) {  
	
			Data single_base = (Data) baseResults.get(i);
			
			String metaName = getMetaName(single_base.getDonor());
			long ItemID = single_base.getId();
			//System.out.println("metaName = "+metaName);
			if(gd.itemExists(ItemID)==1){
				continue;
			}
			   gd.addNode(ItemID,"D",single_base.getDonor(),single_base.getRecv(),metaName,null, single_base.getAmount(),
					   				getTestimonial(single_base.getTestimonialId()));
		}
		
		System.out.println("-------------------------------------");
		System.out.println("DONORS DONE.");
		System.out.println("-------------------------------------");
		
		secResults = getTransactions(0,recID,0,0,datestring_s,datestring_e,-1,0);
		for (int i = 0; i < secResults.size(); i++) {  
			
			Data single_res = (Data) secResults.get(i);
			
			String metaName = getMetaName(single_res.getDonor());
			long ItemID = single_res.getId();
			//System.out.println("metaName = "+metaName);
			if(gd.itemExists(ItemID)==1){
				continue;
			}
			   gd.addNode(ItemID,"R",single_res.getDonor(),single_res.getRecv(),metaName+"<"+gd.getNode(0).getItemID(),gd.getNode(0), single_res.getAmount(),
					   				getTestimonial(single_res.getTestimonialId()));
		}
		
		System.out.println("-------------------------------------");
		System.out.println("PARENT RECEIVERS DONE.");
		System.out.println("-------------------------------------");
		
		for(int graph_ite=0; graph_ite< gd.getGraphSize();graph_ite++){
			if(gd.getNode(graph_ite).getType()=="R" && gd.getNode(graph_ite).getParsed()==0){
				
				secResults = getTransactions(0,gd.getNode(graph_ite).getRecv(),0,0,datestring_s,datestring_e,-1,0);
				for (int i = 0; i < secResults.size(); i++) {  
					
					Data single_res = (Data) secResults.get(i);
					
					String metaName = getMetaName(single_res.getDonor());
					long ItemID = single_res.getId();
					//System.out.println("metaName = "+metaName);
					if(gd.itemExists(ItemID)==1){
						continue;
					}
					   gd.addNode(ItemID,"R",single_res.getDonor(),single_res.getRecv(),metaName,gd.getNode(graph_ite), single_res.getAmount(),
							   				getTestimonial(single_res.getTestimonialId()));
				}
				gd.getNode(graph_ite).setParsed(1);
				
			}
				
		}
		
		gd.convertToJSON();
		//System.out.println(gd.toString());	
		
	}

	private void fetchDonData(long donorID, String datestring_s,
			String datestring_e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String args[]){
		DB_Manager_Sql data = new DB_Manager_Sql();
		System.out.println(data.getAllData_sql());
		/*int result = data.addTransaction(2,3,500);
		result = data.addTransaction(2,3,500);
		result = data.addTransaction(3,4,500);
		result = data.addTransaction(3,4,500);
		//String datestring_s = "2012-12-01 21:57:06";
		//String datestring_e = "2012-12-01 22:57:06";
		 * 
		 */
		String datestring_s = null;
		String datestring_e = null;
		long donorID=0;
		long RecID=1;
				data.analyseResults(donorID,RecID,datestring_s, datestring_e);
				

	}

}