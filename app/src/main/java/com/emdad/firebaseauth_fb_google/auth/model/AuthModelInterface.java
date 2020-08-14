package com.emdad.firebaseauth_fb_google.auth.model;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public interface AuthModelInterface {
    void onSuccess(FirebaseUser user);

    void onFailed(String message, Throwable exception);

    void setUser(@Nullable FirebaseUser user);
}
