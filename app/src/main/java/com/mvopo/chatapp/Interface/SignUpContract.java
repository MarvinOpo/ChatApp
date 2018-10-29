package com.mvopo.chatapp.Interface;

import android.support.v4.app.Fragment;

public class SignUpContract {

    public interface signUpView extends TextErrorListener{
        String getUsername();
        String getPassword();

        void showLoginFragment();
        void showChatFragment(Fragment fragment);

        void enableButton();
    }

    public interface signUpAction extends ClickListener{
        //Add unique methods here.
        boolean isUserValid();
        boolean isPassValid();

        void checkIfUserExist();
        void writeUserToDB();

        Fragment getChatFragment();
    }
}
