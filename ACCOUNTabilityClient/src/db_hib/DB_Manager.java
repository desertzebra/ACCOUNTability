package db_hib;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class DB_Manager {

	/**
	 * @param args
	 */
	public int addTransaction(long donor_id, long rec_id, float amount){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//Entity donor_chk = new Entity();
		//Entity rec_chk = new Entity();
        Data tran_cl = new Data();
        
        tran_cl.setDonor(donor_id);
        tran_cl.setRecv(rec_id);
        tran_cl.setTestimonialId(1);
        long x = 1;
        //tran_cl.setId(x);
        tran_cl.setAmount(amount);
        tran_cl.setDate(Calendar.getInstance().getTime());
        session.saveOrUpdate(tran_cl);
        session.getTransaction().commit();
		return 1;
	}
	
	public void reviewAllTransactions() {  
		  
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query queryResult = session.createQuery("from Data");  
		List all_trans;
		  all_trans = queryResult.list();  
		  
		  System.out.println("ID,From,To,Amount,Testimonial");
		  for (int i = 0; i < all_trans.size(); i++) {  
		   Data single_trans = (Data) all_trans.get(i);
		   System.out.println(single_trans.getId()+","+ single_trans.getDonor()+","+single_trans.getRecv()+","+
		   single_trans.getAmount()+","+single_trans.getTestimonialId());
		  }  
		 System.out.println("Database contents delivered...");   
    }  
	public List getTransactions(int IdValue, long Donor, long Recv, float amount,
			String startdate, String enddate,int offset, int limit) {  
		  
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		/*
		 * Build query
		 */
		String queryString = builtQuery( IdValue, Donor, Recv, amount, startdate, enddate);
		System.out.println(queryString);
		Query query = session.createQuery(queryString);
		query = setQueryParam(query, IdValue, Donor, Recv, amount, startdate, enddate);
		if(offset>0){
			query.setFirstResult(offset);
		}
		if(limit>0){
			query.setMaxResults(limit);
		}
		List all_trans;
		  all_trans = query.list();  
		  
		  return all_trans;  
	   
    }
	public String getMetaName(long Item){
			  
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			/*
			 * Build query
			 */
			
			Query query = session.createQuery("from Meta where id = :IdValue");
			query.setParameter("IdValue", Item);
			
			Meta metaRow = (Meta) query.uniqueResult();
			  
			  return metaRow.getName();  

	}
	public String getTestimonial(long Id){
		  
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		/*
		 * Build query
		 */
		Query query = session.createQuery("from Testimonial where id = :IdValue");
		query.setParameter("IdValue", Id);
		Testimonial testimonialRow = (Testimonial) query.uniqueResult();
		  if(testimonialRow == null){
			  return "-";
		  }
		  return testimonialRow.getProof();  

}

	public String builtQuery(int IdValue, long Donor, long Recv, float amount,
			String startdate, String enddate){
		
		int mul_queries = 0;
		
		String queryString = "from Data ";
		if(IdValue!=0){
			queryString += " where id = :IdValue ";
			mul_queries++;
		}
		if(mul_queries>0 && Donor!=0){
			queryString += " and donor = :Donor ";
			mul_queries++;
		}
		else if(Donor!=0){
			queryString += " where donor = :Donor ";
			mul_queries++;
		}
		if(mul_queries>0 && Recv!=0){
			queryString += " and recv = :Recv ";
			mul_queries++;
		}
		else if(Recv!=0){
			queryString += " where recv = :Recv ";
			mul_queries++;
		}
		if(mul_queries>0 && amount!=0){
			queryString += " and amount = :amount ";
			mul_queries++;
		}
		else if(amount!=0){
			queryString += " where amount = :amount ";
			mul_queries++;
		}
		if(mul_queries>0 && startdate!=null){
			queryString += " and date > :startdate ";
			mul_queries++;
		}
		else if(startdate!=null){
			queryString += " where date > :startdate ";
			mul_queries++;
		}
		if(mul_queries>0 && enddate!=null){
			queryString += " and date < :enddate ";
			mul_queries++;
		}
		else if(enddate!=null){
			queryString += " where date < :enddate ";
			mul_queries++;
		}
		
		return queryString;
		
	}
	
	public Query setQueryParam(Query query, int IdValue, long Donor, long Recv, float amount,
			String startdate, String enddate){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date st_date = null;
		Date en_date = null;
		
		if(IdValue!=0){
			query.setParameter("IdValue", IdValue);
		}
		if(Donor!=0){
			query.setParameter("Donor", Donor);
		}
		if(Recv!=0){
			query.setParameter("Recv", Recv);
		}
		if(amount!=0){
			query.setParameter("amount", amount);
		}
		if(startdate!=null){
			try {
				st_date = formatter.parse(startdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			query.setParameter("startdate", st_date);
		}
		if(enddate!=null){
			try {
				en_date = formatter.parse(enddate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			query.setParameter("enddate", en_date);
		}
		return query;
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
		DB_Manager data = new DB_Manager();
		int result = data.addTransaction(2,3,500);
		result = data.addTransaction(2,3,500);
		result = data.addTransaction(3,4,500);
		result = data.addTransaction(3,4,500);
		//String datestring_s = "2012-12-01 21:57:06";
		//String datestring_e = "2012-12-01 22:57:06";
		String datestring_s = null;
		String datestring_e = null;
		long donorID=0;
		long RecID=1;
				data.analyseResults(donorID,RecID,datestring_s, datestring_e);

	}

}