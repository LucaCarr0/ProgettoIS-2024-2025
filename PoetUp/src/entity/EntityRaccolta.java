package entity;

import database.RaccoltaDAO;
import database.UtenteDAO;

public class EntityRaccolta {
	private String titolo;
	private String descrizione;
	//private ArrayList<EntityPoesia> poesia;
	
	
	public EntityRaccolta() {
		
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int salvaSuDB(int id_utente) {
		
		RaccoltaDAO raccoltaDAO= new RaccoltaDAO(); 
		
		raccoltaDAO.setTitolo(this.titolo);
		raccoltaDAO.setDescrizione(this.descrizione);
		raccoltaDAO.setId_utente(id_utente);

		int i = raccoltaDAO.ScriviSuDB();
		
		return i;
	}
	
	
}
