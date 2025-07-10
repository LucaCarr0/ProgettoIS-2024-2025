package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ProfiloPersonaleDAO {
	private int id_utente;
	private String nickname;
	private String nome;
	private String cognome;
	private String biografia;
	private Date dataNascita;
	private String immagineProfilo;

	public ProfiloPersonaleDAO(){

	}

	public int SalvaInDB() {

		int ret = 0;

		/*String query = "INSERT INTO ProfiliPersonali(nome, cognome, immagineProfilo, biografia, nickname, dataNascita, utente) " +
	               "VALUES ('" + nome + "', '" + cognome + "', '" + immagineProfilo + "', '" + biografia +
	               "', '" + nickname + "', " + "NULL" +  ", '" + id_utente+"')";		*/
		String query = "INSERT INTO ProfiliPersonali(nome, cognome, immagineProfilo, biografia, nickname, dataNascita, utente) " +
	               "VALUES (NULL, NULL, NULL, NULL, '" + nickname + "', NULL, " + id_utente + ")";


		try {

			ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}

		return ret;
	}

	public int updateProfilo() {
	    
		int ret = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String dataFormattata = sdf.format(this.dataNascita);
		
	    
	    String query = "UPDATE ProfiliPersonali SET " +
	               "nome = '" + this.getNome() + "', " +
	               "cognome = '" + this.getCognome() + "', " +
	               "dataNascita = '" + dataFormattata + "', " +
	               "biografia = '" + this.getBiografia() + "' " +
	               "WHERE utente = " + this.getId_utente();

	    System.out.println(query);
		try {

			ret = DBConnectionManager.UpdateQuery(query);


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}

		return ret;
	    
	}
	

	

	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public ProfiloPersonaleDAO caricaProfiloUtente() {
		ProfiloPersonaleDAO profiloTrovato= new ProfiloPersonaleDAO();
		String query = "SELECT * FROM ProfiliPersonali WHERE utente = "+id_utente+";";

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { //finche ho un risultato

				profiloTrovato.setNickname(rs.getString("nickname"));
				profiloTrovato.setNome(rs.getString("nome"));
				profiloTrovato.setCognome(rs.getString("cognome"));
				profiloTrovato.setImmagineProfilo(rs.getString("immagineProfilo"));
				profiloTrovato.setBiografia(rs.getString("biografia"));
				profiloTrovato.setDataNascita(rs.getDate("dataNascita"));



			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return profiloTrovato;
	}




}
