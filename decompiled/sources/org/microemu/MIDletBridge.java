package org.microemu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;
import org.microemu.app.launcher.Launcher;

/* loaded from: avacs.jar:org/microemu/MIDletBridge.class */
public class MIDletBridge {
    static ThreadLocal threadMIDletContexts = new ThreadLocal();
    static Map midletContexts = new WeakHashMap();
    static MicroEmulator emulator = null;
    static MIDlet currentMIDlet = null;
    static Map gameCanvasAccesses = new WeakHashMap();
    private static ArrayList players = new ArrayList();

    public static void setMicroEmulator(MicroEmulator emulator2) {
        emulator = emulator2;
    }

    public static MicroEmulator getMicroEmulator() {
        return emulator;
    }

    public static void setThreadMIDletContext(MIDletContext midletContext) {
        threadMIDletContexts.set(midletContext);
    }

    public static void registerMIDletAccess(MIDletAccess accessor) {
        MIDletContext c = (MIDletContext) threadMIDletContexts.get();
        if (c == null) {
            c = new MIDletContext();
            setThreadMIDletContext(c);
        }
        c.setMIDletAccess(accessor);
        registerMIDletContext(c);
    }

    public static void registerMIDletContext(MIDletContext midletContext) {
        midletContexts.put(midletContext.getMIDlet(), midletContext);
    }

    public static MIDletContext getMIDletContext(MIDlet midlet) {
        return (MIDletContext) midletContexts.get(midlet);
    }

    public static MIDletContext getMIDletContext() {
        MIDletContext c = (MIDletContext) threadMIDletContexts.get();
        if (c != null) {
            return c;
        }
        return getMIDletContext(currentMIDlet);
    }

    public static void setCurrentMIDlet(MIDlet midlet) {
        currentMIDlet = midlet;
    }

    public static MIDlet getCurrentMIDlet() {
        MIDletContext c = getMIDletContext();
        if (c == null) {
            return null;
        }
        return c.getMIDlet();
    }

    public static MIDletAccess getMIDletAccess() {
        MIDletContext c = getMIDletContext();
        if (c == null) {
            return null;
        }
        return c.getMIDletAccess();
    }

    public static MIDletAccess getMIDletAccess(MIDlet midlet) {
        return getMIDletContext(midlet).getMIDletAccess();
    }

    public static RecordStoreManager getRecordStoreManager() {
        return emulator.getRecordStoreManager();
    }

    public static String getAppProperty(String key) {
        return emulator.getAppProperty(key);
    }

    public static InputStream getResourceAsStream(Class origClass, String name) {
        return emulator.getResourceAsStream(name);
    }

    public static void notifyDestroyed() {
        MIDletContext midletContext = getMIDletContext();
        emulator.notifyDestroyed(midletContext);
        destroyMIDletContext(midletContext);
    }

    public static void destroyMIDletContext(MIDletContext midletContext) {
        if (midletContext == null) {
            return;
        }
        emulator.destroyMIDletContext(midletContext);
        closeMediaPlayers();
        if (midletContexts.containsValue(midletContext)) {
            for (Map.Entry entry : midletContexts.entrySet()) {
                if (entry.getValue() == midletContext) {
                    midletContexts.remove(entry.getKey());
                    return;
                }
            }
        }
    }

    public static int checkPermission(String permission) {
        return emulator.checkPermission(permission);
    }

    public static boolean platformRequest(String URL) {
        return emulator.platformRequest(URL);
    }

    public static void clear() {
        currentMIDlet = null;
        for (Map.Entry entry : midletContexts.entrySet()) {
            MIDlet test = ((MIDletContext) entry.getValue()).getMIDlet();
            if (test instanceof Launcher) {
                midletContexts.clear();
                midletContexts.put(entry.getKey(), entry.getValue());
                return;
            }
        }
        midletContexts.clear();
    }

    public static GameCanvasKeyAccess getGameCanvasKeyAccess(GameCanvas gameCanvas) {
        return (GameCanvasKeyAccess) gameCanvasAccesses.get(gameCanvas);
    }

    public static void registerGameCanvasKeyAccess(GameCanvas gameCanvas, GameCanvasKeyAccess access) {
        gameCanvasAccesses.put(gameCanvas, access);
    }

    public static void addMediaPlayer(Player player) {
        players.add(player);
    }

    public static void removeMediaPlayer(Player player) {
        players.remove(player);
    }

    private static void closeMediaPlayers() {
        for (int i = players.size() - 1; i >= 0; i--) {
            ((Player) players.get(i)).close();
        }
        players.clear();
    }
}
