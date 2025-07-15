package dto;

public class PoesiaDTO {
    protected String titolo;
    protected String autore;
    protected int like;
    protected String data;
    protected String tag;
    protected String testo;
    protected int id;

    public PoesiaDTO() {}

    public PoesiaDTO(int id, String titolo, String autore, int like, String data, String tag, String testo) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.like = like;
        this.data = data;
        this.tag = tag;
        this.testo = testo;
    }

    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public int getLike() { return like; }
    public String getData() { return data; }
    public String getTag() { return tag; }
    public String getTesto() { return testo; }
	public int getId() {return id;}

	public void setTag(String tag) { this.tag = tag; }
    public void setTesto(String testo) { this.testo = testo; }
    public void setId(int id) { this.id = id; }
	public void setTitolo(String titolo) {this.titolo = titolo;}
	public void setAutore(String autore) {this.autore = autore;}
	public void setLike(int like) {this.like = like;}
	public void setData(String data) {this.data = data;}
}
