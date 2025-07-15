package session;

public class SessioneUtente {
	private static int idUtente;
	private static boolean amministratore;
	
	public static void login(int id, boolean isAdmin) {
		idUtente = id;
		amministratore = isAdmin;
	}

	public static int getIdUtente() {
		return idUtente;
	}

	public static boolean isAmministratore() {
		return amministratore;
	}

	public static void logout() {
		idUtente = 0;
		amministratore = false;
	}
}