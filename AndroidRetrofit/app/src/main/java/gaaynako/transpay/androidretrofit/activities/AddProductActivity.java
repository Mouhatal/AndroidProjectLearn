package gaaynako.transpay.androidretrofit.activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import gaaynako.transpay.androidretrofit.R;
import gaaynako.transpay.androidretrofit.gson.ProduitGson;
import gaaynako.transpay.androidretrofit.models.Produit;
import gaaynako.transpay.androidretrofit.rest.ApiInterface;
import gaaynako.transpay.androidretrofit.rest.ApiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtName, edtCost, edtCategory,edtDate;
    private Produit produit;
    private String imei = "";

    SimpleDateFormat dateFormat;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtName = (EditText) findViewById(R.id.edtName);
        edtCost = (EditText) findViewById(R.id.edtCost);
        edtCategory = (EditText) findViewById(R.id.edtCategory);
        edtDate = (EditText) findViewById(R.id.edtDate);

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
                new DatePickerDialog(AddProductActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().isEmpty() || edtCost.getText().toString().isEmpty() || edtCategory.getText().toString().isEmpty()){
                    String error = "champ requis";
                    Toast.makeText(AddProductActivity.this, error, Toast.LENGTH_SHORT).show();
                }else {

                    ProduitGson produitGson = new ProduitGson();
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    produitGson.setName(edtName.getText().toString());
                    produitGson.setCategory(Integer.parseInt(edtCategory.getText().toString()));
                    produitGson.setCost(Double.valueOf(edtCost.getText().toString()));
                    produitGson.setCreated_at(edtDate.getText().toString());

                    gson = new GsonBuilder().create();
                    JsonObject jsonObject = gson.toJsonTree(produitGson).getAsJsonObject();

                    ApiInterface apiService = ApiUser.getRetrofit().create(ApiInterface.class);

                    apiService.addProduct(jsonObject).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject produit = response.body();
                            Log.e("Reponse Json","" + produit);
                            JsonElement elementVente = produit.get("succes");

                            String service = elementVente.getAsString();
                            if (service.equals("ok")){
                                Toast.makeText(getApplicationContext(), "Produit enregistre", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddProductActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Produit non enregistre", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("response-failure", call.toString());
                            Toast.makeText(getApplicationContext(), "Produit non enregistre", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }
}