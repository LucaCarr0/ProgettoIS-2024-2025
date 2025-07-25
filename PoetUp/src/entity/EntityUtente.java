package entity;

import java.sql.Date;
import java.util.ArrayList;

import database.CommentoDAO;
import database.PoesiaDAO;
import database.ProfiloPersonaleDAO;
import database.RaccoltaDAO;
import database.UtenteDAO;
import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;
import dto.StatisticheDTO;
import session.SessioneUtente;

public class EntityUtente {

	private String email;
	private String pwd;
	private int id;
	private boolean amministratore;
	private EntityProfiloPersonale profilo;
	protected static ArrayList<EntityRaccolta> raccolte = new ArrayList<>();

	public EntityUtente() {

		this.profilo = new EntityProfiloPersonale();
	}

	public int salvaSuDB() {

		UtenteDAO utenteDAO = new UtenteDAO();

		utenteDAO.setEmail(this.email);
		utenteDAO.setPwd(this.pwd);
		int i = utenteDAO.ScriviSuDB();

		return i;
	}

	public void inizializzaProfilo(int id_utente, String nickname) {

		// UTENTE È CREATOR DEL PROFILOPERSONALE
		profilo = new EntityProfiloPersonale();
		profilo.setNickname(nickname);
		int res = profilo.scriviSuDB(id_utente);
		if (res == -1) {
			System.out.println("errore nel salvataggio del profilo");
		}
	}

	public Integer addRaccolta(String titolo, String descrizione) {

		// UTENTE È CREATOR DELLA RACCOLTA

		this.setId(SessioneUtente.getIdUtente());
		this.caricaRaccoltedaDB();
		EntityRaccolta raccolta = new EntityRaccolta();
		raccolta.setTitolo(titolo);
		raccolta.setDescrizione(descrizione);
		if (EntityUtente.esisteRaccolta(raccolta)) {
			return -1;
		} else {
			int ret = raccolta.salvaSuDB(id);
			return ret;
		}
	}

	public Integer pubblicazionePoesia(String titolo, String testo, String tag, String raccolta, boolean visibilita) {
		this.setId(SessioneUtente.getIdUtente());
		this.caricaRaccoltedaDB();
		EntityPoesia poesia = new EntityPoesia();
		poesia.setTitolo(titolo);
		ArrayList<EntityPoesia> lista_poesie_utente = this.caricaPoesiedaDB();

		if (esistePoesiainUtente(poesia, lista_poesie_utente)) {
			return -1;
		}
		EntityRaccolta raccoltaEntity = new EntityRaccolta();
		raccoltaEntity.setTitolo(raccolta);
		if (!esisteRaccolta(raccoltaEntity)) {
			raccoltaEntity.setId(addRaccolta(raccolta, "raccolta aggiunta dalla pubblicazione di una poesia"));
		}

		poesia.setTag(tag);
		poesia.setTesto(testo);
		poesia.setVisibilita(visibilita);
		poesia.setContatoreLike(0);
		poesia.setDatapubblicazione(new Date(System.currentTimeMillis()));
		int ret = poesia.salvaSuDB(raccoltaEntity.getId());
		return ret;

	}

	public Integer modificaProfilo(String nome, String cognome, Date dataNascita, String biografia,
			String immaginePath) {
		// this.setId(SessioneUtente.getIdUtente());
		profilo = new EntityProfiloPersonale();
		int idUtente = SessioneUtente.getIdUtente();

		profilo.aggiornaProfilo(nome, cognome, dataNascita, biografia, immaginePath);
		int res = profilo.aggiornaSuDB(idUtente);

		return res;
	}

	protected void caricaRaccoltedaDB() {
		// TODO Auto-generated method stub
		RaccoltaDAO raccoltaDAO = new RaccoltaDAO();
		raccoltaDAO.setId_utente(id);
		ArrayList<RaccoltaDAO> lista_db_raccolte = raccoltaDAO.getRaccoltedaDB();

		for (int i = 0; i < lista_db_raccolte.size(); i++) {
			EntityRaccolta raccolta_temp = new EntityRaccolta();
			raccolta_temp.setTitolo(lista_db_raccolte.get(i).getTitolo());
			raccolta_temp.setDescrizione(lista_db_raccolte.get(i).getDescrizione());
			raccolta_temp.setId(lista_db_raccolte.get(i).getId());

			raccolte.add(raccolta_temp);

		}
	}

	private ArrayList<EntityPoesia> caricaPoesiedaDB() {
		// TODO Auto-generated method stub
		ArrayList<EntityPoesia> lista_poesie_utente = new ArrayList<>();

		PoesiaDAO poesiaDAO = new PoesiaDAO();
		poesiaDAO.setAutore(id);
		ArrayList<PoesiaDAO> lista_db_poesie_utente = poesiaDAO.getPoesieUtentedaDB();

		for (int i = 0; i < lista_db_poesie_utente.size(); i++) {
			EntityPoesia poesia_temp = new EntityPoesia();
			poesia_temp.setTag(lista_db_poesie_utente.get(i).getTag());
			poesia_temp.setTesto(lista_db_poesie_utente.get(i).getTesto());
			poesia_temp.setTitolo(lista_db_poesie_utente.get(i).getTitolo());
			poesia_temp.setVisibilita(lista_db_poesie_utente.get(i).isVisibilita());
			poesia_temp.setContatoreLike(lista_db_poesie_utente.get(i).getContatoreLike());
			poesia_temp.setDatapubblicazione(lista_db_poesie_utente.get(i).getDatapubblicazione());

			lista_poesie_utente.add(poesia_temp);

		}
		return lista_poesie_utente;

	}

