package org.microemu.app.ui.swing;

import java.awt.datatransfer.DataFlavor;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/DropTransferHandler.class */
public class DropTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1;
    private static DataFlavor uriListFlavor = new DataFlavor("text/uri-list;class=java.lang.String", (String) null);
    private static boolean debugImport = false;

    public int getSourceActions(JComponent c) {
        return 1;
    }

    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        for (int i = 0; i < transferFlavors.length; i++) {
            Class representationclass = transferFlavors[i].getRepresentationClass();
            if (representationclass != null && URL.class.isAssignableFrom(representationclass)) {
                if (debugImport) {
                    Logger.debug("acepted ", transferFlavors[i]);
                    return true;
                }
                return true;
            }
            if (DataFlavor.javaFileListFlavor.equals(transferFlavors[i])) {
                if (debugImport) {
                    Logger.debug("acepted ", transferFlavors[i]);
                    return true;
                }
                return true;
            }
            if (DataFlavor.stringFlavor.equals(transferFlavors[i])) {
                if (debugImport) {
                    Logger.debug("acepted ", transferFlavors[i]);
                    return true;
                }
                return true;
            }
            if (uriListFlavor.equals(transferFlavors[i])) {
                if (debugImport) {
                    Logger.debug("acepted ", transferFlavors[i]);
                    return true;
                }
                return true;
            }
            if (debugImport) {
                Logger.debug(String.valueOf(i) + " unknown import ", transferFlavors[i]);
            }
        }
        if (debugImport) {
            Logger.debug("import rejected");
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x019d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean importData(javax.swing.JComponent r5, java.awt.datatransfer.Transferable r6) {
        /*
            Method dump skipped, instructions count: 559
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.microemu.app.ui.swing.DropTransferHandler.importData(javax.swing.JComponent, java.awt.datatransfer.Transferable):boolean");
    }

    private String getPathString(String path) {
        if (path == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(path.trim(), "\n\r");
        if (st.hasMoreTokens()) {
            return st.nextToken();
        }
        return path;
    }
}
