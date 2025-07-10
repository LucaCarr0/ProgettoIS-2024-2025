package entity;

import java.sql.Date;

import database.PoesiaDAO;
import session.SessioneUtente;

public class EntityPoesia implements Comparable<EntityPoesia>{
	private String titolo,testo,tag;
	private boolean visibilita;
	private String autore;
	private int id;
	private Date datapubblicazione;
	private int contatoreLike;
	//private ArrayList<EntityCommenti> commenti;
	//private ArrayList<EntityApprezzamenti> apprezzamenti;

	public EntityPoesia() {

	}

	public int salvaSuDB(int id_raccolta) {

		PoesiaDAO poesiaDAO= new PoesiaDAO();

		poesiaDAO.setTitolo(this.titolo);
		poesiaDAO.setAutore(SessioneUtente.getIdUtente());
		poesiaDAO.setContatoreLike(this.contatoreLike);
		poesiaDAO.setDatapubblicazione(datapubblicazione);
		poesiaDAO.setRaccolta(id_raccolta);
		poesiaDAO.setTag(tag);
		poesiaDAO.setTesto(testo);
		poesiaDAO.setVisibilita(visibilita);
		int i = poesiaDAO.ScriviSuDB();

		return i;
	}


	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isVisibilita() {
		return visibilita;
	}
	public void setVisibilita(boolean visibilita) {
		this.visibilita = visibilita;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getDatapubblicazione() {
		return datapubblicazione;
	}

	public void setDatapubblicazione(Date datapubblicazione) {
		this.datapubblicazione = datapubblicazione;
	}

	public int getContatoreLike() {
		return contatoreLike;
	}

	public void setContatoreLike(int contatoreLike) {
		this.contatoreLike = contatoreLike;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	@Override
	public int compareTo(EntityPoesia o) {
	    if (this.datapubblicazione == null && o.datapubblicazione == null) {
	        return 0;
	    } else if (this.datapubblicazione == null) {
	        return -1;
	    } else if (o.datapubblicazione == null) {
	        return 1;
	    } else {
	    	//uso il compareTo di Date
	        return this.datapubblicazione.compareTo(o.getDatapubblicazione());
	    }
	}








}
