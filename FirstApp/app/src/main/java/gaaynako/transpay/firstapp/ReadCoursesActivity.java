package gaaynako.transpay.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ReadCoursesActivity extends AppCompatActivity {

    List<DataModal> dataModals;
    private Button btnClose;
    // creating variables for realm,
    // recycler view, adapter and our list.
    private Realm realm;
    private RecyclerView coursesRV;
    private CourseRVAdapter courseRVAdapter;
    public static Activity read_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_courses);
        btnClose =findViewById(R.id.btnClose);
        // on below lines we are initializing our variables.
        coursesRV = findViewById(R.id.idRVCourses);
        realm = Realm.getDefaultInstance();
        dataModals = new ArrayList<>();

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
        dataModals = realm.where(DataModal.class).findAll();
        // on below line we are adding our list to our adapter class.
        courseRVAdapter = new CourseRVAdapter(dataModals, this);
        // on below line we are setting layout manager to our recycler view.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        // at last we are setting adapter to our recycler view.
        coursesRV.setAdapter(courseRVAdapter);
    }
}
