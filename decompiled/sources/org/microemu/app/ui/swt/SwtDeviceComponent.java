package org.microemu.app.ui.swt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.TextField;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Polygon;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.Shape;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.impl.ui.CommandManager;
import org.microemu.device.swt.SwtButton;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtImmutableImage;
import org.microemu.device.swt.SwtInputMethod;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent.class */
public class SwtDeviceComponent extends Canvas {
    private static SwtDeviceComponent instance;
    private static HashMap colors = new HashMap();
    private SwtDisplayComponent dc;
    private Image fBuffer;
    private SwtButton prevOverButton;
    private SwtButton overButton;
    private SwtButton pressedButton;
    private SoftButton initialPressedSoftButton;
    private boolean mousePressed;
    KeyListener keyListener;
    MouseAdapter mouseListener;
    MouseMoveListener mouseMoveListener;

    public SwtDeviceComponent(Composite parent) {
        super(parent, TextField.SENSITIVE);
        this.fBuffer = null;
        this.keyListener = new KeyListener() { // from class: org.microemu.app.ui.swt.SwtDeviceComponent.1
            public void keyPressed(KeyEvent ev) {
                if (MIDletBridge.getCurrentMIDlet() == null) {
                    return;
                }
                Device device = DeviceFactory.getDevice();
                Iterator it = device.getButtons().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SwtButton button = (SwtButton) it.next();
                    if (ev.keyCode == button.getKeyboardKey()) {
                        ev.keyCode = button.getKeyCode();
                        break;
                    }
                }
                ((SwtInputMethod) device.getInputMethod()).keyPressed(ev);
                SwtDeviceComponent.this.pressedButton = ((SwtInputMethod) device.getInputMethod()).getButton(ev);
                if (SwtDeviceComponent.this.pressedButton != null) {
                    Shape shape = SwtDeviceComponent.this.pressedButton.getShape();
                    if (shape != null) {
                        Rectangle r = shape.getBounds();
                        SwtDeviceComponent.this.redraw(r.x, r.y, r.width, r.height, true);
                        return;
                    }
                    return;
                }
                SwtDeviceComponent.this.redraw();
            }

            public void keyReleased(KeyEvent ev) {
                if (MIDletBridge.getCurrentMIDlet() == null) {
                    return;
                }
                Device device = DeviceFactory.getDevice();
                Iterator it = device.getButtons().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SwtButton button = (SwtButton) it.next();
                    if (ev.keyCode == button.getKeyboardKey()) {
                        ev.keyCode = button.getKeyCode();
                        break;
                    }
                }
                ((SwtInputMethod) device.getInputMethod()).keyReleased(ev);
                SwtDeviceComponent.this.prevOverButton = SwtDeviceComponent.this.pressedButton;
                SwtDeviceComponent.this.pressedButton = null;
                if (SwtDeviceComponent.this.prevOverButton != null) {
                    Shape shape = SwtDeviceComponent.this.prevOverButton.getShape();
                    if (shape != null) {
                        Rectangle r = shape.getBounds();
                        SwtDeviceComponent.this.redraw(r.x, r.y, r.width, r.height, true);
                        return;
                    }
                    return;
                }
                SwtDeviceComponent.this.redraw();
            }
        };
        this.mouseListener = new MouseAdapter() { // from class: org.microemu.app.ui.swt.SwtDeviceComponent.2
            public void mouseDown(MouseEvent e) {
                DisplayAccess da;
                Rectangle pb;
                if (MIDletBridge.getCurrentMIDlet() == null) {
                    return;
                }
                Device device = DeviceFactory.getDevice();
                Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
                SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
                boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
                if (rect.x > e.x || rect.x + rect.width <= e.x || rect.y > e.y || rect.y + rect.height <= e.y) {
                    SwtDeviceComponent.this.pressedButton = SwtDeviceComponent.this.getButton(e.x, e.y);
                    if (SwtDeviceComponent.this.pressedButton != null) {
                        if ((SwtDeviceComponent.this.pressedButton instanceof SoftButton) && !fullScreenMode) {
                            Command cmd = ((SoftButton) SwtDeviceComponent.this.pressedButton).getCommand();
                            if (cmd != null) {
                                MIDletAccess ma = MIDletBridge.getMIDletAccess();
                                if (ma == null || (da = ma.getDisplayAccess()) == null) {
                                    return;
                                }
                                if (cmd.equals(CommandManager.CMD_MENU)) {
                                    CommandManager.getInstance().commandAction(cmd);
                                } else {
                                    da.commandAction(cmd, da.getCurrent());
                                }
                            }
                        } else {
                            Event event = new Event();
                            event.widget = e.widget;
                            KeyEvent ev = new KeyEvent(event);
                            ev.keyCode = SwtDeviceComponent.this.pressedButton.getKeyCode();
                            inputMethod.mousePressed(ev);
                        }
                        Rectangle r = SwtDeviceComponent.this.pressedButton.getShape().getBounds();
                        SwtDeviceComponent.this.redraw(r.x, r.y, r.width, r.height, true);
                    }
                } else if (device.hasPointerEvents()) {
                    if (!fullScreenMode) {
                        Iterator it = device.getSoftButtons().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            SoftButton button = (SoftButton) it.next();
                            if (button.isVisible() && (pb = button.getPaintable()) != null && pb.contains(e.x - rect.x, e.y - rect.y)) {
                                SwtDeviceComponent.this.initialPressedSoftButton = button;
                                button.setPressed(true);
                                SwtDeviceComponent.this.dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                                break;
                            }
                        }
                    }
                    if (fullScreenMode) {
                        inputMethod.pointerPressed(e.x - rect.x, e.y - rect.y);
                    } else {
                        Rectangle pb2 = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayPaintable();
                        inputMethod.pointerPressed((e.x - rect.x) - pb2.x, (e.y - rect.y) - pb2.y);
                    }
                }
                SwtDeviceComponent.this.mousePressed = true;
            }

            public void mouseUp(MouseEvent e) {
                Command cmd;
                DisplayAccess da;
                if (MIDletBridge.getCurrentMIDlet() == null) {
                    return;
                }
                Device device = DeviceFactory.getDevice();
                Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
                SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
                boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
                if (rect.x <= e.x && rect.x + rect.width > e.x && rect.y <= e.y && rect.y + rect.height > e.y) {
                    if (device.hasPointerEvents()) {
                        if (!fullScreenMode) {
                            if (SwtDeviceComponent.this.initialPressedSoftButton != null && SwtDeviceComponent.this.initialPressedSoftButton.isPressed()) {
                                SwtDeviceComponent.this.initialPressedSoftButton.setPressed(false);
                                Rectangle pb = SwtDeviceComponent.this.initialPressedSoftButton.getPaintable();
                                if (pb != null) {
                                    SwtDeviceComponent.this.dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                                    if (pb.contains(e.x - rect.x, e.y - rect.y) && (cmd = SwtDeviceComponent.this.initialPressedSoftButton.getCommand()) != null) {
                                        MIDletAccess ma = MIDletBridge.getMIDletAccess();
                                        if (ma == null || (da = ma.getDisplayAccess()) == null) {
                                            return;
                                        }
                                        if (cmd.equals(CommandManager.CMD_MENU)) {
                                            CommandManager.getInstance().commandAction(cmd);
                                        } else {
                                            da.commandAction(cmd, da.getCurrent());
                                        }
                                    }
                                }
                            }
                            SwtDeviceComponent.this.initialPressedSoftButton = null;
                        }
                        if (fullScreenMode) {
                            inputMethod.pointerReleased(e.x - rect.x, e.y - rect.y);
                        } else {
                            Rectangle pb2 = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayPaintable();
                            inputMethod.pointerReleased((e.x - rect.x) - pb2.x, (e.y - rect.y) - pb2.y);
                        }
                    }
                } else {
                    SwtButton prevOverButton = SwtDeviceComponent.this.getButton(e.x, e.y);
                    if (prevOverButton != null) {
                        inputMethod.mouseReleased(prevOverButton.getKeyCode());
                    }
                    SwtDeviceComponent.this.pressedButton = null;
                    if (prevOverButton != null) {
                        Rectangle r = prevOverButton.getShape().getBounds();
                        SwtDeviceComponent.this.redraw(r.x, r.y, r.width, r.height, true);
                    } else {
                        SwtDeviceComponent.this.redraw();
                    }
                }
                SwtDeviceComponent.this.mousePressed = false;
            }
        };
        this.mouseMoveListener = new MouseMoveListener() { // from class: org.microemu.app.ui.swt.SwtDeviceComponent.3
            public void mouseMove(MouseEvent e) {
                Rectangle pb;
                SwtDeviceComponent.this.prevOverButton = SwtDeviceComponent.this.overButton;
                SwtDeviceComponent.this.overButton = SwtDeviceComponent.this.getButton(e.x, e.y);
                if (SwtDeviceComponent.this.overButton != SwtDeviceComponent.this.prevOverButton) {
                    if (SwtDeviceComponent.this.prevOverButton != null) {
                        Rectangle r = SwtDeviceComponent.this.prevOverButton.getShape().getBounds();
                        SwtDeviceComponent.this.redraw(r.x, r.y, r.width, r.height, true);
                    }
                    if (SwtDeviceComponent.this.overButton != null) {
                        Rectangle r2 = SwtDeviceComponent.this.overButton.getShape().getBounds();
                        SwtDeviceComponent.this.redraw(r2.x, r2.y, r2.width, r2.height, true);
                    }
                }
                if (SwtDeviceComponent.this.mousePressed) {
                    Device device = DeviceFactory.getDevice();
                    Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
                    SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
                    boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
                    if (rect.x <= e.x && rect.x + rect.width > e.x && rect.y <= e.y && rect.y + rect.height > e.y && device.hasPointerMotionEvents()) {
                        if (!fullScreenMode && SwtDeviceComponent.this.initialPressedSoftButton != null && (pb = SwtDeviceComponent.this.initialPressedSoftButton.getPaintable()) != null) {
                            if (pb.contains(e.x - rect.x, e.y - rect.y)) {
                                if (!SwtDeviceComponent.this.initialPressedSoftButton.isPressed()) {
                                    SwtDeviceComponent.this.initialPressedSoftButton.setPressed(true);
                                    SwtDeviceComponent.this.dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                                }
                            } else if (SwtDeviceComponent.this.initialPressedSoftButton.isPressed()) {
                                SwtDeviceComponent.this.initialPressedSoftButton.setPressed(false);
                                SwtDeviceComponent.this.dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                            }
                        }
                        if (fullScreenMode) {
                            inputMethod.pointerDragged(e.x - rect.x, e.y - rect.y);
                        } else {
                            Rectangle pb2 = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayPaintable();
                            inputMethod.pointerDragged((e.x - rect.x) - pb2.x, (e.y - rect.y) - pb2.y);
                        }
                    }
                }
            }
        };
        instance = this;
        this.mousePressed = false;
        this.dc = new SwtDisplayComponent(this);
        this.initialPressedSoftButton = null;
        addKeyListener(this.keyListener);
        addMouseListener(this.mouseListener);
        addMouseMoveListener(this.mouseMoveListener);
        addPaintListener(new PaintListener() { // from class: org.microemu.app.ui.swt.SwtDeviceComponent.4
            public void paintControl(PaintEvent e) {
                SwtDeviceComponent.this.paintControl(e);
            }
        });
    }

    public DisplayComponent getDisplayComponent() {
        return this.dc;
    }

    public Point computeSize(int wHint, int hHint, boolean changed) {
        javax.microedition.lcdui.Image tmp = DeviceFactory.getDevice().getNormalImage();
        return new Point(tmp.getWidth(), tmp.getHeight());
    }

    public void paintControl(PaintEvent pe) {
        Rectangle rect;
        Rectangle rect2;
        Shape shape;
        Shape shape2;
        Point size = getSize();
        if (size.x <= 0 || size.y <= 0) {
            return;
        }
        if (this.fBuffer != null) {
            org.eclipse.swt.graphics.Rectangle r = this.fBuffer.getBounds();
            if (r.width != size.x || r.height != size.y) {
                this.fBuffer.dispose();
                this.fBuffer = null;
            }
        }
        if (this.fBuffer == null) {
            this.fBuffer = new Image(getDisplay(), size.x, size.y);
        }
        SwtGraphics gc = new SwtGraphics(new GC(this.fBuffer));
        try {
            Device device = DeviceFactory.getDevice();
            gc.drawImage(((SwtImmutableImage) device.getNormalImage()).getImage(), 0, 0);
            Rectangle displayRectangle = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
            gc.translate(displayRectangle.x, displayRectangle.y);
            this.dc.paint(gc);
            gc.translate(-displayRectangle.x, -displayRectangle.y);
            if (this.prevOverButton != null) {
                Shape shape3 = this.prevOverButton.getShape();
                if (shape3 != null) {
                    drawImageInShape(gc, ((SwtImmutableImage) device.getNormalImage()).getImage(), shape3);
                }
                this.prevOverButton = null;
            }
            if (this.overButton != null && (shape2 = this.overButton.getShape()) != null) {
                drawImageInShape(gc, ((SwtImmutableImage) device.getOverImage()).getImage(), shape2);
            }
            if (this.pressedButton != null && (shape = this.pressedButton.getShape()) != null) {
                drawImageInShape(gc, ((SwtImmutableImage) device.getPressedImage()).getImage(), shape);
            }
            if (this.prevOverButton != null) {
                Rectangle rect3 = this.prevOverButton.getShape().getBounds();
                if (rect3 != null) {
                    gc.drawImage(((SwtImmutableImage) DeviceFactory.getDevice().getNormalImage()).getImage(), rect3.x, rect3.y, rect3.width, rect3.height, rect3.x, rect3.y, rect3.width, rect3.height);
                }
                this.prevOverButton = null;
            }
            if (this.overButton != null && (rect2 = this.overButton.getShape().getBounds()) != null) {
                gc.drawImage(((SwtImmutableImage) DeviceFactory.getDevice().getOverImage()).getImage(), rect2.x, rect2.y, rect2.width, rect2.height, rect2.x, rect2.y, rect2.width, rect2.height);
            }
            if (this.pressedButton != null && (rect = this.pressedButton.getShape().getBounds()) != null) {
                gc.drawImage(((SwtImmutableImage) DeviceFactory.getDevice().getPressedImage()).getImage(), rect.x, rect.y, rect.width, rect.height, rect.x, rect.y, rect.width, rect.height);
            }
            gc.dispose();
            pe.gc.drawImage(this.fBuffer, 0, 0);
        } catch (Throwable th) {
            gc.dispose();
            throw th;
        }
    }

    private void drawImageInShape(SwtGraphics g, Image image, Shape shape) {
        org.eclipse.swt.graphics.Rectangle clipSave = g.getClipping();
        boolean z = shape instanceof Polygon;
        Rectangle r = shape.getBounds();
        g.drawImage(image, r.x, r.y, r.width, r.height, r.x, r.y, r.width, r.height);
        g.setClipping(clipSave);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SwtButton getButton(int x, int y) {
        Enumeration e = DeviceFactory.getDevice().getButtons().elements();
        while (e.hasMoreElements()) {
            SwtButton button = (SwtButton) e.nextElement();
            if (button.getShape() != null) {
                try {
                    Shape tmp = (Shape) button.getShape().clone();
                    if (tmp.contains(x, y)) {
                        return button;
                    }
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent$CreateImageRunnable.class */
    private class CreateImageRunnable implements Runnable {
        private ImageData data;
        private Image image;

        CreateImageRunnable(ImageData data) {
            this.data = data;
        }

        Image getImage() {
            return this.image;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.image = new Image(SwtDeviceComponent.instance.getParent().getDisplay(), this.data);
        }
    }

    public static Image createImage(int width, int height) {
        return new Image(instance.getDisplay(), width, height);
    }

    public static Image createImage(Image image) {
        return new Image(instance.getDisplay(), image, 0);
    }

    public static Image createImage(ImageData data) {
        SwtDeviceComponent swtDeviceComponent = instance;
        swtDeviceComponent.getClass();
        CreateImageRunnable createImageRunnable = swtDeviceComponent.new CreateImageRunnable(data);
        instance.getDisplay().syncExec(createImageRunnable);
        return createImageRunnable.getImage();
    }

    public static Image createImage(InputStream is) {
        ImageData data = new ImageData(is);
        SwtDeviceComponent swtDeviceComponent = instance;
        swtDeviceComponent.getClass();
        CreateImageRunnable createImageRunnable = swtDeviceComponent.new CreateImageRunnable(data);
        instance.getDisplay().syncExec(createImageRunnable);
        return createImageRunnable.getImage();
    }

    public static Image createImage(InputStream is, ImageFilter filter) throws IOException {
        try {
            ImageData data = new ImageData(is);
            RGB[] rgbs = data.getRGBs();
            if (rgbs != null) {
                for (int i = 0; i < rgbs.length; i++) {
                    rgbs[i] = filter.filterRGB(0, 0, rgbs[i]);
                }
            } else {
                for (int y = 0; y < data.height; y++) {
                    for (int x = 0; x < data.width; x++) {
                        int pixel = data.getPixel(x, y);
                        RGB rgb = filter.filterRGB(x, y, new RGB((pixel >> 16) & 255, (pixel >> 8) & 255, pixel & 255));
                        data.setPixel(x, y, (rgb.red << 16) + (rgb.green << 8) + rgb.blue);
                    }
                }
            }
            SwtDeviceComponent swtDeviceComponent = instance;
            swtDeviceComponent.getClass();
            CreateImageRunnable createImageRunnable = swtDeviceComponent.new CreateImageRunnable(data);
            instance.getDisplay().syncExec(createImageRunnable);
            return createImageRunnable.getImage();
        } catch (SWTException ex) {
            throw new IOException(ex.toString());
        }
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent$CreateColorRunnable.class */
    private class CreateColorRunnable implements Runnable {
        private RGB rgb;
        private Color color;

        CreateColorRunnable(RGB rgb) {
            this.rgb = rgb;
        }

        Color getColor() {
            return this.color;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.color = new Color(SwtDeviceComponent.instance.getParent().getDisplay(), this.rgb);
        }
    }

    public static Color getColor(RGB rgb) {
        Color result = (Color) colors.get(rgb);
        if (result == null) {
            SwtDeviceComponent swtDeviceComponent = instance;
            swtDeviceComponent.getClass();
            CreateColorRunnable createColorRunnable = swtDeviceComponent.new CreateColorRunnable(rgb);
            instance.getDisplay().syncExec(createColorRunnable);
            result = createColorRunnable.getColor();
            colors.put(rgb, result);
        }
        return result;
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent$GetFontMetricsRunnable.class */
    private class GetFontMetricsRunnable implements Runnable {
        private Font font;
        private FontMetrics fontMetrics;

        GetFontMetricsRunnable(Font font) {
            this.font = font;
        }

        FontMetrics getFontMetrics() {
            return this.fontMetrics;
        }

        @Override // java.lang.Runnable
        public void run() {
            SwtGraphics gc = new SwtGraphics(SwtDeviceComponent.instance.getParent().getDisplay());
            gc.setFont(this.font);
            this.fontMetrics = gc.getFontMetrics();
            gc.dispose();
        }
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent$GetFontRunnable.class */
    private class GetFontRunnable implements Runnable {
        private String name;
        private int size;
        private int style;
        private boolean antialiasing;
        private Font font;

        GetFontRunnable(String name, int size, int style, boolean antialiasing) {
            this.name = name;
            this.size = size;
            this.style = style;
        }

        Font getFont() {
            return this.font;
        }

        @Override // java.lang.Runnable
        public void run() {
            SwtGraphics gc = new SwtGraphics(SwtDeviceComponent.instance.getParent().getDisplay());
            gc.setAntialias(this.antialiasing);
            gc.setFont(new Font(SwtDeviceComponent.instance.getParent().getDisplay(), this.name, this.size, this.style));
            this.font = gc.getFont();
            gc.dispose();
        }
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDeviceComponent$StringWidthRunnable.class */
    private class StringWidthRunnable implements Runnable {
        private Font font;
        private String str;
        private int stringWidth;

        StringWidthRunnable(Font font, String str) {
            this.font = font;
            this.str = str;
        }

        int stringWidth() {
            return this.stringWidth;
        }

        @Override // java.lang.Runnable
        public void run() {
            SwtGraphics gc = new SwtGraphics(SwtDeviceComponent.instance.getParent().getDisplay());
            gc.setFont(this.font);
            this.stringWidth = gc.stringWidth(this.str);
            gc.dispose();
        }
    }

    public static Font getFont(String name, int size, int style, boolean antialiasing) {
        SwtDeviceComponent swtDeviceComponent = instance;
        swtDeviceComponent.getClass();
        GetFontRunnable getFontRunnable = swtDeviceComponent.new GetFontRunnable(name, size, style, antialiasing);
        instance.getDisplay().syncExec(getFontRunnable);
        return getFontRunnable.getFont();
    }

    public static FontMetrics getFontMetrics(Font font) {
        SwtDeviceComponent swtDeviceComponent = instance;
        swtDeviceComponent.getClass();
        GetFontMetricsRunnable getFontMetricsRunnable = swtDeviceComponent.new GetFontMetricsRunnable(font);
        instance.getDisplay().syncExec(getFontMetricsRunnable);
        return getFontMetricsRunnable.getFontMetrics();
    }

    public static int stringWidth(Font font, String str) {
        SwtDeviceComponent swtDeviceComponent = instance;
        swtDeviceComponent.getClass();
        StringWidthRunnable stringWidthRunnable = swtDeviceComponent.new StringWidthRunnable(font, str);
        instance.getDisplay().syncExec(stringWidthRunnable);
        return stringWidthRunnable.stringWidth();
    }
}
