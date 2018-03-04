package sdm.pract1.whowantstobeamillionaire;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sdm.pract1.whowantstobeamillionaire.databases.GameSqlHelper;
import sdm.pract1.whowantstobeamillionaire.pojo.Question;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {
//En este espacio solo se pueden crear variables pero NO inicializarlas, porque hace que se cierre la aplicacion

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private List<Question> questions;
    private TextView play_for;
    private TextView number_question;
    private TextView current_question;
    private int ind; /*Indice*/
    private int points; /*Puntos*/
    private int correct; /*Respuesta correcta*/
    private boolean addScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*Inicializamos aqui las variables*/
        b1 = (Button) findViewById(R.id.option1);
        b2 = (Button) findViewById(R.id.option2);
        b3 = (Button) findViewById(R.id.option3);
        b4 = (Button) findViewById(R.id.option4);
        play_for = (TextView) findViewById(R.id.PF_MN);
        number_question = (TextView) findViewById(R.id.QT_NB);
        current_question = (TextView) findViewById(R.id.question);
        points = R.string.price0; correct = 0;


        b1.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b2.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b3.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b4.setBackgroundColor(getResources().getColor(R.color.colorGrey));


        /*Obtenemos la lista de preguntas*/
        questions = generateQuestionList();
        //readXmlPullParser();
        /*Indice de preguntas*/
        //ind = 0;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ((TextView) findViewById(R.id.question)).setText(prefs.getString("question", ""));
        ((TextView) findViewById(R.id.QT_NB)).setText(prefs.getString("question_number", "1"));
        ((TextView) findViewById(R.id.PF_MN)).setText(prefs.getString("play_for", getResources().getString(R.string.price100)));
        points = prefs.getInt("score", 0);
        correct = prefs.getInt("right", 0);
        ind = prefs.getInt("indice", 0);
        ((Button) findViewById(R.id.option1)).setText(prefs.getString("button1", ""));
        ((Button) findViewById(R.id.option2)).setText(prefs.getString("button2", ""));
        ((Button) findViewById(R.id.option3)).setText(prefs.getString("button3", ""));
        ((Button) findViewById(R.id.option4)).setText(prefs.getString("button4", ""));


        assignation(ind);

    }

    private void game(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);
        String name = sharedPreferences.getString("prefs_name", getResources().getString(R.string.preference_name));
        String jokers = sharedPreferences.getString("prefs_jokers", getResources().getString(R.string.number_jokers));
        int joker = Integer.parseInt(jokers);
        switch (joker) {
            case(0):
                findViewById(R.id.menu_phone).setEnabled(false);
                findViewById(R.id.menu_people).setEnabled(false);
                findViewById(R.id.menu_fifty).setEnabled(false);
                break;

            case(1):
                findViewById(R.id.menu_phone).setEnabled(true);
                findViewById(R.id.menu_people).setEnabled(false);
                findViewById(R.id.menu_fifty).setEnabled(false);
                break;

            case(2):
                findViewById(R.id.menu_phone).setEnabled(true);
                findViewById(R.id.menu_people).setEnabled(false);
                findViewById(R.id.menu_fifty).setEnabled(true);
                break;

            case(3):
                findViewById(R.id.menu_phone).setEnabled(true);
                findViewById(R.id.menu_people).setEnabled(true);
                findViewById(R.id.menu_fifty).setEnabled(true);
                break;

        }

        /*Si el indice es menor o igual que 15 incrementamos una unidad*/
        if(ind < 15) ind++;
        else {
            /*Partida finalizada*/
            ind = 0;
        }
        /*Habilitamos los botones*/
        b1.setEnabled(true); b2.setEnabled(true); b3.setEnabled(true); b4.setEnabled(true);
        /*Dejamos el color original*/
        b1.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b2.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b3.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        b4.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        /*Ejecutamos la siguiente pregunta*/
        assignation(ind);
    }

    public void assignation(int index){
        int a = index;
        switch (a) {
            case 0:
                play_for.setText(R.string.price100);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 1:
                play_for.setText(R.string.price200);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 2:
                play_for.setText(R.string.price300);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 3:
                play_for.setText(R.string.price500);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 4:
                play_for.setText(R.string.price1000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 5:
                play_for.setText(R.string.price2000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 6:
                play_for.setText(R.string.price4000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 7:
                play_for.setText(R.string.price8000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 8:
                play_for.setText(R.string.price16000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 9:
                play_for.setText(R.string.price32000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 10:
                play_for.setText(R.string.price64000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 11:
                play_for.setText(R.string.price125000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 12:
                play_for.setText(R.string.price250000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 13:
                play_for.setText(R.string.price500000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());
                break;

            case 14:
                play_for.setText(R.string.price1000000);
                number_question.setText(questions.get(a).getNumber());
                current_question.setText(questions.get(a).getText());
                b1.setText(questions.get(a).getAnswer1());
                b2.setText(questions.get(a).getAnswer2());
                b3.setText(questions.get(a).getAnswer3());
                b4.setText(questions.get(a).getAnswer4());

                break;
        }
    }

    //Segun el numero de pregunta asigna una puntuación
    private void puntuacion(int aux){
        switch (aux){
            case 0:
                points = 0;
                break;

            case 1:
                points = 100;
                break;

            case 2:
                points = 200;
                break;

            case 3:
                points = 300;
                break;

            case 4:
                points = 500;
                break;

            case 5:
                points = 1000;
                break;

            case 6:
                points = 2000;
                break;

            case 7:
                points = 4000;
                break;

            case 8:
                points = 8000;
                break;

            case 9:
                points = 16000;
                break;

            case 10:
                points = 32000;
                break;

            case 11:
                points = 64000;
                break;

            case 12:
                points = 125000;
                break;

            case 13:
                points = 250000;
                break;

            case 14:
                points = 500000;
                break;

            case 15:
                points = 1000000;
                break;
        }

    }

    public void clickButtons(View v){
        correct = Integer.parseInt(questions.get(ind).getRight());
        int cont = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);
        String name = sharedPreferences.getString("prefs_name", getResources().getString(R.string.preference_name));
        switch (v.getId()){
            case R.id.option1:
                if (correct == 1){
                    cont++;
                    puntuacion(cont);
                    String score = String.valueOf(points);
                    b1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    b2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b3.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b4.setBackgroundColor(getResources().getColor(R.color.colorRed));


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    },3000);
                }
                else{
                    if(cont >= 10 ) {
                        puntuacion(10);
                        String score = String.valueOf(points);
                        GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);
                    }
                    else {
                        if (cont >= 5 & cont < 10) {puntuacion(5); String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        else {
                            if (cont < 5) {puntuacion(0); String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        }
                    }
                    b1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b2.setEnabled(false);b3.setEnabled(false);b4.setEnabled(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, R.string.looser, Toast.LENGTH_LONG).show();
                        }
                    },200);
                    ind = 0;
                    assignation(ind);
                    findViewById(R.id.menu_fifty).setEnabled(true);
                    findViewById(R.id.menu_people).setEnabled(true);
                    findViewById(R.id.menu_phone).setEnabled(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);


                }
                break;
            case R.id.option2:
                if (correct == 2) {
                    cont ++;
                    puntuacion(cont);
                    String score = String.valueOf(points);
                    b2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    b1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b3.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b4.setBackgroundColor(getResources().getColor(R.color.colorRed));


                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    },3000);
                }
                else{

                    if(cont >= 10 ) {
                        puntuacion(10);
                        String score = String.valueOf(points);
                        GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);
                    }
                    else {
                        if (cont >= 5 & cont < 10){ puntuacion(5); String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        else {
                            if (cont < 10){ puntuacion(0); String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        }
                    }
                    b2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b1.setEnabled(false);b3.setEnabled(false);b4.setEnabled(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, R.string.looser, Toast.LENGTH_LONG).show();
                        }
                    },200);
                    ind = 0;
                    assignation(ind);
                    findViewById(R.id.menu_fifty).setEnabled(true);
                    findViewById(R.id.menu_people).setEnabled(true);
                    findViewById(R.id.menu_phone).setEnabled(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.option3:
                if (correct == 3) {
                    cont ++;
                    puntuacion(cont);
                    String score = String.valueOf(points);
                    b3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    b1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b4.setBackgroundColor(getResources().getColor(R.color.colorRed));

                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    },3000);
                }
                else {
                    if(cont >= 10 ) {
                        points = 32000;
                        String score = String.valueOf(points);
                        GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);
                    }
                    else {
                        if (cont >= 5 & cont < 10){ points = 1000; String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        else {
                            if (cont < 5){ points = 0; String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        }
                    }
                    b3.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b1.setEnabled(false);b2.setEnabled(false);b4.setEnabled(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, R.string.looser, Toast.LENGTH_LONG).show();
                        }
                    },200);
                    ind = 0;
                    assignation(ind);
                    findViewById(R.id.menu_fifty).setEnabled(true);
                    findViewById(R.id.menu_people).setEnabled(true);
                    findViewById(R.id.menu_phone).setEnabled(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.option4:
                if (correct == 4) {
                    cont ++;
                    puntuacion(cont);
                    String score = String.valueOf(points);
                    b4.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    b1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b3.setBackgroundColor(getResources().getColor(R.color.colorRed));

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    },3000);
                }
                else {
                    if(cont >= 10 ) {
                        points = 32000;
                        String score = String.valueOf(points);
                        GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);
                    }
                    else {
                        if (cont >= 5 & cont < 10){
                            points = 1000;
                            String score = String.valueOf(points);
                            GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        else {
                            if (cont < 5){ points = 0; String score = String.valueOf(points); GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);}
                        }
                    }
                    b4.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    b1.setEnabled(false);b2.setEnabled(false);b3.setEnabled(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, R.string.looser, Toast.LENGTH_LONG).show();
                        }
                    },200);
                    ind = 0;
                    assignation(ind);
                    findViewById(R.id.menu_fifty).setEnabled(true);
                    findViewById(R.id.menu_people).setEnabled(true);
                    findViewById(R.id.menu_phone).setEnabled(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.game_menu, menu);

        menu.findItem(R.id.menu_phone).setVisible(true);
        menu.findItem(R.id.menu_fifty).setVisible(true);
        menu.findItem(R.id.menu_people).setVisible(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        menu.findItem(R.id.menu_phone).setEnabled(prefs.getBoolean("phone", true));
        menu.findItem(R.id.menu_fifty).setEnabled(prefs.getBoolean("fifty", true));
        menu.findItem(R.id.menu_people).setEnabled(prefs.getBoolean("people", true));


        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {

           case R.id.menu_fifty:
               if (Integer.parseInt(questions.get(ind).getFifty1()) == 1 && Integer.parseInt(questions.get(ind).getFifty2()) == 2) {
                   b1.setEnabled(false);
                   b2.setEnabled(false);
               } else {
                   if (Integer.parseInt(questions.get(ind).getFifty1()) == 1 && Integer.parseInt(questions.get(ind).getFifty2()) == 3) {
                       b1.setEnabled(false);
                       b3.setEnabled(false);
                   } else {
                       if (Integer.parseInt(questions.get(ind).getFifty1()) == 1 && Integer.parseInt(questions.get(ind).getFifty2()) == 4) {
                           b1.setEnabled(false);
                           b4.setEnabled(false);
                       } else {
                           if (Integer.parseInt(questions.get(ind).getFifty1()) == 2 && Integer.parseInt(questions.get(ind).getFifty2()) == 3) {
                               b2.setEnabled(false);
                               b3.setEnabled(false);
                           } else {
                               if (Integer.parseInt(questions.get(ind).getFifty1()) == 2 && Integer.parseInt(questions.get(ind).getFifty2()) == 4) {
                                   b2.setEnabled(false);
                                   b4.setEnabled(false);
                               } else {
                                   if (Integer.parseInt(questions.get(ind).getFifty1()) == 3 && Integer.parseInt(questions.get(ind).getFifty2()) == 4) {
                                       b3.setEnabled(false);
                                       b4.setEnabled(false);
                                   }
                               }
                           }
                       }
                   }
               }
               findViewById(R.id.menu_fifty).setEnabled(false);
               break;

           case R.id.menu_people:
               if (Integer.parseInt(questions.get(ind).getAudience()) == 1) {
                   b1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
               } else {
                   if (Integer.parseInt(questions.get(ind).getAudience()) == 2) {
                       b2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                   } else {
                       if (Integer.parseInt(questions.get(ind).getAudience()) == 3) {
                           b3.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                       } else {
                           if (Integer.parseInt(questions.get(ind).getAudience()) == 4) {
                               b4.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                           }
                       }
                   }
               }
               findViewById(R.id.menu_people).setEnabled(false);
               break;

           case R.id.menu_phone:
               if (Integer.parseInt(questions.get(ind).getPhone()) == 1) {
                   b1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
               } else {
                   if (Integer.parseInt(questions.get(ind).getPhone()) == 2) {
                       b2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                   } else {
                       if (Integer.parseInt(questions.get(ind).getPhone()) == 3) {
                           b3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                       } else {
                           if (Integer.parseInt(questions.get(ind).getPhone()) == 4) {
                               b4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                           }
                       }
                   }
               }
               findViewById(R.id.menu_phone).setEnabled(false);
               break;

           case R.id.menu_cancel:

               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setMessage(R.string.surrender);

               AlertDialog.Builder builder1 = builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       puntuacion(ind);
                       String score = String.valueOf(points);
                       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);
                       String name = sharedPreferences.getString("prefs_name", getResources().getString(R.string.preference_name));
                       GameSqlHelper.getInstance(GameActivity.this).addScore(name, score);
                       ind = 0;
                       assignation(ind);
                       findViewById(R.id.menu_fifty).setEnabled(true);
                       findViewById(R.id.menu_people).setEnabled(true);
                       findViewById(R.id.menu_phone).setEnabled(true);
                       Intent intent = new Intent(GameActivity.this, MainActivity.class);
                       startActivity(intent);
                   }

               });

               builder.setNegativeButton(android.R.string.no, null);

               builder.create().show();
               return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("play_for", ((TextView) findViewById(R.id.PF_MN)).getText().toString());
        editor.putString("question_number", ((TextView) findViewById(R.id.QT_NB)).getText().toString());
        editor.putInt("score", points);
        editor.putString("question", ((TextView) findViewById(R.id.question)).getText().toString());
        editor.putString("button1", ((TextView) findViewById(R.id.option1)).getText().toString());
        editor.putString("button2", ((TextView) findViewById(R.id.option2)).getText().toString());
        editor.putString("button3", ((TextView) findViewById(R.id.option3)).getText().toString());
        editor.putString("button4", ((TextView) findViewById(R.id.option4)).getText().toString());
        editor.putInt("right", Integer.parseInt(questions.get(ind).getRight()));
        editor.putInt("indice", ind);
        editor.putBoolean("fifty", (findViewById(R.id.menu_fifty)).isEnabled());
        editor.putBoolean("people", (findViewById(R.id.menu_people)).isEnabled());
        editor.putBoolean("phone",(findViewById(R.id.menu_phone)).isEnabled());


        editor.apply();
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){

        outState.putString("play_for", ((TextView) findViewById(R.id.PF_MN)).getText().toString());
        outState.putString("question_number", ((TextView) findViewById(R.id.QT_NB)).getText().toString());
        outState.putInt("score", points);
        outState.putString("question", ((TextView) findViewById(R.id.question)).getText().toString());
        outState.putString("button1", ((TextView) findViewById(R.id.option1)).getText().toString());
        outState.putString("button2", ((TextView) findViewById(R.id.option2)).getText().toString());
        outState.putString("button3", ((TextView) findViewById(R.id.option3)).getText().toString());
        outState.putString("button4", ((TextView) findViewById(R.id.option4)).getText().toString());
        outState.putInt("right", Integer.parseInt(questions.get(ind).getRight()));
        outState.putInt("indice", ind);
        outState.putBoolean("fifty", (findViewById(R.id.menu_fifty)).isEnabled());
        outState.putBoolean("people", (findViewById(R.id.menu_people)).isEnabled());
        outState.putBoolean("phone",(findViewById(R.id.menu_phone)).isEnabled());
        super.onSaveInstanceState(outState);
    }
