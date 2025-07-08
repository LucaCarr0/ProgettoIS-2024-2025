package entity;

import java.util.ArrayList;

import database.RaccoltaDAO;
import database.UtenteDAO;
import session.SessioneUtente;

public class EntityUtente {

	private String email;
	private String pwd;
	private int id;
	private boolean amministratore;
	private EntityProfiloPersonale profilo;
	private static ArrayList<EntityRaccolta> raccolte;
	
	public EntityUtente(){ 
		
		this.profilo=new EntityProfiloPersonale();
	}
	
	public int salvaSuDB() {
		
		UtenteDAO utenteDAO= new UtenteDAO(); 
		
		utenteDAO.setEmail(this.email);
		utenteDAO.setPwd(this.pwd);
		int i = utenteDAO.ScriviSuDB();
		
		return i;
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

	public Integer addRaccolta(String titolo, String descrizione) {
		
		//UTENTE È CREATOR DELLA RACCOLTA
		
		this.setId(SessioneUtente.getIdUtente());
		this.caricaRaccoltedaDB();
		EntityRaccolta raccolta=new EntityRaccolta();
		raccolta.setTitolo(titolo);
		raccolta.setDescrizione(descrizione);
		if(this.esisteRaccolta(raccolta)) {
			return -1;
		} else {
			int ret = raccolta.salvaSuDB(id);
			return ret;
		}
	}

	private void caricaRaccoltedaDB() {
		// TODO Auto-generated method stub
		RaccoltaDAO raccoltaDAO = new RaccoltaDAO();
		raccoltaDAO.setId_utente(id);
		ArrayList<RaccoltaDAO> lista_db_raccolte = raccoltaDAO.getRaccoltedaDB();
		
		for(int i = 0; i<lista_db_raccolte.size();i++) {
			EntityRaccolta raccolta_temp = new EntityRaccolta();
			raccolta_temp.setTitolo(lista_db_raccolte.get(i).getTitolo());
			raccolta_temp.setDescrizione(lista_db_raccolte.get(i).getDescrizione());
			
			raccolte.add(raccolta_temp);
			
		
		
	}
	}
	private static boolean esisteRaccolta(EntityRaccolta raccolta) {
        String titolo=raccolta.getTitolo();
        if (raccolte!=null) {
        	for(EntityRaccolta r : raccolte) {
        		if(r.getTitolo().equals(titolo)) {
        			return true;
        		}
        	}
        }
        return false;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAmministratore() {
		return amministratore;
	}

	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}

}
