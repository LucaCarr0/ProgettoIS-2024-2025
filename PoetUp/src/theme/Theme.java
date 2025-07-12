package boundary.theme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Theme {
    private final ArrayList<Color> palette;

    public Theme(Color... colors) {
        this.palette = new ArrayList<>(Arrays.asList(colors));
    }

    public ArrayList<Color> getPalette() {
        return palette;
    }

    public Color getTextPrimary() {
        return palette.get(0);
    }

    public Color getTextSecondary() {
        return palette.get(1);
    }

    public Color getBackgroundPrimary() {
        return palette.get(2);
    }

    public Color getBackgroundSecondary() {
        return palette.get(3);
    }

    public Color getBackgroundTertiary() {
        return palette.get(4);
    }

    public Color getBorderColor() {
        return palette.get(5);
    }

    public Color getAccentColor() {
        return palette.get(6);
    }

    public Color getHighlightColor() {
        return palette.get(7);
    }

    public Color getButtonColor() {
        return palette.get(8);
    }

    public Color getCardColor() {
        return palette.get(9);
    }
}
