package gaaynako.transpay.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private EditText txtUserName1,txtPassWord1;
    private Button btnSignIn;
    private String userName,passWord;
    private BdUser db = new BdUser(SignInActivity.this);
    private TextView signIn;
    private ImageView goBackIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtUserName1=findViewById(R.id.txtUserName1);
        txtPassWord1=findViewById(R.id.txtPassWord1);
        btnSignIn=findViewById(R.id.btnSignIn);
        //signIn=findViewById(R.id.signInBtn);
        //goBackIcon=findViewById(R.id.goBackIcon);
        final String tmp_userName=getIntent().getStringExtra("USERNAME");
        final String tmp_passWord=getIntent().getStringExtra("PASSWORD");

        //signUp=findViewById(R.id.signUpBtn);
        // goBackIcon1=findViewById(R.id.goBackIcon);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=txtUserName1.getText().toString().trim();
                passWord=txtPassWord1.getText().toString().trim();

                if ( userName.isEmpty() || passWord.isEmpty())
                {
                    String error = getString(R.string.error_fields);
                    Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean isExist = db.checkUserExist(userName,passWord);
                    try {
                        if(isExist){
                            Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            String error = getString(R.string.error_parameters);
                            Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(SignInActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}