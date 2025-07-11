package dto;

import java.util.ArrayList;

public class PoesiaCompletaDTO {
    private String titolo;
    private String autore;
    private String testo;
    private String tag;
    private int contatoreLike;
    private String stato;
    private ArrayList<CommentoDTO> ultimiCommenti;
    private boolean alreadyLiked;
    
    
    
	public PoesiaCompletaDTO(String titolo, String autore, String testo, String tag, int contatoreLike, String stato,
			ArrayList<CommentoDTO> ultimiCommenti, boolean alreadyLiked) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.testo = testo;
		this.tag = tag;
		this.contatoreLike = contatoreLike;
		this.stato = stato;
		this.ultimiCommenti = ultimiCommenti;
		this.alreadyLiked = alreadyLiked;
	}
	public boolean isAlreadyLiked() {
		return alreadyLiked;
	}
	public void setAlreadyLiked(boolean alreadyLiked) {
		this.alreadyLiked = alreadyLiked;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
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
	public int getContatoreLike() {
		return contatoreLike;
	}
	public void setContatoreLike(int contatoreLike) {
		this.contatoreLike = contatoreLike;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public ArrayList<CommentoDTO> getUltimiCommenti() {
		return ultimiCommenti;
	}
	public void setUltimiCommenti(ArrayList<CommentoDTO> ultimiCommenti) {
		this.ultimiCommenti = ultimiCommenti;
	}
    
    
}

