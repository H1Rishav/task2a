package com.example.tomandjerry;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    GameView view;

   TextView pause,score_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pause=findViewById(R.id.pause_btn);
        score_txt=findViewById(R.id.score);
        view=findViewById(R.id.main_game);


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d=new Dialog(GameActivity.this);//we set the context of the current activity
                d.setContentView(R.layout.pause_dialog);
                //Thread.notify use karke make the thread wait for a while as the pause button is clicked
                d.show();
            }
        });
        score_txt.setText("Score:"+String.valueOf(view.fx));
    }

}