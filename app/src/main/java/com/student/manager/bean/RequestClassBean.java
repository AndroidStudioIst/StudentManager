package com.student.manager.bean;

/**
 * Created by angcyo on 2018-02-25.
 */

public class RequestClassBean extends BaseBmob {
    private String request = "";//正在申请的教室
    private String success = "";//申请成功的教室
    private String field = "";//申请失败

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void addSuccess(String value) {
        request = request.replaceAll(value, "");
        field = field.replaceAll(value, "");
        success = success + "," + value;
    }

    public void addRequest(String value) {
        field = field.replaceAll(value, "");
        success = success.replaceAll(value, "");
        request = request + "," + value;
    }
}
