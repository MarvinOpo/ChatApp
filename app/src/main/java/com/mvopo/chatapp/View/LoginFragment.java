package com.mvopo.chatapp.View;

import android.graphics.Paint;
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
import android.widget.EditText;
import android.widget.TextView;

import com.mvopo.chatapp.Interface.LoginContract;
import com.mvopo.chatapp.Presenter.LoginPresenter;
import com.mvopo.chatapp.R;

public class LoginFragment extends Fragment implements LoginContract.loginView {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private EditText edtxUser, edtxPass;
    private TextView tvUserError, tvPassError, tvSignUp;
    private Button btnLogin;

    private LoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        loginPresenter = new LoginPresenter(this);

        edtxUser = view.findViewById(R.id.login_user_edtx);
        edtxPass = view.findViewById(R.id.login_pass_edtx);

        tvUserError = view.findViewById(R.id.login_user_error);
        tvPassError = view.findViewById(R.id.login_pass_error);

        tvSignUp = view.findViewById(R.id.login_sign_up_btn);
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.onSignUpClick();
            }
        });

        btnLogin = view.findViewById(R.id.login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);
                loginPresenter.onLoginClick();
            }
        });
        
        return view;
    }

    @Override
    public String getUsername() {
        String username = edtxUser.getText().toString().trim();
        return username;
    }

    @Override
    public String getPassword() {
        String password = edtxPass.getText().toString().trim();
        return password;
    }

    @Override
    public void showSignUpFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SignUpFragment()).commit();
    }

    @Override
    public void showChatFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "ChatFragment").commit();
    }

    @Override
    public void enableButton() {
        btnLogin.setEnabled(true);
    }

    @Override
    public void showUserError() {
        tvUserError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPassError() {
        tvPassError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUserError() {
        tvUserError.setVisibility(View.GONE);
    }

    @Override
    public void hidePassError() {
        tvPassError.setVisibility(View.GONE);
    }
}
