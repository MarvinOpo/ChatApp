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

import com.mvopo.chatapp.Interface.SignUpContract;
import com.mvopo.chatapp.Presenter.SignUpPresenter;
import com.mvopo.chatapp.R;

public class SignUpFragment extends Fragment implements SignUpContract.signUpView {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private EditText edtxUser, edtxPass;
    private TextView tvUserError, tvPassError, tvLogin;
    private Button btnSignUp;

    private SignUpPresenter signUpPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        signUpPresenter = new SignUpPresenter(this);

        edtxUser = view.findViewById(R.id.sign_up_user_edtx);
        edtxPass = view.findViewById(R.id.sign_up_pass_edtx);

        tvUserError = view.findViewById(R.id.sign_up_user_error);
        tvPassError = view.findViewById(R.id.sign_up_pass_error);

        tvLogin = view.findViewById(R.id.sign_up_login_btn);
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpPresenter.onLoginClick();
            }
        });

        btnSignUp = view.findViewById(R.id.sign_up_btn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp.setEnabled(false);
                signUpPresenter.onSignUpClick();
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
    public void showLoginFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment()).commit();
    }

    @Override
    public void showChatFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "ChatFragment").commit();
    }

    @Override
    public void enableButton() {
        btnSignUp.setEnabled(true);
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
