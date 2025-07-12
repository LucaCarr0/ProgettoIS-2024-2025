package entity;

import database.ApprezzamentoDAO;

public class EntityApprezzamento {
	private int id_poesia;
	private int id_autore;
	private int id;

	public EntityApprezzamento() {

	}

	public int getId_poesia() {
		return id_poesia;
	}

	public void setId_poesia(int id_poesia) {
		this.id_poesia = id_poesia;
	}

	public int getId_autore() {
		return id_autore;
	}

	public void setId_autore(int id_autore) {
		this.id_autore = id_autore;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id_apprezzamento) {
		this.id = id_apprezzamento;
	}

	public int salvaSuDB(int idPoesia) {
		ApprezzamentoDAO likeDao=new ApprezzamentoDAO();
		likeDao.setId_utente(this.getId_autore());
		likeDao.setId_poesia(idPoesia);
		int res=likeDao.ScriviSuDB();
		System.out.println(idPoesia);
		return res;
	}

	public int eliminaDaDB() {
		ApprezzamentoDAO likeDao=new ApprezzamentoDAO();
		likeDao.setId_utente(this.getId_autore());
		likeDao.setId_poesia(this.getId_poesia());
		int ret = likeDao.eliminaDaDB();
		return ret;
	}

}
