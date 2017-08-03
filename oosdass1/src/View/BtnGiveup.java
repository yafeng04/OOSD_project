package View;

import javafx.scene.control.Button;

import java.awt.event.ActionListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class BtnGiveup extends Button implements Command {
    Mediator med;

    public BtnGiveup(ActionListener al, Mediator m) {
        super("give up");
        addActionListener(al);
        med = m;
        med.registerNewGiveup(this);
    }
    @Override
    public void execute() {
        med.setGiveup();
    }
}
