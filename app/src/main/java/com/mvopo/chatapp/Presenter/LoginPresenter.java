package com.mvopo.chatapp.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mvopo.chatapp.Interface.LoginContract;
import com.mvopo.chatapp.Model.Constants;
import com.mvopo.chatapp.View.ChatFragment;

public class LoginPresenter implements LoginContract.loginAction {
    private LoginContract.loginView loginView;
    private String username, password;

    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    public LoginPresenter(LoginContract.loginView loginView) {
        this.loginView = loginView;

        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
    }

    @Override
    public void onLoginClick() {
        loginView.hideUserError();
        loginView.hidePassError();

        boolean userValid = isUserValid();
        boolean passValid = isPassValid();

        if (userValid && passValid) {
            validateCredential();
            return;
        }

        if (!userValid) loginView.showUserError();
        if (!passValid) loginView.showPassError();

        loginView.enableButton();
    }

    @Override
    public void onSignUpClick() {
        loginView.showSignUpFragment();
    }

    @Override
    public boolean isUserValid() {
        username = loginView.getUsername();

        if (username.isEmpty()) return false;

        if (username.length() < Constants.MIN_STRING_LEN ||
                username.length() > Constants.MAX_STRING_LEN) return false;

        return true;
    }

    @Override
    public boolean isPassValid() {
        password = loginView.getPassword();

        if (password.isEmpty()) return false;

        if (password.length() < Constants.MIN_STRING_LEN ||
                password.length() > Constants.MAX_STRING_LEN) return false;

        return true;
    }

    @Override
    public void validateCredential() {
        Query userQuery = usersRef.orderByChild("username").equalTo(username);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        String password = (String) snapshot.child("password").getValue();
                        String passInput = LoginPresenter.this.password;

                        if (passInput.equals(password)) {
                            loginView.showChatFragment(getChatFragment());
                            return;
                        }
                    }
                }

                loginView.showUserError();
                loginView.showPassError();
                loginView.enableButton();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public Fragment getChatFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);

        return chatFragment;
    }
}
