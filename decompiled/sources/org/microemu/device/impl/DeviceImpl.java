package org.microemu.device.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import nanoxml.XMLElement;
import nanoxml.XMLParseException;
import org.microemu.EmulatorContext;
import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;

/* loaded from: avacs.jar:org/microemu/device/impl/DeviceImpl.class */
public abstract class DeviceImpl implements Device {
    private String name;
    private static EmulatorContext context;
    private Image normalImage;
    private Image overImage;
    private Image pressedImage;
    private boolean hasPointerEvents;
    private boolean hasPointerMotionEvents;
    private boolean hasRepeatEvents;
    private int skinVersion;
    public static final String DEFAULT_LOCATION = "org/microemu/device/default/device.xml";
    public static final String RESIZABLE_LOCATION = "org/microemu/device/resizable/device.xml";
    private String descriptorLocation;
    private static Map specialInheritanceAttributeSet;
    private Map systemProperties = new HashMap();
    private Vector buttons = new Vector();
    private Vector softButtons = new Vector();

    /* JADX WARN: Code restructure failed: missing block: B:18:0x007f, code lost:
    
        if (r10 != null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0082, code lost:
    
        r10 = (org.microemu.device.impl.DeviceImpl) r8.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008e, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x009c, code lost:
    
        throw new java.io.IOException(r11.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009d, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ab, code lost:
    
        throw new java.io.IOException(r11.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ac, code lost:
    
        org.microemu.device.impl.DeviceImpl.context = r5;
        r10.descriptorLocation = r7;
        r10.loadConfig(r6, besourceBase(r7), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c4, code lost:
    
        return r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.microemu.device.impl.DeviceImpl create(org.microemu.EmulatorContext r5, java.lang.ClassLoader r6, java.lang.String r7, java.lang.Class r8) throws java.io.IOException, java.lang.ClassNotFoundException, java.lang.NumberFormatException {
        /*
            r0 = r6
            r1 = r7
            nanoxml.XMLElement r0 = loadDeviceDescriptor(r0, r1)
            r9 = r0
            r0 = 0
            r10 = r0
            r0 = r9
            java.util.Enumeration r0 = r0.enumerateChildren()
            r11 = r0
            goto L73
        L14:
            r0 = r11
            java.lang.Object r0 = r0.nextElement()
            nanoxml.XMLElement r0 = (nanoxml.XMLElement) r0
            r12 = r0
            r0 = r12
            java.lang.String r0 = r0.getName()
            java.lang.String r1 = "class-name"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L73
            r0 = r12
            java.lang.String r0 = r0.getContent()     // Catch: java.lang.ClassNotFoundException -> L46 java.lang.InstantiationException -> L55 java.lang.IllegalAccessException -> L64
            r1 = 1
            r2 = r6
            java.lang.Class r0 = java.lang.Class.forName(r0, r1, r2)     // Catch: java.lang.ClassNotFoundException -> L46 java.lang.InstantiationException -> L55 java.lang.IllegalAccessException -> L64
            r13 = r0
            r0 = r13
            java.lang.Object r0 = r0.newInstance()     // Catch: java.lang.ClassNotFoundException -> L46 java.lang.InstantiationException -> L55 java.lang.IllegalAccessException -> L64
            org.microemu.device.impl.DeviceImpl r0 = (org.microemu.device.impl.DeviceImpl) r0     // Catch: java.lang.ClassNotFoundException -> L46 java.lang.InstantiationException -> L55 java.lang.IllegalAccessException -> L64
            r10 = r0
            goto L7d
        L46:
            r13 = move-exception
            java.io.IOException r0 = new java.io.IOException
            r1 = r0
            r2 = r13
            java.lang.String r2 = r2.getMessage()
            r1.<init>(r2)
            throw r0
        L55:
            r13 = move-exception
            java.io.IOException r0 = new java.io.IOException
            r1 = r0
            r2 = r13
            java.lang.String r2 = r2.getMessage()
            r1.<init>(r2)
            throw r0
        L64:
            r13 = move-exception
            java.io.IOException r0 = new java.io.IOException
            r1 = r0
            r2 = r13
            java.lang.String r2 = r2.getMessage()
            r1.<init>(r2)
            throw r0
        L73:
            r0 = r11
            boolean r0 = r0.hasMoreElements()
            if (r0 != 0) goto L14
        L7d:
            r0 = r10
            if (r0 != 0) goto Lac
            r0 = r8
            java.lang.Object r0 = r0.newInstance()     // Catch: java.lang.InstantiationException -> L8e java.lang.IllegalAccessException -> L9d
            org.microemu.device.impl.DeviceImpl r0 = (org.microemu.device.impl.DeviceImpl) r0     // Catch: java.lang.InstantiationException -> L8e java.lang.IllegalAccessException -> L9d
            r10 = r0
            goto Lac
        L8e:
            r11 = move-exception
            java.io.IOException r0 = new java.io.IOException
            r1 = r0
            r2 = r11
            java.lang.String r2 = r2.getMessage()
            r1.<init>(r2)
            throw r0
        L9d:
            r11 = move-exception
            java.io.IOException r0 = new java.io.IOException
            r1 = r0
            r2 = r11
            java.lang.String r2 = r2.getMessage()
            r1.<init>(r2)
            throw r0
        Lac:
            r0 = r5
            org.microemu.device.impl.DeviceImpl.context = r0
            r0 = r10
            r1 = r7
            r0.descriptorLocation = r1
            r0 = r10
            r1 = r6
            r2 = r7
            java.lang.String r2 = besourceBase(r2)
            r3 = r9
            r0.loadConfig(r1, r2, r3)
            r0 = r10
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.microemu.device.impl.DeviceImpl.create(org.microemu.EmulatorContext, java.lang.ClassLoader, java.lang.String, java.lang.Class):org.microemu.device.impl.DeviceImpl");
    }

    @Override // org.microemu.device.Device
    public void init() {
    }

    public void init(EmulatorContext context2) {
        init(context2, DEFAULT_LOCATION);
    }

    public void init(EmulatorContext context2, String descriptorLocation) {
        context = context2;
        if (descriptorLocation.startsWith("/")) {
            this.descriptorLocation = descriptorLocation.substring(1);
        } else {
            this.descriptorLocation = descriptorLocation;
        }
        try {
            String base = descriptorLocation.substring(0, descriptorLocation.lastIndexOf("/"));
            XMLElement doc = loadDeviceDescriptor(getClass().getClassLoader(), descriptorLocation);
            loadConfig(getClass().getClassLoader(), base, doc);
        } catch (IOException ex) {
            System.out.println("Cannot load config: " + ex);
        }
    }

    public String getDescriptorLocation() {
        return this.descriptorLocation;
    }

    @Override // org.microemu.device.Device
    public void destroy() {
    }

    @Override // org.microemu.device.Device
    public String getName() {
        return this.name;
    }

    public static EmulatorContext getEmulatorContext() {
        return context;
    }

    @Override // org.microemu.device.Device
    public InputMethod getInputMethod() {
        return context.getDeviceInputMethod();
    }

    @Override // org.microemu.device.Device
    public FontManager getFontManager() {
        return context.getDeviceFontManager();
    }

    @Override // org.microemu.device.Device
    public DeviceDisplay getDeviceDisplay() {
        return context.getDeviceDisplay();
    }

    @Override // org.microemu.device.Device
    public Image getNormalImage() {
        return this.normalImage;
    }

    @Override // org.microemu.device.Device
    public Image getOverImage() {
        return this.overImage;
    }

    @Override // org.microemu.device.Device
    public Image getPressedImage() {
        return this.pressedImage;
    }

    @Override // org.microemu.device.Device
    public Vector getSoftButtons() {
        return this.softButtons;
    }

    @Override // org.microemu.device.Device
    public Vector getButtons() {
        return this.buttons;
    }

    protected void loadConfig(ClassLoader classLoader, String base, XMLElement doc) throws IOException, NumberFormatException {
        String deviceName = doc.getStringAttribute("name");
        if (deviceName != null) {
            this.name = deviceName;
        } else {
            this.name = base;
        }
        loadSkinVersion(doc);
        this.hasPointerEvents = false;
        this.hasPointerMotionEvents = false;
        this.hasRepeatEvents = false;
        ((FontManagerImpl) getFontManager()).setAntialiasing(false);
        parseDisplay(classLoader, base, doc.getChild("display"));
        Enumeration e = doc.enumerateChildren();
        while (e.hasMoreElements()) {
            XMLElement tmp = (XMLElement) e.nextElement();
            if (tmp.getName().equals("system-properties")) {
                parseSystemProperties(tmp);
            } else if (tmp.getName().equals("img") && !((DeviceDisplayImpl) getDeviceDisplay()).isResizable()) {
                try {
                    if (tmp.getStringAttribute("name").equals("normal")) {
                        this.normalImage = loadImage(classLoader, base, tmp.getStringAttribute("src"));
                    } else if (tmp.getStringAttribute("name").equals("over")) {
                        this.overImage = loadImage(classLoader, base, tmp.getStringAttribute("src"));
                    } else if (tmp.getStringAttribute("name").equals("pressed")) {
                        this.pressedImage = loadImage(classLoader, base, tmp.getStringAttribute("src"));
                    }
                } catch (IOException e2) {
                    System.out.println("Cannot load " + tmp.getStringAttribute("src"));
                    return;
                }
            } else if (tmp.getName().equals("fonts")) {
                parseFonts(classLoader, base, tmp);
            } else if (tmp.getName().equals("input") || tmp.getName().equals("keyboard")) {
                parseInput(tmp);
            }
        }
    }

    private void loadSkinVersion(XMLElement doc) {
        String xmlns = doc.getStringAttribute("xmlns");
        if (xmlns == null) {
            this.skinVersion = 20000;
        } else if (xmlns.endsWith("/2.0.2/")) {
            this.skinVersion = Button.NAME_RIMARY_SINCE_SKIN_VERSION;
        } else {
            this.skinVersion = 20000;
        }
    }

    private void parseDisplay(ClassLoader classLoader, String base, XMLElement tmp) throws IOException {
        DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) getDeviceDisplay();
        String resizable = tmp.getStringAttribute("resizable", "false");
        if (resizable.equalsIgnoreCase("true")) {
            deviceDisplay.setResizable(true);
        } else {
            deviceDisplay.setResizable(false);
        }
        Enumeration e_display = tmp.enumerateChildren();
        while (e_display.hasMoreElements()) {
            XMLElement tmp_display = (XMLElement) e_display.nextElement();
            if (tmp_display.getName().equals("numcolors")) {
                deviceDisplay.setNumColors(Integer.parseInt(tmp_display.getContent()));
            } else if (tmp_display.getName().equals("iscolor")) {
                deviceDisplay.setIsColor(parseBoolean(tmp_display.getContent()));
            } else if (tmp_display.getName().equals("numalphalevels")) {
                deviceDisplay.setNumAlphaLevels(Integer.parseInt(tmp_display.getContent()));
            } else if (tmp_display.getName().equals("background")) {
                deviceDisplay.setBackgroundColor(new Color(Integer.parseInt(tmp_display.getContent(), 16)));
            } else if (tmp_display.getName().equals("foreground")) {
                deviceDisplay.setForegroundColor(new Color(Integer.parseInt(tmp_display.getContent(), 16)));
            } else if (tmp_display.getName().equals("rectangle")) {
                Rectangle rect = getRectangle(tmp_display);
                if (deviceDisplay.isResizable()) {
                    rect.x = 0;
                    rect.y = 0;
                }
                deviceDisplay.setDisplayRectangle(rect);
            } else if (tmp_display.getName().equals("paintable")) {
                deviceDisplay.setDisplayPaintable(getRectangle(tmp_display));
            }
        }
        Enumeration e_display2 = tmp.enumerateChildren();
        while (e_display2.hasMoreElements()) {
            XMLElement tmp_display2 = (XMLElement) e_display2.nextElement();
            if (tmp_display2.getName().equals("img")) {
                if (tmp_display2.getStringAttribute("name").equals("up") || tmp_display2.getStringAttribute("name").equals("down")) {
                    SoftButton icon = deviceDisplay.createSoftButton(this.skinVersion, tmp_display2.getStringAttribute("name"), getRectangle(tmp_display2.getChild("paintable")), loadImage(classLoader, base, tmp_display2.getStringAttribute("src")), loadImage(classLoader, base, tmp_display2.getStringAttribute("src")));
                    getSoftButtons().addElement(icon);
                } else if (tmp_display2.getStringAttribute("name").equals("mode")) {
                    if (tmp_display2.getStringAttribute("type").equals("123")) {
                        deviceDisplay.setMode123Image(new PositionedImage(loadImage(classLoader, base, tmp_display2.getStringAttribute("src")), getRectangle(tmp_display2.getChild("paintable"))));
                    } else if (tmp_display2.getStringAttribute("type").equals("abc")) {
                        deviceDisplay.setModeAbcLowerImage(new PositionedImage(loadImage(classLoader, base, tmp_display2.getStringAttribute("src")), getRectangle(tmp_display2.getChild("paintable"))));
                    } else if (tmp_display2.getStringAttribute("type").equals("ABC")) {
                        deviceDisplay.setModeAbcUpperImage(new PositionedImage(loadImage(classLoader, base, tmp_display2.getStringAttribute("src")), getRectangle(tmp_display2.getChild("paintable"))));
                    }
                }
            } else if (tmp_display2.getName().equals("icon")) {
                Image iconNormalImage = null;
                Image iconPressedImage = null;
                Enumeration e_icon = tmp_display2.enumerateChildren();
                while (e_icon.hasMoreElements()) {
                    XMLElement tmp_icon = (XMLElement) e_icon.nextElement();
                    if (tmp_icon.getName().equals("img")) {
                        if (tmp_icon.getStringAttribute("name").equals("normal")) {
                            iconNormalImage = loadImage(classLoader, base, tmp_icon.getStringAttribute("src"));
                        } else if (tmp_icon.getStringAttribute("name").equals("pressed")) {
                            iconPressedImage = loadImage(classLoader, base, tmp_icon.getStringAttribute("src"));
                        }
                    }
                }
                SoftButton icon2 = deviceDisplay.createSoftButton(this.skinVersion, tmp_display2.getStringAttribute("name"), getRectangle(tmp_display2.getChild("paintable")), iconNormalImage, iconPressedImage);
                getSoftButtons().addElement(icon2);
            } else if (tmp_display2.getName().equals("status") && tmp_display2.getStringAttribute("name").equals("input")) {
                Rectangle paintable = getRectangle(tmp_display2.getChild("paintable"));
                Enumeration e_status = tmp_display2.enumerateChildren();
                while (e_status.hasMoreElements()) {
                    XMLElement tmp_status = (XMLElement) e_status.nextElement();
                    if (tmp_status.getName().equals("img")) {
                        if (tmp_status.getStringAttribute("name").equals("123")) {
                            deviceDisplay.setMode123Image(new PositionedImage(loadImage(classLoader, base, tmp_status.getStringAttribute("src")), paintable));
                        } else if (tmp_status.getStringAttribute("name").equals("abc")) {
                            deviceDisplay.setModeAbcLowerImage(new PositionedImage(loadImage(classLoader, base, tmp_status.getStringAttribute("src")), paintable));
                        } else if (tmp_status.getStringAttribute("name").equals("ABC")) {
                            deviceDisplay.setModeAbcUpperImage(new PositionedImage(loadImage(classLoader, base, tmp_status.getStringAttribute("src")), paintable));
                        }
                    }
                }
            }
        }
    }

    private void parseFonts(ClassLoader classLoader, String base, XMLElement tmp) throws NumberFormatException, IOException {
        FontManagerImpl fontManager = (FontManagerImpl) getFontManager();
        String hint = tmp.getStringAttribute("hint");
        boolean antialiasing = false;
        if (hint != null && hint.equals("antialiasing")) {
            antialiasing = true;
        }
        fontManager.setAntialiasing(antialiasing);
        Enumeration e_fonts = tmp.enumerateChildren();
        while (e_fonts.hasMoreElements()) {
            XMLElement tmp_font = (XMLElement) e_fonts.nextElement();
            if (tmp_font.getName().equals("font")) {
                String face = tmp_font.getStringAttribute("face").toLowerCase();
                String style = tmp_font.getStringAttribute("style").toLowerCase();
                String size = tmp_font.getStringAttribute("size").toLowerCase();
                if (face.startsWith("face_")) {
                    face = face.substring("face_".length());
                }
                if (style.startsWith("style_")) {
                    style = style.substring("style_".length());
                }
                if (size.startsWith("size_")) {
                    size = size.substring("size_".length());
                }
                Enumeration e_defs = tmp_font.enumerateChildren();
                while (e_defs.hasMoreElements()) {
                    XMLElement tmp_def = (XMLElement) e_defs.nextElement();
                    if (tmp_def.getName().equals("system")) {
                        String defName = tmp_def.getStringAttribute("name");
                        String defStyle = tmp_def.getStringAttribute("style");
                        int defSize = Integer.parseInt(tmp_def.getStringAttribute("size"));
                        fontManager.setFont(face, style, size, fontManager.createSystemFont(defName, defStyle, defSize, antialiasing));
                    } else if (tmp_def.getName().equals("ttf")) {
                        String defSrc = tmp_def.getStringAttribute("src");
                        String defStyle2 = tmp_def.getStringAttribute("style");
                        int defSize2 = Integer.parseInt(tmp_def.getStringAttribute("size"));
                        fontManager.setFont(face, style, size, fontManager.createTrueTypeFont(getResourceUrl(classLoader, base, defSrc), defStyle2, defSize2, antialiasing));
                    }
                }
            }
        }
    }

    private void parseInput(XMLElement tmp) {
        DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) getDeviceDisplay();
        boolean resizable = deviceDisplay.isResizable();
        Enumeration e_keyboard = tmp.enumerateChildren();
        while (e_keyboard.hasMoreElements()) {
            XMLElement tmp_keyboard = (XMLElement) e_keyboard.nextElement();
            if (tmp_keyboard.getName().equals("haspointerevents")) {
                this.hasPointerEvents = parseBoolean(tmp_keyboard.getContent());
            } else if (tmp_keyboard.getName().equals("haspointermotionevents")) {
                this.hasPointerMotionEvents = parseBoolean(tmp_keyboard.getContent());
            } else if (tmp_keyboard.getName().equals("hasrepeatevents")) {
                this.hasRepeatEvents = parseBoolean(tmp_keyboard.getContent());
            } else if (tmp_keyboard.getName().equals("button")) {
                Shape shape = null;
                Hashtable inputToChars = new Hashtable();
                Enumeration e_button = tmp_keyboard.enumerateChildren();
                while (e_button.hasMoreElements()) {
                    XMLElement tmp_button = (XMLElement) e_button.nextElement();
                    if (tmp_button.getName().equals("chars")) {
                        String input = tmp_button.getStringAttribute("input", "common");
                        Vector stringArray = new Vector();
                        Enumeration e_chars = tmp_button.enumerateChildren();
                        while (e_chars.hasMoreElements()) {
                            XMLElement tmp_chars = (XMLElement) e_chars.nextElement();
                            if (tmp_chars.getName().equals("char")) {
                                stringArray.addElement(tmp_chars.getContent());
                            }
                        }
                        char[] charArray = new char[stringArray.size()];
                        for (int i = 0; i < stringArray.size(); i++) {
                            String str = (String) stringArray.elementAt(i);
                            if (str.length() > 0) {
                                charArray[i] = str.charAt(0);
                            } else {
                                charArray[i] = ' ';
                            }
                        }
                        inputToChars.put(input, charArray);
                    } else if (tmp_button.getName().equals("rectangle") && !resizable) {
                        shape = getRectangle(tmp_button);
                    } else if (tmp_button.getName().equals("polygon") && !resizable) {
                        shape = getPolygon(tmp_button);
                    }
                }
                int keyCode = tmp_keyboard.getIntAttribute("keyCode", Integer.MIN_VALUE);
                getButtons().addElement(deviceDisplay.createButton(this.skinVersion, tmp_keyboard.getStringAttribute("name"), shape, keyCode, tmp_keyboard.getStringAttribute("key"), tmp_keyboard.getStringAttribute("keyboardChars"), inputToChars, tmp_keyboard.getBooleanAttribute("modeChange", false)));
            } else if (tmp_keyboard.getName().equals("softbutton")) {
                Vector commands = new Vector();
                Shape shape2 = null;
                Rectangle paintable = null;
                javax.microedition.lcdui.Font font = null;
                Enumeration e_button2 = tmp_keyboard.enumerateChildren();
                while (e_button2.hasMoreElements()) {
                    XMLElement tmp_button2 = (XMLElement) e_button2.nextElement();
                    if (tmp_button2.getName().equals("rectangle") && !resizable) {
                        shape2 = getRectangle(tmp_button2);
                    } else if (tmp_button2.getName().equals("polygon") && !resizable) {
                        shape2 = getPolygon(tmp_button2);
                    } else if (tmp_button2.getName().equals("paintable")) {
                        paintable = getRectangle(tmp_button2);
                    } else if (tmp_button2.getName().equals("command")) {
                        commands.addElement(tmp_button2.getContent());
                    } else if (tmp_button2.getName().equals("font")) {
                        font = getFont(tmp_button2.getStringAttribute("face"), tmp_button2.getStringAttribute("style"), tmp_button2.getStringAttribute("size"));
                    }
                }
                int keyCode2 = tmp_keyboard.getIntAttribute("keyCode", Integer.MIN_VALUE);
                SoftButton button = deviceDisplay.createSoftButton(this.skinVersion, tmp_keyboard.getStringAttribute("name"), shape2, keyCode2, tmp_keyboard.getStringAttribute("key"), paintable, tmp_keyboard.getStringAttribute("alignment"), commands, font);
                getButtons().addElement(button);
                getSoftButtons().addElement(button);
            }
        }
    }

    private void parseSystemProperties(XMLElement tmp) {
        Enumeration e_prop = tmp.enumerateChildren();
        while (e_prop.hasMoreElements()) {
            XMLElement tmp_prop = (XMLElement) e_prop.nextElement();
            if (tmp_prop.getName().equals("system-property")) {
                this.systemProperties.put(tmp_prop.getStringAttribute("name"), tmp_prop.getStringAttribute("value"));
            }
        }
    }

    private static javax.microedition.lcdui.Font getFont(String face, String style, String size) {
        int meFace = 0;
        if (face.equalsIgnoreCase("system")) {
            meFace = 0 | 0;
        } else if (face.equalsIgnoreCase("monospace")) {
            meFace = 0 | 32;
        } else if (face.equalsIgnoreCase("proportional")) {
            meFace = 0 | 64;
        }
        int meStyle = 0;
        String testStyle = style.toLowerCase();
        if (testStyle.indexOf("plain") != -1) {
            meStyle = 0 | 0;
        }
        if (testStyle.indexOf("bold") != -1) {
            meStyle |= 1;
        }
        if (testStyle.indexOf("italic") != -1) {
            meStyle |= 2;
        }
        if (testStyle.indexOf("underlined") != -1) {
            meStyle |= 4;
        }
        int meSize = 0;
        if (size.equalsIgnoreCase("small")) {
            meSize = 0 | 8;
        } else if (size.equalsIgnoreCase("medium")) {
            meSize = 0 | 0;
        } else if (size.equalsIgnoreCase("large")) {
            meSize = 0 | 16;
        }
        return javax.microedition.lcdui.Font.getFont(meFace, meStyle, meSize);
    }

    private Rectangle getRectangle(XMLElement source) {
        Rectangle rect = new Rectangle();
        Enumeration e_rectangle = source.enumerateChildren();
        while (e_rectangle.hasMoreElements()) {
            XMLElement tmp_rectangle = (XMLElement) e_rectangle.nextElement();
            if (tmp_rectangle.getName().equals("x")) {
                rect.x = Integer.parseInt(tmp_rectangle.getContent());
            } else if (tmp_rectangle.getName().equals("y")) {
                rect.y = Integer.parseInt(tmp_rectangle.getContent());
            } else if (tmp_rectangle.getName().equals("width")) {
                rect.width = Integer.parseInt(tmp_rectangle.getContent());
            } else if (tmp_rectangle.getName().equals("height")) {
                rect.height = Integer.parseInt(tmp_rectangle.getContent());
            }
        }
        return rect;
    }

    private Polygon getPolygon(XMLElement source) {
        Polygon poly = new Polygon();
        Enumeration e_poly = source.enumerateChildren();
        while (e_poly.hasMoreElements()) {
            XMLElement tmp_point = (XMLElement) e_poly.nextElement();
            if (tmp_point.getName().equals("point")) {
                poly.addPoint(Integer.parseInt(tmp_point.getStringAttribute("x")), Integer.parseInt(tmp_point.getStringAttribute("y")));
            }
        }
        return poly;
    }

    private boolean parseBoolean(String value) {
        if (value.toLowerCase().equals(new String("true").toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override // org.microemu.device.Device
    public boolean hasPointerEvents() {
        return this.hasPointerEvents;
    }

    @Override // org.microemu.device.Device
    public boolean hasPointerMotionEvents() {
        return this.hasPointerMotionEvents;
    }

    @Override // org.microemu.device.Device
    public boolean hasRepeatEvents() {
        return this.hasRepeatEvents;
    }

    @Override // org.microemu.device.Device
    public boolean vibrate(int duration) {
        return false;
    }

    @Override // org.microemu.device.Device
    public Map getSystemProperties() {
        return this.systemProperties;
    }

    private static void saveDevice(XMLElement doc) throws IOException {
        File configFile = new File(".", "device-tmp.xml");
        FileWriter fw = null;
        try {
            try {
                fw = new FileWriter(configFile);
                doc.write(fw);
                fw.close();
                IOUtils.closeQuietly(fw);
            } catch (IOException ex) {
                System.out.println(ex);
                IOUtils.closeQuietly(fw);
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(fw);
            throw th;
        }
    }

    private static XMLElement loadDeviceDescriptor(ClassLoader classLoader, String descriptorLocation) throws IOException {
        InputStream descriptor = classLoader.getResourceAsStream(descriptorLocation);
        if (descriptor == null) {
            throw new IOException("Cannot find descriptor at: " + descriptorLocation);
        }
        try {
            XMLElement doc = loadXmlDocument(descriptor);
            IOUtils.closeQuietly(descriptor);
            String parent = doc.getStringAttribute("extends");
            if (parent != null) {
                return inheritXML(loadDeviceDescriptor(classLoader, expandResourcePath(besourceBase(descriptorLocation), parent)), doc, "/");
            }
            return doc;
        } catch (Throwable th) {
            IOUtils.closeQuietly(descriptor);
            throw th;
        }
    }

    private static void inheritanceConstInit() {
        if (specialInheritanceAttributeSet == null) {
            specialInheritanceAttributeSet = new Hashtable();
            specialInheritanceAttributeSet.put("//FONTS/FONT", new String[]{"face", "style", "size"});
        }
    }

    static XMLElement inheritXML(XMLElement parent, XMLElement child, String parentName) {
        boolean inheritWithName;
        XMLElement p;
        inheritanceConstInit();
        if (parent == null) {
            return child;
        }
        parent.setContent(child.getContent());
        Enumeration ena = child.enumerateAttributeNames();
        while (ena.hasMoreElements()) {
            String key = (String) ena.nextElement();
            parent.setAttribute(key, child.getAttribute(key));
        }
        Enumeration enc = child.enumerateChildren();
        while (enc.hasMoreElements()) {
            XMLElement c = (XMLElement) enc.nextElement();
            String fullName = (String.valueOf(parentName) + "/" + c.getName()).toUpperCase(Locale.ENGLISH);
            if (c.getStringAttribute("name") != null) {
                inheritWithName = true;
            } else {
                inheritWithName = child.getChildCount(c.getName()) > 1 || parent.getChildCount(c.getName()) > 1;
            }
            if (inheritWithName) {
                String[] equalAttributes = (String[]) specialInheritanceAttributeSet.get(fullName);
                if (equalAttributes != null) {
                    p = parent.getChild(c.getName(), c.getStringAttributes(equalAttributes));
                } else {
                    p = parent.getChild(c.getName(), c.getStringAttribute("name"));
                }
            } else {
                p = parent.getChild(c.getName());
            }
            boolean inheritOverride = c.getBooleanAttribute("override", false);
            if (inheritOverride) {
                c.removeAttribute("override");
                if (p != null) {
                    parent.removeChild(p);
                    p = null;
                }
            }
            if (p == null) {
                parent.addChild(c);
            } else {
                inheritXML(p, c, fullName);
            }
        }
        return parent;
    }

    private static XMLElement loadXmlDocument(InputStream descriptor) throws IOException {
        XMLElement doc = new XMLElement();
        try {
            doc.parse(descriptor, 1);
            return doc;
        } catch (XMLParseException ex) {
            throw new IOException(ex.toString());
        }
    }

    private static String besourceBase(String descriptorLocation) {
        return descriptorLocation.substring(0, descriptorLocation.lastIndexOf("/"));
    }

    private static String expandResourcePath(String base, String src) throws IOException {
        String expandedSource;
        if (src.startsWith("/")) {
            expandedSource = src;
        } else {
            expandedSource = String.valueOf(base) + "/" + src;
        }
        if (expandedSource.startsWith("/")) {
            expandedSource = expandedSource.substring(1);
        }
        return expandedSource;
    }

    private URL getResourceUrl(ClassLoader classLoader, String base, String src) throws IOException {
        String expandedSource = expandResourcePath(base, src);
        URL result = classLoader.getResource(expandedSource);
        if (result == null) {
            throw new IOException("Cannot find resource: " + expandedSource);
        }
        return result;
    }

    private Image loadImage(ClassLoader classLoader, String base, String src) throws IOException {
        URL url = getResourceUrl(classLoader, base, src);
        return ((DeviceDisplayImpl) getDeviceDisplay()).createSystemImage(url);
    }
}
