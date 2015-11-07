package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.uni.kevintruong.flextime.R;
import com.uni.kevintruong.flextime.managers.OptionsPopUpEventListener;

public class MainActivity extends AppCompatActivity
{
    private Button optionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickOptionsBtnListener();
    }

    public void OnClickOptionsBtnListener()
    {
        optionsBtn = (Button) findViewById(R.id.mainOptionsBtn);

        optionsBtn.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showOptionsMenuPopUp(v);
                //Intent intent = new Intent("com.uni.kevintruong.flextime.MapsActivity");
                //startActivity(intent);

                return false;
            }
        });
    }

    public void showOptionsMenuPopUp(View view)
    {
        PopupMenu optionsPopupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = optionsPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.options_popup_menu, optionsPopupMenu.getMenu());
        OptionsPopUpEventListener optionsPopUpEventListener = new OptionsPopUpEventListener(getApplicationContext());
        optionsPopupMenu.setOnMenuItemClickListener(optionsPopUpEventListener);
        optionsPopupMenu.show();
    }
}
