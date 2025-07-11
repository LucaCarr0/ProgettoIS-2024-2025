package dto;

public class RaccoltaDTO {
	private String titolo;
	private String descrizione;
	private int id;


	public RaccoltaDTO(String titolo, String descrizione) {
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
	public RaccoltaDTO() {
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


}
