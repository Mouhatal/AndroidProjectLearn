package com.devoir.footsen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MactchActivity extends Fragment {

    private ListView listMatch;
    private String score,stade,statut,match;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mactch
                , container, false);
        listMatch=view.findViewById(R.id.listMatch);

        getJSON("http://3aa2c26e4bea.ngrok.io/senfoot/getMatch.php");

        String wait= getString(R.string.wait);
        Toast.makeText(getActivity(), wait, Toast.LENGTH_SHORT).show();

        return view;
    }

    private  void getJSON (final String urlWebService){

        class GetJSON extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s){
               super.onPostExecute(s);
               //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {

                try {
                    URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e){
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        final String[] matches = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            matches[i] = obj.getString("equipe");
        }
               /*String[] dates = new String[jsonArray.length()];
               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject obj = jsonArray.getJSONObject(i);
                   dates[i] = obj.getString("date");
               }*/
        final String[] stades = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            stades[i] = obj.getString("lieu");
        }
        final String[] scores = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            scores[i] = obj.getString("score");
        }
        final String[] statuts = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            statuts[i] = obj.getString("statut");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, matches);
        listMatch.setAdapter(arrayAdapter);
        listMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                score= scores[position];
                stade= stades[position];
                statut= statuts[position];
                match= matches[position];

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle(match);
                dialog.setMessage(stade +"\n"+ statut +"\n" + score);
                dialog.setNeutralButton("Ok",null);
                       /*if(statut!="prochainement"){
                           dialog.setMessage(stade +"\n"+ statut +"\n" + score);
                           dialog.setNeutralButton("Ok",null);
                       }else {
                           dialog.setMessage(stade +"\n"+ statut +"\n" + score);
                           dialog.setNegativeButton(getString(R.string.cancel),null);
                           dialog.setPositiveButton(getString(R.string.ticket), new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   Intent intent = new Intent(getActivity(),TicketActivity.class);
                                   intent.putExtra("MATCH",match);
                                   startActivity(intent);
                               }
                           });
                       }*/
                dialog.show();
            }
        });
    }
}
