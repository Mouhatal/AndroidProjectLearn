package gaaynako.transpay.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtFirstName,txtLastName,txtUserName,txtPassWord;
    private Button btnSignUp;
    private String firstName,lastName,userName,passWord;
    private BdUser db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new BdUser(this);
        txtFirstName=findViewById(R.id.txtFirstName);
        txtLastName=findViewById(R.id.txtLastName);
        txtUserName=findViewById(R.id.txtUserName);
        txtPassWord=findViewById(R.id.txtPassWord);
        btnSignUp=findViewById(R.id.btnSignUp);
        //signIn=findViewById(R.id.signInBtn);
        // goBackIcon=findViewById(R.id.goBackIcon);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                userName = txtUserName.getText().toString().trim();
                passWord = txtPassWord.getText().toString().trim();

                if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || passWord.isEmpty()){
                    String error = getString(R.string.error_fields);
                    Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
                }else {
                    boolean b = db.create(firstName, lastName, userName, passWord);
                    if (b == true){
                        Toast.makeText(SignUpActivity.this, "Utilisateur créé", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        intent.putExtra("USERNAME",userName);
                        intent.putExtra("PASSWORD", passWord);
                        startActivity(intent);}
                    else
                    {Toast.makeText(SignUpActivity.this, "Utilisateur non créé", Toast.LENGTH_SHORT).show();}
                }

            }

        });

    }
}