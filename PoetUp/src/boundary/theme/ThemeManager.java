package boundary.theme;

import java.awt.Color;

public class ThemeManager {
    private static Theme currentTheme;
    private static ThemeType currentType;

    private static final Theme darkTheme = new Theme(
        new Color(151, 208, 232), new Color(113, 179, 211), new Color(25, 27, 35),
        new Color(67, 134, 168), new Color(66, 104, 124), new Color(51, 79, 98),
        new Color(101, 149, 164), new Color(41, 63, 78), new Color(92, 123, 139),
        new Color(60, 76, 84)
    );

    private static final Theme lightTheme = new Theme(
        new Color(201, 146, 82), new Color(78, 43, 35), new Color(158, 101, 55),
        new Color(120, 101, 47), new Color(99, 81, 40), new Color(30, 15, 13),
        new Color(170, 157, 118), new Color(111, 74, 84), new Color(68, 116, 228),
        new Color(180, 76, 20)
    );

    static {
        currentType = ThemeType.SCURO;  // tema di default
        currentTheme = darkTheme;
    }

    public static void setTheme(ThemeType type) {
        currentType = type;
        currentTheme = (type == ThemeType.SCURO) ? darkTheme : lightTheme;
    }

    public static Theme getTheme() {
        return currentTheme;
    }

    public static ThemeType getCurrentType() {
        return currentType;
    }
}
