package sdm.pract1.whowantstobeamillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.menu_about:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickButtons(View v){

        Intent intent = null;
        switch (v.getId()){

            case R.id.ibPlay:
                intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;

            case R.id.ibScore:
                intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
                break;

            case R.id.ibSettings:
               intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}