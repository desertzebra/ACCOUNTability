package db_hib;


import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;

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
		Entity donor_chk = new Entity();
		Entity rec_chk = new Entity();
        Transaction tran_cl = new Transaction();
        
        tran_cl.setFrom(donor_id);
        tran_cl.setFrom(rec_id);
        tran_cl.setAmount(amount);
        tran_cl.setDate(Calendar.getInstance().getTime());
        session.save(tran_cl);
        session.getTransaction().commit();
		return 1;
	}

}
