# ProgettoIS-2024-2025  
Progetto per il corso di Ingegneria del Software | Università Federico II di Napoli

## Guida all’installazione e all’uso

1. **Download**  
   Scaricare il progetto come archivio `.zip` o clonare il repository. All’interno sarà presente una cartella già pronta per l'esecuzione.

2. **Contenuto della cartella**  
   All’interno troverai:
   - `PoetUp.exe`: il file eseguibile dell'applicazione.
   - `runtime/`: la JRE customizzata usata dall'eseguibile.
   - `app/`: la directory che contiene tutti i file `.jar` e le risorse necessarie all’avvio.  
     Tra queste risorse c'è anche un file `Properties.txt`, che va configurato nel seguente modo:
     - **Riga 1**: nome utente del database MySQL (di default è `root`)
     - **Riga 2**: password corrispondente a quell’utente

3. **Avvio dell’applicazione**  
   Fare doppio clic su `PoetUp.exe`. L'applicazione verrà eseguita automaticamente usando la JRE inclusa.  
   **Nota**: l’eseguibile è stato generato esclusivamente per sistemi **Windows** e non è compatibile con macOS o Linux.

4. **Requisiti di sistema**  
   - Sistema operativo **Windows** (64-bit)  
   - **MySQL** installato e attivo sul sistema  
   - Nessuna installazione di Java richiesta (la JRE è inclusa)  
   - Porta di default MySQL: **3306**
