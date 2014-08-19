package com.rhok;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse; 
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.mysql.jdbc.PreparedStatement;

/**
 * Portlet implementation class accountability
 */
public class accountability extends MVCPortlet {
	
	private Connection connect = null;
	private Statement statement = null;
	//private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private static List<ListEntry> ENTRIES;

	public void insertTransaction(ActionRequest request, ActionResponse response) throws SystemException
	{
		try
		{
			
			String donorId = request.getParameter("donorid");
			String receiverId = request.getParameter("receiverid");
			String amount = request.getParameter("amount");
			
			//System.out.println(donorId);
			//System.out.println(receiverId);
			//System.out.println(amount);
			//System.out.println("Service is loaded."); 
			Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost:8889/accountability?"
		                  + "user=root&password=root");
		      // Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // Result set get the result of the SQL query
		      String query;
				 query= "INSERT into accountability.Transaction(`from`,`to`,`amount`,`testimonial_id`) values('" +donorId + "', '" +receiverId+ "','"+amount+"','1')";
				 System.out.println(query);
		       statement.executeUpdate(query);
		   
		      connect.close();
		      
		}
		catch (Exception e)
		{
		System.err.println("Got an exception! "); 
		System.err.println(e.getMessage()); 
		} 		
	}
	
	
	public void serveResource(ResourceRequest req, ResourceResponse res) throws PortletException, IOException {
	
		String query = req.getParameter("q");

        JSONObject json = JSONFactoryUtil.createJSONObject();

        JSONArray results = JSONFactoryUtil.createJSONArray();
        json.put("response", results);

        List<ListEntry> filteredEntries = getFilteredEntries(query);
        
        for (ListEntry entry : filteredEntries) {
            JSONObject listEntry = JSONFactoryUtil.createJSONObject();
            listEntry.put("entityid", entry.id);
            listEntry.put("name", entry.name);
            //listEntry.put("score", entry.score);                
            results.put(listEntry);
        }
        PrintWriter writer = res.getWriter();
        writer.println(json.toString());
	}
	

private List<ListEntry> getFilteredEntries(String query) {
	try {
		ENTRIES =Collections.unmodifiableList(new ArrayList<ListEntry>() {{
		
			System.out.println("Service is loaded."); 
			Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost:8889/accountability?"
		                  + "user=root&password=root");
		      // Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // Result set get the result of the SQL query
		      resultSet = statement
		          .executeQuery("SELECT * from accountability.Entity");
		      //writeResultSet(resultSet);
		
			 while (resultSet.next()) {
				 System.out.println(resultSet.getString("name"));
				 add(new ListEntry(resultSet.getLong("id"), resultSet.getString("name")));			 
			 }
			 connect.close();
			
}});
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//System.out.println(ENTRIES.size());
if (query == null || "*".equals(query)) {
	
	return ENTRIES;
}

List<ListEntry> result = new ArrayList<ListEntry>();
String lowercaseQuery = query.toLowerCase();         
for (ListEntry entry : ENTRIES) {
    if (entry.name.toLowerCase().startsWith(lowercaseQuery)) {
    
        result.add(entry);
     
    }
}

return result;
}

private static class ListEntry {

    public long id;
    public String name;
    //public Integer score;
     
    public ListEntry (long id, String name) {
        this.id = id;
        this.name = name;
      //  this.score = score;
        
    }
}

}
