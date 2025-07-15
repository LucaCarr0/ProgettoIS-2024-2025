package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO {
	private String email;
	private String pwd;
	private boolean amministratore;
	private int id;

	public UtenteDAO() {

	}

	public int ScriviSuDB() {

		int ret = 0;

		String query = "INSERT INTO Utenti(email, password, amministratore) VALUES ('" + this.email + "', '" + this.pwd + "', '" + 0 +"')"; //su DB amministratore è TINYINT 0 se è falso 1 se vero
		System.out.println(query);
		try {

			ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; 
		}

		return ret;
	}

	public ArrayList<UtenteDAO> getListaUtenti(){

		
		ArrayList<UtenteDAO> lista_utenti_temp = new ArrayList<>();

		String query = "SELECT * FROM Utenti;";

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { 
				UtenteDAO utente_temp = new UtenteDAO();

				utente_temp.setEmail(rs.getString("email"));
				utente_temp.setPwd(rs.getString("password"));
				utente_temp.setAmministratore(rs.getBoolean("amministratore"));
				utente_temp.setId(rs.getInt("id"));
				lista_utenti_temp.add(utente_temp);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		return lista_utenti_temp; 
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

	public boolean isAmministratore() {
		return amministratore;
	}

	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String ottieniEmail() {
	    
		int utenteId = this.id;
		//System.out.println("[OTTIENI EMAIL] ID DELL UTENTE DA CUI PRENDERE LA MAIL: " + utenteId);
	    String query = "SELECT email FROM Utenti U WHERE U.id = " + utenteId;
	    String result = "-1";

	    try {
	        ResultSet rs = DBConnectionManager.selectQuery(query);

	        if (rs.next()) {  
	        	result = rs.getString("email");
	        }

	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    return result;
	}

	


}
