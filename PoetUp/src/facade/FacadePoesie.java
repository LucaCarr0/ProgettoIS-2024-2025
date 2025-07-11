package facade;

import dto.PoesiaCompletaDTO;
import dto.ProfiloPersonaleDTO;
import entity.EntityPoesia;
import entity.EntityUtente;

public class FacadePoesie {

	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia, String autore) {
		EntityPoesia poesia= new EntityPoesia();
		PoesiaCompletaDTO poesiaDTO = poesia.visualizzaPoesia(id_poesia,autore);
        return poesiaDTO;
	}

}
