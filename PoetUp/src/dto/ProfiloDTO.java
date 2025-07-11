package dto;

import java.sql.Date;

public class ProfiloDTO {
	private String nome;
	private String nickname;
    private String cognome;
    private Date dataNascita;
    private String immagine;
    private String biografia;

    public ProfiloDTO() {

    }

    public ProfiloDTO(String nome, String cognome, Date dataNascita, String immagine, String biografia) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.immagine = immagine;
        this.biografia = biografia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setNickname(String nickname) {
    	this.nickname = nickname;
    }
}
