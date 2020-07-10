package cn.zcgl.dao.enumClass;

/**
 * Created by fonlin on 2017/9/8.
 */
public enum TypeCode {

    Computer_notebook(0, "笔记本"),

    Computer_PC(1, "PC");

    int code;

    String message;

    TypeCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
