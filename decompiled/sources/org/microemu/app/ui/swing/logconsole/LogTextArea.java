package org.microemu.app.ui.swing.logconsole;

import java.awt.Rectangle;
import javax.swing.JTextArea;
import javax.swing.JViewport;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/logconsole/LogTextArea.class */
public class LogTextArea extends JTextArea {
    private static final long serialVersionUID = 1;
    private LogTextCaret caret;

    public LogTextArea(int rows, int columns, int maxLines) {
        super(rows, columns);
        this.caret = new LogTextCaret();
        setCaret(this.caret);
        setEditable(false);
    }

    public void setText(String t) {
        super.setText(t);
        this.caret.setVisibilityAdjustment(true);
    }

    public void append(String str) {
        super.append(str);
        JViewport viewport = getParent();
        boolean scrollToBottom = Math.abs(viewport.getViewPosition().getY() - ((double) (getHeight() - viewport.getHeight()))) < 100.0d;
        this.caret.setVisibilityAdjustment(scrollToBottom);
        if (scrollToBottom) {
            setCaretPosition(getText().length());
        }
    }

    /* loaded from: avacs.jar:org/microemu/app/ui/swing/logconsole/LogTextArea$SafeScroller.class */
    class SafeScroller implements Runnable {
        Rectangle r;

        SafeScroller(Rectangle r) {
            this.r = r;
        }

        @Override // java.lang.Runnable
        public void run() {
            LogTextArea.this.scrollRectToVisible(this.r);
        }
    }
}
