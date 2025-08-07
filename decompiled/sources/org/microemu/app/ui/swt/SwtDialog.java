package org.microemu.app.ui.swt;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDialog.class */
public abstract class SwtDialog {
    public static final int OK = 0;
    public static final int CANCEL = 1;
    private Shell parentShell;
    private Shell shell;
    protected Control dialogArea;
    protected Control buttonBar;
    protected Button btOk;
    protected Button btCancel;
    private Listener resizeListener;
    private Control contents;
    private int shellStyle;
    private boolean block;
    private boolean resizeHasOccurred = false;
    private int returnCode = 0;

    public SwtDialog(Shell parentShell) {
        this.shellStyle = 1264;
        this.block = false;
        this.parentShell = parentShell;
        this.block = true;
        this.shellStyle = 67680;
    }

    public void create() {
        this.shell = createShell();
        this.contents = createContents(this.shell);
        initializeBounds();
    }

    protected final Shell createShell() {
        Shell newShell = new Shell(this.parentShell, this.shellStyle);
        this.resizeListener = new Listener() { // from class: org.microemu.app.ui.swt.SwtDialog.1
            public void handleEvent(Event e) {
                SwtDialog.this.resizeHasOccurred = true;
            }
        };
        newShell.addListener(11, this.resizeListener);
        newShell.setData(this);
        newShell.addShellListener(getShellListener());
        configureShell(newShell);
        return newShell;
    }

    protected void configureShell(Shell newShell) {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        newShell.setLayout(layout);
    }

    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, 0);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(1808));
        composite.setFont(parent.getFont());
        this.dialogArea = createDialogArea(composite);
        this.buttonBar = createButtonBar(composite);
        return composite;
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, 0);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(1808));
        composite.setFont(parent.getFont());
        return composite;
    }

    protected Control createButtonBar(Composite parent) {
        Composite composite = new Composite(parent, 0);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(64));
        composite.setFont(parent.getFont());
        this.btOk = new Button(composite, 8);
        this.btOk.setText("OK");
        this.btOk.addSelectionListener(new SelectionAdapter() { // from class: org.microemu.app.ui.swt.SwtDialog.2
            public void widgetSelected(SelectionEvent event) {
                SwtDialog.this.buttonPressed(0);
            }
        });
        this.btCancel = new Button(composite, 8);
        this.btCancel.setText("Cancel");
        this.btCancel.addSelectionListener(new SelectionAdapter() { // from class: org.microemu.app.ui.swt.SwtDialog.3
            public void widgetSelected(SelectionEvent event) {
                SwtDialog.this.buttonPressed(1);
            }
        });
        return composite;
    }

    protected void initializeBounds() {
        if (this.resizeListener != null) {
            this.shell.removeListener(11, this.resizeListener);
        }
        if (this.resizeHasOccurred) {
            return;
        }
        Point size = getInitialSize();
        Point location = getInitialLocation(size);
        this.shell.setBounds(location.x, location.y, size.x, size.y);
    }

    protected Point getInitialSize() {
        return this.shell.computeSize(-1, -1, true);
    }

    protected Point getInitialLocation(Point initialSize) {
        Composite parentShell = this.shell.getParent();
        Rectangle containerBounds = parentShell != null ? parentShell.getBounds() : this.shell.getDisplay().getClientArea();
        int x = Math.max(0, containerBounds.x + ((containerBounds.width - initialSize.x) / 2));
        int y = Math.max(0, containerBounds.y + ((containerBounds.height - initialSize.y) / 3));
        return new Point(x, y);
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == 0) {
            okPressed();
        } else if (buttonId == 1) {
            cancelPressed();
        }
    }

    protected void okPressed() {
        setReturnCode(0);
        close();
    }

    protected void cancelPressed() {
        setReturnCode(1);
        close();
    }

    protected int getReturnCode() {
        return this.returnCode;
    }

    protected void setReturnCode(int code) {
        this.returnCode = code;
    }

    protected ShellListener getShellListener() {
        return new ShellAdapter() { // from class: org.microemu.app.ui.swt.SwtDialog.4
            public void shellClosed(ShellEvent event) {
                event.doit = false;
                SwtDialog.this.setReturnCode(1);
                SwtDialog.this.close();
            }
        };
    }

    public boolean close() {
        if (this.shell != null || !this.shell.isDisposed()) {
            this.shell.dispose();
            this.shell = null;
            this.contents = null;
        }
        this.buttonBar = null;
        this.dialogArea = null;
        return true;
    }

    public Shell getShell() {
        return this.shell;
    }

    public int open() {
        if (this.shell == null) {
            create();
        }
        this.shell.open();
        if (this.block) {
            runEventLoop(this.shell);
        }
        return this.returnCode;
    }

    private void runEventLoop(Shell shell) {
        Display display;
        if (shell == null) {
            display = Display.getCurrent();
        } else {
            display = shell.getDisplay();
        }
        while (shell != null && !shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Throwable ex) {
                System.err.println(ex);
            }
        }
        display.update();
    }
}
