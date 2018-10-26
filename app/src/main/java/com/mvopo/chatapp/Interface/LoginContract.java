package com.mvopo.chatapp.Interface;

import android.support.v4.app.Fragment;

public class LoginContract {

    public interface loginView extends TextErrorListener{
        String getUsername();
        String getPassword();

        void showSignUpFragment();
        void showChatFragment(Fragment fragment);

        void enableButton();
    }

    public interface loginAction extends ClickListener{
        //Add unique methods here.
        boolean isUserValid();
        boolean isPassValid();

        void validateCredential();

        Fragment getChatFragment();
    }
}
