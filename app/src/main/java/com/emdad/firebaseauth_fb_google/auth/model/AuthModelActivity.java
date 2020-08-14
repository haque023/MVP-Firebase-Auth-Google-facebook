package com.emdad.firebaseauth_fb_google.auth.model;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emdad.firebaseauth_fb_google.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthModelActivity {
    private static final String TAG = "";
    private CallbackManager mcallbackManager;
    private FirebaseAuth mAuth;
    private Activity activity;
    private AuthModelInterface authModelInterface;
    @Nullable
    private FirebaseUser user;
    GoogleSignInClient mGoogleSignInClient;

    public AuthModelActivity(Activity activity, AuthModelInterface authModelInterface) {

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        this.authModelInterface = authModelInterface;
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        mcallbackManager = CallbackManager.Factory.create();

        if (mAuth.getCurrentUser() != null) {
            user = mAuth.getCurrentUser();
        }

// Configure Google Sign In
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(this.activity.getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this.activity, gso);


    }

    public void getUser() {
        authModelInterface.setUser(user);
    }

    //googel token handale
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            authModelInterface.onSuccess(user);
                        } else {
                            Log.w("", "signInWithCredential:failure", task.getException());
                            authModelInterface.onFailed("signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }


    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, 1234);
    }

    //face book token
    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            authModelInterface.onSuccess(user);
                        } else {
                            authModelInterface.onFailed("signInWithCredential:failure", task.getException());
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                    }
                });
    }


}
