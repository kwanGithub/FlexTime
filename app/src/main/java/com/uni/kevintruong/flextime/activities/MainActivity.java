package com.uni.kevintruong.flextime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.uni.kevintruong.flextime.R;

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
                showPopUp(v);
                //Intent intent = new Intent("com.uni.kevintruong.flextime.MapsActivity");
                //startActivity(intent);

                return false;
            }
        });
    }

    public void showPopUp(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.options_popup_menu, popupMenu.getMenu());
        popupMenu.show();
    }
}
