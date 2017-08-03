package View;

import javafx.scene.control.Button;

import java.awt.event.ActionListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class BtnNew extends Button implements Command {
    Mediator med;

    public BtnNew(ActionListener al, Mediator m) {
        super("new round");
        addActionListener(al);
        med = m;
        med.registerNewButton(this);
    }
    @Override
    public void execute() {
        med.setNew();
    }
}
