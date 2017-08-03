package View;

public interface  Mediator {
    void registerNewButton(BtnNew btn);
    void registerNewUndo(BtnUndo btn);
    void registerNewGiveup(BtnGiveup btn);

    void setNew();
    void setUndo();
    void setGiveup();
}
