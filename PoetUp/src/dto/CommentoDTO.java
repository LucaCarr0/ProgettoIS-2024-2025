package dto;

public class CommentoDTO {
	private String autore;
	private String testo;
	private String data;
	public CommentoDTO() {
		
	}
	public CommentoDTO(String autore, String testo, String data) {
		this.autore = autore;
		this.testo = testo;
		this.data = data;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
