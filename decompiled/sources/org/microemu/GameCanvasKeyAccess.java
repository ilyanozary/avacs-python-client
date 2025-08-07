package org.microemu;

import javax.microedition.lcdui.game.GameCanvas;

/* loaded from: avacs.jar:org/microemu/GameCanvasKeyAccess.class */
public interface GameCanvasKeyAccess {
    boolean suppressedKeyEvents(GameCanvas gameCanvas);

    void recordKeyPressed(GameCanvas gameCanvas, int i);

    void recordKeyReleased(GameCanvas gameCanvas, int i);

    void setActualKeyState(GameCanvas gameCanvas, int i);

    void initBuffer();
}
