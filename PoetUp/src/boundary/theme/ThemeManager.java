package boundary.theme;

import java.awt.Color;


//SINGLETON PER LA GESTIONE DEL TEMA NELL'APPLICAZIONE

public class ThemeManager {
    private static ThemeManager uniqueInstance;
    
    private Theme currentTheme;
    private ThemeType currentType;

    private final Theme darkTheme = new Theme(
        new Color(151, 208, 232), new Color(113, 179, 211), new Color(25, 27, 35),
        new Color(67, 134, 168), new Color(66, 104, 124), new Color(51, 79, 98),
        new Color(101, 149, 164), new Color(41, 63, 78), new Color(92, 123, 139),
        new Color(60, 76, 84)
    );

    private final Theme lightTheme = new Theme(
        new Color(201, 146, 82), new Color(78, 43, 35), new Color(158, 101, 55),
        new Color(120, 101, 47), new Color(99, 81, 40), new Color(30, 15, 13),
        new Color(170, 157, 118), new Color(111, 74, 84), new Color(68, 116, 228),
        new Color(180, 76, 20)
    );
    
    //COSTRUTTORE PRIVATO per non rendere possibile la creazione di un' altra istanza del theme manager
    
    private ThemeManager() {
        currentType = ThemeType.SCURO;  
        currentTheme = darkTheme;
    }

    public static ThemeManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ThemeManager();
        }
        return uniqueInstance;
    }

    public void setTheme(ThemeType type) {
        currentType = type;
        currentTheme = (type == ThemeType.SCURO) ? darkTheme : lightTheme;
    }

    public Theme getTheme() {
        return currentTheme;
    }

    public ThemeType getCurrentType() {
        return currentType;
    }
}
