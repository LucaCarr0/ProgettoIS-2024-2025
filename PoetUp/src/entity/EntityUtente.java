package entity;

import database.UtenteDAO;

public class EntityUtente {

	private String email;
	private String pwd;
	
	
	
	public EntityUtente(){ 
		
		
	}
	
	public int scriviSuDB() {
		
		UtenteDAO utente= new UtenteDAO(); 
		
		utente.setEmail(this.email);
		utente.setPwd(this.pwd);
		int i = utente.SalvaInDB();
		
		return i;
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
