package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/Choice.class */
public interface Choice {
    public static final int EXCLUSIVE = 1;
    public static final int MULTIPLE = 2;
    public static final int IMPLICIT = 3;
    public static final int POPUP = 4;
    public static final int TEXT_WRAP_ON = 1;
    public static final int TEXT_WRAP_OFF = 2;
    public static final int TEXT_WRAP_DEFAULT = 0;

    int append(String str, Image image);

    void delete(int i);

    void deleteAll();

    int getFitPolicy();

    Font getFont(int i);

    Image getImage(int i);

    int getSelectedFlags(boolean[] zArr);

    int getSelectedIndex();

    String getString(int i);

    void insert(int i, String str, Image image);

    boolean isSelected(int i);

    void set(int i, String str, Image image);

    void setFitPolicy(int i);

    void setFont(int i, Font font);

    void setSelectedFlags(boolean[] zArr);

    void setSelectedIndex(int i, boolean z);

    int size();
}
