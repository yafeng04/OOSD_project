package Model;

import java.util.ArrayList;

/**
 * Created by yangfeng on 28/5/17.
 */
public class StoreNExecute {

    private ArrayList<Command> history = new ArrayList<>();

    public ArrayList<Command> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Command> history) {
        this.history = history;
    }

    public void StoreNExecute (Command cmd) {
        this.history.add(cmd); // optional
        cmd.execute();
    }

}
