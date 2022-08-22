package com.as.photoapp.session;

import androidx.annotation.NonNull;

public class LogoutState implements State {
    @Override
    public void doAction(Context context) {
        context.setState(this);
    }

    @NonNull
    @Override
    public String toString() {
        return "You are logged out";
    }
}
