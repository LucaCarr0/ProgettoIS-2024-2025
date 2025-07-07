package database;

import java.sql.Date;
import java.sql.SQLException;

public class ProfiloPersonaleDAO {
	private int id_utente;
	private String nickname;
	private String nome;
	private String cognome;
	private String immagineProfilo;
	private String biografia;
	private Date data_di_nascita;
	
	public ProfiloPersonaleDAO(){
		
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
	
	
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getImmagineProfilo() {
		return immagineProfilo;
	}
	public void setImmagineProfilo(String immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	public String getBiografia() {
		return biografia;
	}
	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	public Date getData_di_nascita() {
		return data_di_nascita;
	}
	public void setData_di_nascita(Date data_di_nascita) {
		this.data_di_nascita = data_di_nascita;
	} 
	
	
}
