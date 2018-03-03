package sdm.pract1.whowantstobeamillionaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sdm.pract1.whowantstobeamillionaire.HighScore;

import sdm.pract1.whowantstobeamillionaire.R;

/**
 * Created by guimonto on 26/02/2018.
 */

public class ScoreAdapter extends ArrayAdapter {

    private Context context;
    private List<HighScore> scores;
    private int layout;

    public ScoreAdapter(Context context, List<HighScore> score, int layout){

        super(context, layout, score);
        this.context = context;
        this.scores = score;
        this.layout = layout;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.score_list_row, null);
        }

        HighScore item = scores.get(position);
        TextView tvP = (TextView) view.findViewById(R.id.tv_player_name);
        TextView tvS = (TextView) view.findViewById(R.id.tv_score_puntuation);
        tvP.setText(item.getName());
        tvS.setText(item.getScoring());
        return view;
    }
}
