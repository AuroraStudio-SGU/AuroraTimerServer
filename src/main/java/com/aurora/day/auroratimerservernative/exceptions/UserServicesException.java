package com.aurora.day.auroratimerservernative.exceptions;

import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;

//其实没什么用讲真(
public class UserServicesException extends RuntimeException {

    private static final long serialVersionUID = -1L;
    private ResponseState state;

    public UserServicesException() {
    }

    public UserServicesException(ResponseState state) {
        super(state.getMsg());
        this.state = state;
    }

    public ResponseState getState() {
        return state;
    }
}
