package entity;

import java.sql.Date;

import database.ProfiloPersonaleDAO;

public class EntityProfiloPersonale {
	private String nickname;
	private String nome;
	private String cognome;
	private String immagineProfilo;
	private String biografia;
	private Date data_di_nascita;


	public EntityProfiloPersonale() {

	}

	public int scriviSuDB(int id_utente) {
		ProfiloPersonaleDAO profilo=new ProfiloPersonaleDAO();
		profilo.setId_utente(id_utente);
		profilo.setNickname(this.nickname);
		int res=profilo.SalvaInDB();
		return res;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getImmagineProfilo() {
		return immagineProfilo;
	}
	public void setImmagineProfilo(String immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	public String getBiografia() {
		return biografia;
	}
	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	public Date getData_di_nascita() {
		return data_di_nascita;
	}
	public void setData_di_nascita(Date data_di_nascita) {
		this.data_di_nascita = data_di_nascita;
	}


}
