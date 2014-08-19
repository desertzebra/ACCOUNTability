

import db_hib.DB_Manager;

/**
 * @author desertzebra
 *
 */
public class client {

		public static void main(String[] args) {
		System.out.println("Maven + Hibernate + MySQL");
		DB_Manager data = new DB_Manager();
		int result = data.addTransaction(1,1,1000);
		
		
		System.out.println(result);	
	}

}
