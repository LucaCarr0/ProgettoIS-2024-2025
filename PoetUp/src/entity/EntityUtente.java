package entity;

import database.UtenteDAO;

public class EntityUtente {

	private String email;
	private String pwd;
	
	
	
	public EntityUtente(){ 
		
		
	}
	
	public int scriviSuDB() {
		
		UtenteDAO utenteDAO= new UtenteDAO(); 
		
		utenteDAO.setEmail(this.email);
		utenteDAO.setPwd(this.pwd);
		int i = utenteDAO.SalvaInDB();
		
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

	public void inizializzaProfilo(int id_utente, String nickname) {
		
		//UTENTE È CREATOR DEL PROFILOPERSONALE
		EntityProfiloPersonale profilo= new EntityProfiloPersonale();
		profilo.setNickname(nickname);
		int res=profilo.scriviSuDB(id_utente);
		if (res==-1) {
			System.out.println("errore nel salvataggio del profilo");
		}
	}
	
}
