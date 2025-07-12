package entity;

import java.sql.Date;

public class EntityCommento implements Comparable<EntityCommento>{
	private String testo;
	private int id_poesia;
	private int id_autore;
	private Date dataPubblicazione;


	public EntityCommento() {

	}

	public int getId_poesia() {
		return id_poesia;
	}
	public void setId_poesia(int id_poesia) {
		this.id_poesia = id_poesia;
	}
	public int getId_autore() {
		return id_autore;
	}
	public void setId_autore(int id_autore) {
		this.id_autore = id_autore;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	@Override
	public int compareTo(EntityCommento o) {
	    if (this.dataPubblicazione == null && o.dataPubblicazione == null) {
	        return 0;
	    } else if (this.dataPubblicazione == null) {
	        return -1;
	    } else if (o.dataPubblicazione == null) {
	        return 1;
	    } else {
	    	//uso il compareTo di Date
	        return this.dataPubblicazione.compareTo(o.getDataPubblicazione());
	    }
	}


}
