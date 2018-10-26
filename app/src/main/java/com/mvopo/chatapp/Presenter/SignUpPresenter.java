package com.mvopo.chatapp.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mvopo.chatapp.Interface.SignUpContract;
import com.mvopo.chatapp.Model.Constants;
import com.mvopo.chatapp.View.ChatFragment;

public class SignUpPresenter implements SignUpContract.signUpAction {

    private SignUpContract.signUpView signUpView;
    private String username, password;

    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    public SignUpPresenter(SignUpContract.signUpView signUpView) {
        this.signUpView = signUpView;

        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
    }

    @Override
    public void onLoginClick() {
        signUpView.showLoginFragment();
    }

    @Override
    public void onSignUpClick() {
        signUpView.hideUserError();
        signUpView.hidePassError();

        boolean userInputValid = isUserValid();
        boolean passInputValid = isPassValid();

        if(userInputValid && passInputValid){
            checkIfUserExist();
            return;
        }

        if (!userInputValid)  signUpView.showUserError();
        if (!passInputValid)  signUpView.showPassError();

        signUpView.enableButton();
    }

    @Override
    public boolean isUserValid() {
        username = signUpView.getUsername().toLowerCase();

        if (username.isEmpty()) return false;

        if (username.length() < Constants.MIN_STRING_LEN ||
                username.length() > Constants.MAX_STRING_LEN) return false;

        return true;
    }

    @Override
    public boolean isPassValid() {
        password = signUpView.getPassword().toLowerCase();

        if (password.isEmpty()) return false;

        if (password.length() < Constants.MIN_STRING_LEN ||
                password.length() > Constants.MAX_STRING_LEN) return false;

        return true;
    }

    @Override
    public void checkIfUserExist() {
        Query userQuery = usersRef.orderByChild("username").equalTo(username);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    signUpView.showUserError();
                    signUpView.enableButton();
                    return;
                }

                addUserInDB();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void addUserInDB() {
        String key = usersRef.push().getKey();
        usersRef.child(key + "/username").setValue(username);
        usersRef.child(key + "/password").setValue(password);

        signUpView.showChatFragment(getChatFragment());
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
