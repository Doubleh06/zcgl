package cn.zcgl.core;

/**
 * Created by fonlin on 2017/9/8.
 * 可以装填对象返回的Result
 */
public class JSONResult extends Result {

    private Object obj;

    public JSONResult() {
        super();
    }

    public JSONResult(int code, String msg) {
        super(code, msg);
    }

    public JSONResult(int code, String msg, Object obj) {
        super(code, msg);
        this.obj = obj;
    }

    public JSONResult(ErrorCode httpCode, Object obj) {
        super(httpCode);
        this.obj = obj;
    }


    public JSONResult(Object obj) {
        super(ErrorCode.OK);
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
