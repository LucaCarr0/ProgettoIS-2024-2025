package facade;

import java.util.ArrayList;

import dto.PoesiaCompletaDTO;
import dto.PoesiaDTO;
import dto.ProfiloPersonaleDTO;
import entity.EntityPoesia;
import entity.EntityRaccolta;
import entity.EntityUtente;

public class FacadePoesie {

	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia, String autore) {
		EntityPoesia poesia= new EntityPoesia();
		PoesiaCompletaDTO poesiaDTO = poesia.visualizzaPoesia(id_poesia,autore);
        return poesiaDTO;
	}

	public static ArrayList<PoesiaDTO> getPoesieByRaccolta(int id_raccolta) {
		EntityRaccolta raccolta= new EntityRaccolta();
		ArrayList<PoesiaDTO> poesie = raccolta.getPoesieByRaccolta(id_raccolta);
		return poesie;
	}

}
