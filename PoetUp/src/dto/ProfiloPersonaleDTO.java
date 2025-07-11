package dto;

import java.sql.Date;

public class ProfiloPersonaleDTO {
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String nickname;
    private String bio;
    private String ImmagineProfilo;
    
    public ProfiloPersonaleDTO(String nome, String cognome, Date dataNascita, String nickname, String bio, String ImmagineProfilo) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.nickname = nickname;
        this.bio = bio;
        this.ImmagineProfilo = ImmagineProfilo;
    }
    
    

    public String getImmagineProfilo() {
        return ImmagineProfilo;
    }

    public void setImmagineProfilo(String ImmagineProfilo) {
        this.ImmagineProfilo = ImmagineProfilo;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}


}