	private static boolean esisteRaccolta(EntityRaccolta raccolta) {
		String titolo = raccolta.getTitolo();
		if (raccolte != null) {
			for (EntityRaccolta r : raccolte) {
				if (r.getTitolo().equals(titolo)) {
					raccolta.setId(r.getId());
					return true;
				}
			}
		}
		return false;
	}

	private boolean esistePoesiainUtente(EntityPoesia poesia, ArrayList<EntityPoesia> lista_poesie_utente) {
		String titolo = poesia.getTitolo();
		if (lista_poesie_utente != null) {
			for (EntityPoesia p : lista_poesie_utente) {
				if (p.getTitolo().equals(titolo)) {
					poesia.setId(p.getId());
					return true;
				}
			}
		}
		return false;
	}

	public StatisticheDTO getStatistiche() {
		StatisticheDTO statistiche = new StatisticheDTO();
		PoesiaDAO poesiaDAO = new PoesiaDAO();
		poesiaDAO.setAutore(SessioneUtente.getIdUtente());
		ArrayList<PoesiaDAO> poesieUtente = poesiaDAO.getPoesieUtentedaDB();
		calcolaStatistiche(poesieUtente, statistiche);
		caricaCommenti(poesieUtente, statistiche);
		return statistiche;
	}

	private void calcolaStatistiche(ArrayList<PoesiaDAO> poesie, StatisticheDTO stat) {
		int totaleApprezzamenti = 0;
		if (poesie.isEmpty()) {
			stat.setTotaleApprezzamenti(0);
			stat.setTitoloPoesiaPiuApprezzata("Nessuna poesia trovata.");
			stat.setTestoPoesiaPiuApprezzata("");
			stat.setLikePoesiaPiuApprezzata(0);
			return;
		}
		PoesiaDAO poesiaPiuApprezzata = poesie.get(0);
		for (PoesiaDAO poesia : poesie) {
			totaleApprezzamenti += poesia.getContatoreLike();
			if (poesia.getContatoreLike() > poesiaPiuApprezzata.getContatoreLike()) {
				poesiaPiuApprezzata = poesia;
			}
		}

		stat.setTotaleApprezzamenti(totaleApprezzamenti);
		stat.setTitoloPoesiaPiuApprezzata(poesiaPiuApprezzata.getTitolo());
		stat.setTestoPoesiaPiuApprezzata(poesiaPiuApprezzata.getTesto());
		stat.setLikePoesiaPiuApprezzata(poesiaPiuApprezzata.getContatoreLike());
	}

	private void caricaCommenti(ArrayList<PoesiaDAO> poesie, StatisticheDTO stat) {
		if (poesie.isEmpty()) {
			stat.setTotaleCommenti(0);
			return;
		}
		int totaleCommenti = 0;
		for (PoesiaDAO poesia : poesie) {
			CommentoDAO commentoDAO = new CommentoDAO();
			commentoDAO.setId_poesia(poesia.getId());
			ArrayList<CommentoDAO> commentiPoesia = commentoDAO.caricadaDB();
			totaleCommenti += commentiPoesia.size();
		}
		stat.setTotaleCommenti(totaleCommenti);
	}

	public String getNickdaDB() {
		ProfiloPersonaleDAO profiloDAO = new ProfiloPersonaleDAO();
		profiloDAO.setId_utente(id);
		ProfiloPersonaleDAO profiloTrovato = profiloDAO.caricaProfiloUtente();
		return profiloTrovato.getNickname();
	}

	public ProfiloPersonaleDTO getProfiloUtente() {
		ProfiloPersonaleDAO profiloDAO = new ProfiloPersonaleDAO();
		profiloDAO.setId_utente(SessioneUtente.getIdUtente());
		ProfiloPersonaleDAO profiloTrovato = profiloDAO.caricaProfiloUtente();
		ProfiloPersonaleDTO profilo = new ProfiloPersonaleDTO(profiloTrovato.getNome(), profiloTrovato.getCognome(),
				profiloTrovato.getDataNascita(), profiloTrovato.getNickname(), profiloTrovato.getBiografia(),
				profiloTrovato.getImmagineProfilo());

		return profilo;

	}

	public ArrayList<RaccoltaDTO> getRaccolteByUtente() {
		EntityUtente.raccolte.clear(); // se no al ricaricamento della pagina si accumulano duplicati
		this.setId(SessioneUtente.getIdUtente());
		this.caricaRaccoltedaDB();
		ArrayList<RaccoltaDTO> raccolte_dto = new ArrayList<>();

		for (EntityRaccolta raccolta : raccolte) {
			RaccoltaDTO raccolta_temp = new RaccoltaDTO();
			raccolta_temp.setDescrizione(raccolta.getDescrizione());
			raccolta_temp.setTitolo(raccolta.getTitolo());
			raccolta_temp.setId(raccolta.getId());

			raccolte_dto.add(raccolta_temp);
		}
		return raccolte_dto;
	}

	public EntityProfiloPersonale getProfilo() {
		return profilo;
	}

	public void setProfilo(EntityProfiloPersonale profilo) {
		this.profilo = profilo;
	}

	public static ArrayList<EntityRaccolta> getRaccolte() {
		return raccolte;
	}

	public static void setRaccolte(ArrayList<EntityRaccolta> raccolte) {
		EntityUtente.raccolte = raccolte;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAmministratore() {
		return amministratore;
	}

	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}

}
