package org.microemu.app.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.microemu.app.util.MRUListListener;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/JMRUMenu.class */
public class JMRUMenu extends JMenu implements MRUListListener {
    private static final long serialVersionUID = 1;

    /* loaded from: avacs.jar:org/microemu/app/ui/swing/JMRUMenu$MRUActionEvent.class */
    public static class MRUActionEvent extends ActionEvent {
        private static final long serialVersionUID = 1;
        Object sourceMRU;

        public MRUActionEvent(Object sourceMRU, ActionEvent orig) {
            super(orig.getSource(), orig.getID(), orig.getActionCommand(), orig.getWhen(), orig.getModifiers());
            this.sourceMRU = sourceMRU;
        }

        public Object getSourceMRU() {
            return this.sourceMRU;
        }
    }

    public JMRUMenu(String s) {
        super(s);
    }

    @Override // org.microemu.app.util.MRUListListener
    public void listItemChanged(Object item) {
        String label = item.toString();
        int i = 0;
        while (true) {
            if (i >= getItemCount()) {
                break;
            }
            if (!getItem(i).getText().equals(label)) {
                i++;
            } else {
                remove(i);
                break;
            }
        }
        AbstractAction a = new AbstractAction(label, item) { // from class: org.microemu.app.ui.swing.JMRUMenu.1
            private static final long serialVersionUID = 1;
            Object sourceMRU;

            {
                this.sourceMRU = item;
            }

            public void actionPerformed(ActionEvent e) {
                JMRUMenu.this.fireActionPerformed(new MRUActionEvent(this.sourceMRU, e));
            }
        };
        JMenuItem menu = new JMenuItem(a);
        insert(menu, 0);
    }

    protected void fireActionPerformed(ActionEvent event) {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActionListener.class) {
                ((ActionListener) listeners[i + 1]).actionPerformed(event);
            }
        }
    }
}
