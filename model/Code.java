package model;

public class Code {
    private String date;;
    private String code;

    public Code() {
    }

    public Code(String data, String date) {
        this.date = date;
        this.code = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


