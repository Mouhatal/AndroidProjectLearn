package gaaynako.transpay.crudandroidretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import gaaynako.transpay.crudandroidretrofit.R;
import gaaynako.transpay.crudandroidretrofit.adapters.PersonneRvAdapter;
import gaaynako.transpay.crudandroidretrofit.models.Personne;
import gaaynako.transpay.crudandroidretrofit.rest.ApiInterface;
import gaaynako.transpay.crudandroidretrofit.rest.ApiUser;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPersonneActivity extends AppCompatActivity {

    List<Personne> personneList;
    private Button btnClose;
    // creating variables for realm,
    // recycler view, adapter and our list.
    private Realm realm;
    private RecyclerView coursesRV;
    private PersonneRvAdapter personneRvAdapter;
    public static Activity read_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_personne);
        btnClose = findViewById(R.id.btnClose);
        // on below lines we are initializing our variables.
        coursesRV = findViewById(R.id.idRVCourses);

        realm = Realm.getDefaultInstance();



        // calling a method to load
        // our recycler view with data.
        prepareRecyclerView();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void prepareRecyclerView() {
        // on below line we are getting data from realm database in our list.

        personneList = realm.where(Personne.class).findAll();

        // on below line we are adding our list to our adapter class.
        Log.e("personne", "" + personneList);
        personneRvAdapter = new PersonneRvAdapter(personneList, this);
        // on below line we are setting layout manager to our recycler view.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        // at last we are setting adapter to our recycler view.
        coursesRV.setAdapter(personneRvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean currentState = false;
        if (item.isCheckable()) {
            currentState = item.isChecked();
            item.setChecked(!currentState);
        }


        switch (item.getItemId()) {

            case R.id.quit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}