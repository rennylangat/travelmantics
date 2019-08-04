package com.renny.user.travelmantics;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil mFirebaseUtil;
    public static ArrayList<TravelDeals> deals;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN=123;

    private static Activity caller;
    private FirebaseUtil(){};

    public static void openFbReference(String ref, final Activity callerActivity){
        if (mFirebaseUtil == null){

            mFirebaseUtil=new FirebaseUtil();
            mFirebaseDatabase=FirebaseDatabase.getInstance();
            caller=callerActivity;
            mFirebaseAuth=FirebaseAuth.getInstance();
            mAuthStateListener=new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null){
                        FirebaseUtil.signIn();
                    }
                    Toast.makeText(callerActivity.getBaseContext(),"Welcome back!",Toast.LENGTH_LONG).show();
                     }
            };

            deals=new ArrayList<TravelDeals>();
        }
        mDatabaseReference=mFirebaseDatabase.getReference().child(ref);
    }
    public static void signIn(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }

    public static void attachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    public static void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
}
