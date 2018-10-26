package com.mvopo.chatapp.Interface;

import android.support.v4.app.Fragment;

public class HomeContract {

    public interface homeView  {
        void showFragment(Fragment fragment);
    }

    public interface homeAction extends ClickListener{
        //Add unique methods here.

    }
}
