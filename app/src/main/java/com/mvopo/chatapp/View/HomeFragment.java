package com.mvopo.chatapp.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mvopo.chatapp.Interface.HomeContract;
import com.mvopo.chatapp.Presenter.HomePresenter;
import com.mvopo.chatapp.R;

public class HomeFragment extends Fragment implements HomeContract.homeView {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button btnSignUp, btnLogin;

    private HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        homePresenter = new HomePresenter(this);

        btnSignUp = view.findViewById(R.id.home_sign_up_btn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.onSignUpClick();
            }
        });

        btnLogin = view.findViewById(R.id.home_login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.onLoginClick();
            }
        });

        return view;
    }

    @Override
    public void showFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack("").commit();
    }
}
