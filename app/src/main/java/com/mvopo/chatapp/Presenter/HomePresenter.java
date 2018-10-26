package com.mvopo.chatapp.Presenter;

import com.mvopo.chatapp.Interface.HomeContract;
import com.mvopo.chatapp.View.LoginFragment;
import com.mvopo.chatapp.View.SignUpFragment;

public class HomePresenter implements HomeContract.homeAction {
    private HomeContract.homeView homeView;

    public HomePresenter(HomeContract.homeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void onLoginClick() {
        LoginFragment loginFragment = new LoginFragment();
        homeView.showFragment(loginFragment);
    }

    @Override
    public void onSignUpClick() {
        SignUpFragment signUpFragment = new SignUpFragment();
        homeView.showFragment(signUpFragment);
    }
}
