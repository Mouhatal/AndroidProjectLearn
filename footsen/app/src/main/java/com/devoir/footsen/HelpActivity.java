package com.devoir.footsen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HelpActivity extends Fragment {

    private String[] Faq, response;
    private ListView listFaq;
    


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_help
                , container, false);
        listFaq = view.findViewById(R.id.listFaq);
        Faq=getResources().getStringArray(R.array.tab_faq);
        response=getResources().getStringArray(R.array.tab_reponse);
        //logoTeam=getResources().getIntArray(R.array.image_team);

        CustumAdapter adapter= new CustumAdapter();
        listFaq.setAdapter(adapter);

        return view;
    }

    class CustumAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return Faq.length;
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
            convertView = getLayoutInflater().inflate(R.layout.custum_help_layout,null);
            TextView team=convertView.findViewById(R.id.Faq);
            TextView desc=convertView.findViewById(R.id.reponse);
            //View line=convertView.findViewById(R.id.divider);

            team.setText(Faq[position]);
            desc.setText(response[position]);


            return convertView;
        }
    }
}
