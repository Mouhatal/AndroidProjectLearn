package gaaynako.transpay.crudandroidretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gaaynako.transpay.crudandroidretrofit.R;
import gaaynako.transpay.crudandroidretrofit.config.SmsManager;
import gaaynako.transpay.crudandroidretrofit.gson.PersonneGson;
import gaaynako.transpay.crudandroidretrofit.models.Personne;
import gaaynako.transpay.crudandroidretrofit.rest.ApiInterface;
import gaaynako.transpay.crudandroidretrofit.rest.ApiUser;
import gaaynako.transpay.crudandroidretrofit.service.PersonneService;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPersonneActivity extends AppCompatActivity {

    private Button btnAdd,btnRead;
    private EditText edtName, edtPhone, edtAdresse, edtDate, edtEmail;

    private Realm realm;

    SimpleDateFormat dateFormat;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personne);
        realm = Realm.getDefaultInstance();
        edtAdresse = (EditText) findViewById(R.id.edtAdresse);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtDate = (EditText) findViewById(R.id.edtDate);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRead = (Button) findViewById(R.id.btnRead);

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
                new DatePickerDialog(AddPersonneActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().isEmpty() || edtAdresse.getText().toString().isEmpty() || edtPhone.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()){
                    String error = "champ requis";
                    Toast.makeText(AddPersonneActivity.this, error, Toast.LENGTH_SHORT).show();
                }else {
                    PersonneGson personneGson = new PersonneGson();
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    personneGson.setAdressePersonne(edtAdresse.getText().toString());
                    personneGson.setEmailPersonne(edtEmail.getText().toString());
                    personneGson.setTelPersonne(edtPhone.getText().toString());
                    personneGson.setName(edtName.getText().toString());
                    personneGson.setDateNaissancePersonne(edtDate.getText().toString());

                    gson = new GsonBuilder().create();
                    JsonObject jsonObject = gson.toJsonTree(personneGson).getAsJsonObject();

                    ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);

                    apiService.create(jsonObject).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject personne = response.body();
                            Log.e("Response Json", "" + personne );
                            JsonElement elementPersonne = personne.get("ajout");

                            String service = elementPersonne.getAsString();
                            if (service.equals("Ok")){
                                /*addDataToDatabase( edtName.getText().toString(),
                                                    edtPhone.getText().toString(),
                                                    edtEmail.getText().toString(),
                                                    edtAdresse.getText().toString(),
                                                    edtDate.getText().toString());*/
                                Toast.makeText(getApplicationContext(), "Personne enregistre", Toast.LENGTH_SHORT).show();
                                String sms = SmsManager.formatAjout(edtName.getText().toString(),edtPhone.getText().toString(),edtEmail.getText().toString());
                                SmsManager.sendSMSDirect(getApplicationContext(),sms,SmsManager.Tel);
                                edtAdresse.setText("");
                                edtDate.setText("");
                                edtEmail.setText("");
                                edtPhone.setText("");
                                edtName.setText("");
                                startService(new Intent(AddPersonneActivity.this, Personne.class));

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Personne non enregistre", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("response-failure", call.toString());
                            Toast.makeText(getApplicationContext(), "Personne non enregistre", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddPersonneActivity.this,ReadPersonneActivity.class);
                startService(new Intent(getApplicationContext(), PersonneService.class));
                startActivity(i);
            }
        });
    }
    /*private void addDataToDatabase( String name, String phone, String email, String adresse, String date) {

        // on below line we are creating
        // a variable for our modal class.
        Personne personne = new Personne();

        // on below line we are getting id for the course which we are storing.
        Number id = realm.where(Personne.class).max("id");
        Number idPersonne = realm.where(Personne.class).max("idPersonne");
        Log.e("ID", "" + idPersonne);
        // on below line we are
        // creating a variable for our id.
        int nextId;
        int nextIdPersonne = 0;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
            nextIdPersonne =idPersonne.intValue() + 1;
        }
        // on below line we are setting the
        // data entered by user in our modal class.
        personne.setId(nextId);
        personne.setIdPersonne(nextIdPersonne);
        personne.setAdressePersonne(adresse);
        personne.setEmailPersonne(email);
        personne.setTelPersonne(phone);
        personne.setNomPrenomPersonne(name);
        personne.setDateNaissancePersonne(date);


        // on below line we are calling a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
                realm.copyToRealm(personne);
            }
        });
    }*/
}