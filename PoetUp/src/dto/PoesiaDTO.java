package dto;

public class PoesiaDTO {
    private String titolo;
    private String autore;
    private int like;
    private String data;

    public PoesiaDTO(String titolo, String autore, int like, String data) {
        this.titolo = titolo;
        this.autore = autore;
        this.like = like;
        this.data = data;
    }

    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public int getLike() { return like; }
    public String getData() { return data; }
}
