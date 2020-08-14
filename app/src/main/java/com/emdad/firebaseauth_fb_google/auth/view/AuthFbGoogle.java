package com.emdad.firebaseauth_fb_google.auth.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.emdad.firebaseauth_fb_google.auth.presenter.AuthPresenterActivity;
import com.emdad.firebaseauth_fb_google.auth.presenter.AuthPresenterInterface;
import com.emdad.firebaseauth_fb_google.main.view.MainActivity;
import com.emdad.firebaseauth_fb_google.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class AuthFbGoogle extends AppCompatActivity implements AuthPresenterInterface {

    private String TAG = "";
    CallbackManager mCallbackManager;
    AuthPresenterActivity authPresenterActivity;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        authPresenterActivity = new AuthPresenterActivity(AuthFbGoogle.this, AuthFbGoogle.this);
        mCallbackManager = CallbackManager.Factory.create();
        authPresenterActivity.getUser();

        if (user != null) {
            move();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_fb_google);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                authPresenterActivity.signIn(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authPresenterActivity.signInWithgoogle();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void move() {
        Intent mainActivity = new Intent(AuthFbGoogle.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


        //google sign in handale
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authPresenterActivity.handelGoogleAuth(account);
            } catch (ApiException e) {
                Toast.makeText(AuthFbGoogle.this, e.getMessage() + "camera", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onSuccess(FirebaseUser user) {
        move();
    }

    @Override
    public void onFailed(String message, Throwable exception) {
        Toast.makeText(this, message + exception, Toast.LENGTH_SHORT);

    }

    @Override
    public void getUser(@Nullable FirebaseUser user) {
        if (user != null)
            this.user = user;
    }


}