package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO {
	private String email;
	private String pwd;
	
	public UtenteDAO() {

	}
	
	public int SalvaInDB() {
		
		int ret = 0;
		
		String query = "INSERT INTO studenti(email, password) VALUES ('" + this.email + "', '" + this.pwd + "')";
		System.out.println(query);
		try {
			
			ret = DBConnectionManager.updateQueryReturnGeneratedKey(query);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}
		
		return ret;
	}
	
	public ArrayList<UtenteDAO> getListaUtenti(){
		
		//creo il la lista di appoggio
		ArrayList<UtenteDAO> lista_utenti_temp = new ArrayList<>();
		
		String query = "SELECT * FROM utenti;";
		
		try {
			
			ResultSet rs = DBConnectionManager.selectQuery(query);
			
				while(rs.next()) { //finche ho un risultato
				
				UtenteDAO utente_temp = new UtenteDAO(); //mi creo un nuovo studente
				//mi vado a prendere i dati, accedendo tramite il nome dell'attributo-colonna
				
				utente_temp.setEmail(rs.getString("email"));
				utente_temp.setPwd(rs.getString("cognome"));
				lista_utenti_temp.add(utente_temp); //AGGIUNGO lo studente appena creato alla lista che devo ritornare
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista_utenti_temp; //ritorno la lista
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	


}
