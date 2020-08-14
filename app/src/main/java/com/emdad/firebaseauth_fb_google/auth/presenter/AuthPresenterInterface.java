package com.emdad.firebaseauth_fb_google.auth.presenter;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public interface AuthPresenterInterface {
    void onSuccess(FirebaseUser user);

    void onFailed(String message, Throwable exception);

    void getUser(@Nullable FirebaseUser user);

}
