package entity;

import java.util.ArrayList;

import database.RaccoltaDAO;

public class EntityRaccolta {
	private int id;
	private String titolo;
	private String descrizione;
	private ArrayList<EntityPoesia> poesia;


	public EntityRaccolta() {

	}



	public int salvaSuDB(int id_utente) {

		RaccoltaDAO raccoltaDAO= new RaccoltaDAO();

		raccoltaDAO.setTitolo(this.titolo);
		raccoltaDAO.setDescrizione(this.descrizione);
		raccoltaDAO.setId_utente(id_utente);

		int i = raccoltaDAO.ScriviSuDB();

		return i;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public void aggiornaRaccolta(String titolo, String descrizione, int id_raccolta) {
		this.setTitolo(titolo);
		this.setDescrizione(descrizione);
		this.setId(id_raccolta);
	}



	public int aggiornaSuDB(int idUtente) {
		RaccoltaDAO raccoltaDao = new RaccoltaDAO();
		raccoltaDao.setTitolo(this.titolo);
		raccoltaDao.setDescrizione(this.descrizione);
		raccoltaDao.setId_utente(idUtente);
		raccoltaDao.setId(this.id);
		int res = raccoltaDao.updateRaccolta();
		return res;
	}


	public int eliminaDaDB() {
		RaccoltaDAO raccoltaDao = new RaccoltaDAO();
		raccoltaDao.setId(this.id);
		int res = raccoltaDao.deleteRaccolta();
		return res;
	}



	public void eliminaRaccolta(int id_raccolta) {
		this.setId(id_raccolta);
	}

}
