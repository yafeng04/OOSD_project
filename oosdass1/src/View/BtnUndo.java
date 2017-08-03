package View;

import javafx.scene.control.Button;

import java.awt.event.ActionListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class BtnUndo extends Button implements Command {
    Mediator med;

    public BtnUndo(ActionListener al, Mediator m) {
        super("Undo");
        addActionListener(al);
        med = m;
        med.registerNewUndo(this);
    }
    @Override
    public void execute() {
        med.setUndo();
    }
}