/*
    public  void readXmlPullParser(){
        Question q;
        try {
            FileInputStream fis = openFileInput("questions_spanish");
            InputStreamReader reader = new InputStreamReader(fis);
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(reader);
            int eventType = parser.getEventType();

            while (XmlPullParser.END_DOCUMENT != eventType) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        q = readQuestion(parser);
                        questions.add(q);
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                parser.next();
                eventType = parser.getEventType();
            }
         reader.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /*Obtenemos cada objeto Question*/
    /*
    private Question readQuestion(XmlPullParser p) throws XmlPullParserException, IOException {
        String number = "";
        String text = "";
        String answer1 = "";
        String answer2 = "";
        String answer3 = "";
        String answer4 = "";
        String right = "";
        String audience = "";
        String phone = "";
        String fifty1 = "";
        String fifty2= "";

        while (p.next() != XmlPullParser.END_TAG){
            if (p.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = p.getName();

            switch (name) {
                case "question":
                    p.require(XmlPullParser.START_TAG, null, "question");
                    number = p.getAttributeValue(null, "number");
                    text = p.getAttributeValue(null, "text");
                    answer1 = p.getAttributeValue(null, "answer1");
                    answer2 = p.getAttributeValue(null, "answer2");
                    answer3 = p.getAttributeValue(null, "answer3");
                    answer4 = p.getAttributeValue(null, "answer4");
                    right = p.getAttributeValue(null, "right");
                    audience = p.getAttributeValue(null, "audience");
                    phone = p.getAttributeValue(null, "phone");
                    fifty1 = p.getAttributeValue(null, "fifty1");
                    fifty2 = p.getAttributeValue(null, "fifty2");
                    break;
            }
        }
        return new Question(number, text, answer1, answer2, answer3, answer4, right, audience, phone, fifty1, fifty2);
    }*/


    public List<Question> generateQuestionList() {
        List<Question> list = new ArrayList<Question>();
        Question q = null;

        q = new Question(
                "1",
                "Which is the Sunshine State of the US?",
                "North Carolina",
                "Florida",
                "Texas",
                "Arizona",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "2",
                "Which of these is not a U.S. state?",
                "New Hampshire",
                "Washington",
                "Wyoming",
                "Manitoba",
                "4",
                "4",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "3",
                "What is Book 3 in the Pokemon book series?",
                "Charizard",
                "Island of the Giant Pokemon",
                "Attack of the Prehistoric Pokemon",
                "I Choose You!",
                "3",
                "2",
                "3",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "4",
                "Who was forced to sign the Magna Carta?",
                "King John",
                "King Henry VIII",
                "King Richard the Lion-Hearted",
                "King George III",
                "1",
                "3",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "5",
                "Which ship was sunk in 1912 on its first voyage, although people said it would never sink?",
                "Monitor",
                "Royal Caribean",
                "Queen Elizabeth",
                "Titanic",
                "4",
                "4",
                "4",
                "1",
                "2"
        );
        list.add(q);

        q = new Question(
                "6",
                "Who was the third James Bond actor in the MGM films? (Do not include &apos;Casino Royale&apos?",
                "Roger Moore",
                "Pierce Brosnan",
                "Timothy Dalton",
                "Sean Connery",
                "1",
                "3",
                "3",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "7",
                "Which is the largest toothed whale?",
                "Humpback Whale",
                "Blue Whale",
                "Killer Whale",
                "Sperm Whale",
                "4",
                "2",
                "2",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "8",
                "In what year was George Washington born?",
                "1728",
                "1732",
                "1713",
                "1776",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "9",
                "Which of these rooms is in the second floor of the White House?",
                "Red Room",
                "China Room",
                "State Dining Room",
                "East Room",
                "2",
                "2",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "10",
                "Which Pope began his reign in 963?",
                "Innocent III",
                "Leo VIII",
                "Gregory VII",
                "Gregory I",
                "2",
                "1",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "11",
                "What is the second longest river in South America?",
                "Parana River",
                "Xingu River",
                "Amazon River",
                "Rio Orinoco",
                "1",
                "1",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "12",
                "What Ford replaced the Model T?",
                "Model U",
                "Model A",
                "Edsel",
                "Mustang",
                "2",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "13",
                "When was the first picture taken?",
                "1860",
                "1793",
                "1912",
                "1826",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "14",
                "Where were the first Winter Olympics held?",
                "St. Moritz, Switzerland",
                "Stockholm, Sweden",
                "Oslo, Norway",
                "Chamonix, France",
                "4",
                "1",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "15",
                "Which of these is not the name of a New York tunnel?",
                "Brooklyn-Battery",
                "Lincoln",
                "Queens Midtown",
                "Manhattan",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        return list;
    }
}
