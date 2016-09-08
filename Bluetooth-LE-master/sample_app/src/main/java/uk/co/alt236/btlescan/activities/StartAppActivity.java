package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.co.alt236.btlescan.R;

public class StartAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        Button btnstrat = (Button) findViewById(R.id.btn_start);
        btnstrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tt =  new Intent(StartAppActivity.this,ShowlistbeaconActivity.class);
                startActivity(tt);
            }
        });

    }
}
