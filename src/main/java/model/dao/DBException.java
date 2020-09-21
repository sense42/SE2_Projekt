package model.dao;

public class DBException extends Exception {
    private String text;

    public DBException(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
