package com.devoir.footsen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TeamsActivity extends Fragment {

    private String[] nameTeam, regionsTeam;
    private int[] logoTeam ={R.drawable.teunguetch,R.drawable.jaaraf,R.drawable.douane,R.drawable.dsc,R.drawable.casa,
                             R.drawable.pkine,R.drawable.stadembour,R.drawable.moburpc,R.drawable.goree,R.drawable.diambars,
                             R.drawable.ndiambour,R.drawable.genefoot,R.drawable.ngb,R.drawable.excellence};
    private ListView listTeam;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_teams
                , container, false);
        listTeam = view.findViewById(R.id.listTeam);
        nameTeam=getResources().getStringArray(R.array.tab_teams);
        regionsTeam=getResources().getStringArray(R.array.tab_description);
        //logoTeam=getResources().getIntArray(R.array.image_team);

        CustumAdapter adapter= new CustumAdapter();
        listTeam.setAdapter(adapter);

        return view;
    }

    class CustumAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return logoTeam.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custum_team_layout,null);
            ImageView imageView=convertView.findViewById(R.id.logo);
            TextView team=convertView.findViewById(R.id.team);
            TextView desc=convertView.findViewById(R.id.region);

            imageView.setImageResource(logoTeam[position]);
            team.setText(nameTeam[position]);
            desc.setText(regionsTeam[position]);
            return convertView;
        }
    }
}
