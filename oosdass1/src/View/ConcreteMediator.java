package View;

public class ConcreteMediator implements Mediator {
    BtnNew btnNew;
    BtnUndo btnUndo;
    BtnGiveup btnGiveup;

    @Override
    public void registerNewButton(BtnNew btn) {
        btnNew = btn;
    }

    @Override
    public void registerNewUndo(BtnUndo btn) {
        btnUndo = btn;
    }

    @Override
    public void registerNewGiveup(BtnGiveup btn) {
        btnGiveup = btn;
    }

    @Override
    public void setNew(){
        btnNew.setLayoutX(100);
        btnNew.setLayoutY(80);
    }

    @Override
    public void setUndo() {
        btnUndo.setLayoutX(100);
        btnUndo.setLayoutY(200);
    }

    @Override
    public void setGiveup() {
        btnGiveup.setLayoutX(100);
        btnGiveup.setLayoutY(300);
    }
}
