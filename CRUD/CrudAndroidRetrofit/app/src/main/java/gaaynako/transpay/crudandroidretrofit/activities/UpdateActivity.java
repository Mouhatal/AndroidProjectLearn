package gaaynako.transpay.crudandroidretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import gaaynako.transpay.crudandroidretrofit.R;
import gaaynako.transpay.crudandroidretrofit.gson.PersonneGson;
import gaaynako.transpay.crudandroidretrofit.models.Personne;
import gaaynako.transpay.crudandroidretrofit.rest.ApiInterface;
import gaaynako.transpay.crudandroidretrofit.rest.ApiUser;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private Button btnUpdate,btnDelete;
    private EditText edtName, edtPhone, edtAdresse, edtDate, edtEmail;

    private String name, phone, adresse, date, email;
    private int idPersonne,id;
    private Realm realm;
    SimpleDateFormat dateFormat;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        realm = Realm.getDefaultInstance();

        edtAdresse = (EditText) findViewById(R.id.edtAdresse);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtDate = (EditText) findViewById(R.id.edtDate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        name = getIntent().getStringExtra("nomPrenomPersonne");
        phone = getIntent().getStringExtra("telPersonne");
        adresse = getIntent().getStringExtra("adressePersonne");
        date = getIntent().getStringExtra("dateNaissancePersonne");
        email = getIntent().getStringExtra("emailPersonne");
        id = getIntent().getIntExtra("id", 0);
        idPersonne = getIntent().getIntExtra("idPersonne",1);

        edtName.setText(name);
        edtPhone.setText(phone);
        edtAdresse.setText(adresse);
        edtDate.setText(date);
        edtEmail.setText(email);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                edtDate.setText(sdf.format(myCalendar.getTime()));
                //Toast.makeText(HistoriqueActivity.this, "Get historique", Toast.LENGTH_LONG).show();

            }

        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String adresse = edtAdresse.getText().toString();
                String email = edtEmail.getText().toString();
                String date = edtDate.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(phone)) {
                    edtPhone.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(adresse)) {
                    edtAdresse.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Please enter Course Tracks");
                } else if (TextUtils.isEmpty(date)) {
                    edtDate.setError("Please enter Course Tracks");
                }
                else {
                    // on below line we are getting data from our modal where
                    // the id of the course equals to which we passed previously.
                    final Personne personneR = realm.where(Personne.class).equalTo("id", id).findFirst();
                    PersonneGson personneGson = new PersonneGson();
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    personneGson.setAdressePersonne(adresse);
                    personneGson.setEmailPersonne(email);
                    personneGson.setTelPersonne(phone);
                    personneGson.setName(name);
                    personneGson.setDateNaissancePersonne(date);
                    personneGson.setIdPersonne(idPersonne);

                    gson = new GsonBuilder().create();
                    JsonObject jsonObject = gson.toJsonTree(personneGson).getAsJsonObject();
                    ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);
                    apiService.update(jsonObject).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            JsonObject personne = response.body();
                            Log.e("Response Json", "" + personne );
                            JsonElement elementPersonne = personne.get("update");

                            String service = elementPersonne.getAsString();
                            if (service.equals("Ok")){
                                Toast.makeText(UpdateActivity.this, "Course Updated.", Toast.LENGTH_SHORT).show();
                                updateCourse(personneR, name, phone,  email,  adresse, date);
                                // on below line we are opening our activity for read course activity to view updated course.
                                Intent i = new Intent(UpdateActivity.this, ReadPersonneActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Personne non enregistre", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                }

                // on below line we are displaying a toast message when course is updated.

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersonneGson personneGson = new PersonneGson();
                personneGson.setIdPersonne(idPersonne);

                gson = new GsonBuilder().create();

                ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);
                JsonObject jsonObject = gson.toJsonTree(personneGson).getAsJsonObject();
                apiService.delete(jsonObject).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject personne = response.body();
                        Log.e("Response Json", "" + personne );
                        JsonElement elementPersonne = personne.get("suppression");

                        String service = elementPersonne.getAsString();
                        if (service.equals("Ok")){
                            Toast.makeText(UpdateActivity.this, "Course Updated.", Toast.LENGTH_SHORT).show();
                            deleteCourse(id);
                            // on below line we are opening our activity for read course activity to view updated course.
                            Intent i = new Intent(UpdateActivity.this, ReadPersonneActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Personne non enregistre", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void updateCourse(Personne personne, String name, String phone, String email, String adresse, String date) {

        // on below line we are calling
        // a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // on below line we are setting data to our modal class
                // which we get from our edit text fields.
                personne.setAdressePersonne(adresse);
                personne.setEmailPersonne(email);
                personne.setTelPersonne(phone);
                personne.setNomPrenomPersonne(name);
                personne.setDateNaissancePersonne(date);

                // inside on execute method we are calling a method to copy
                // and update to real m database from our modal class.
                realm.copyToRealmOrUpdate(personne);
            }
        });
    }

    // deleteCourse() function
    private void deleteCourse(int id) {
        // on below line we are finding data from our modal class by comparing it with the course id.
        Personne modal = realm.where(Personne.class).equalTo("id", id).findFirst();
        // on below line we are executing a realm transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // on below line we are calling a method for deleting this course
                modal.deleteFromRealm();
            }
        });
    }
}