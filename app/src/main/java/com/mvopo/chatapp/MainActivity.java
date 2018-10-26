package com.mvopo.chatapp;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mvopo.chatapp.View.ChatFragment;
import com.mvopo.chatapp.View.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        ChatFragment chatFragment = (ChatFragment) fragmentManager.findFragmentByTag("ChatFragment");

        if(chatFragment != null && chatFragment.isVisible()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

            return;
        }

        if(fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
            return;
        }

        super.onBackPressed();
    }
}
