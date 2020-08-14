package com.emdad.firebaseauth_fb_google.auth.presenter;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.emdad.firebaseauth_fb_google.auth.model.AuthModelActivity;
import com.emdad.firebaseauth_fb_google.auth.model.AuthModelInterface;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterActivity implements AuthModelInterface {
    private AuthPresenterInterface authPresenterInterface;
    private AuthModelActivity authModelActivity;

    public AuthPresenterActivity(AuthPresenterInterface authPresenterInterface, Activity activity) {
        this.authModelActivity = new AuthModelActivity(activity, AuthPresenterActivity.this);
        this.authPresenterInterface = authPresenterInterface;

    }

    public void signIn(AccessToken token) {
        authModelActivity.handleFacebookAccessToken(token);
    }

    public void handelGoogleAuth(GoogleSignInAccount acct) {
        authModelActivity.firebaseAuthWithGoogle(acct);
    }


    public void signInWithgoogle() {
        authModelActivity.signIn();
    }


    public void getUser() {
        authModelActivity.getUser();
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        authPresenterInterface.onSuccess(user);
    }

    @Override
    public void onFailed(String message, Throwable exception) {
        authPresenterInterface.onFailed(message, exception);
    }

    @Override
    public void setUser(@Nullable FirebaseUser user) {
        authPresenterInterface.getUser(user);
    }
}
