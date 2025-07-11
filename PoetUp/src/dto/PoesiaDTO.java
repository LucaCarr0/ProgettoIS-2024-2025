package dto;

public class PoesiaDTO {
    private String titolo;
    private String autore;
    private int like;
    private String data;
    private int id;

    public PoesiaDTO(String titolo, String autore, int like, String data,int id) {
        this.titolo = titolo;
        this.autore = autore;
        this.like = like;
        this.data = data;
        this.id=id;
    }

    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public int getLike() { return like; }
    public String getData() { return data; }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
