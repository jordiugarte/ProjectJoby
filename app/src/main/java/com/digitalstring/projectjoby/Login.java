package com.digitalstring.projectjoby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Login extends AppCompatActivity {

    //Atributes
    SharedPreferences sharedPreferences;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;


    //Declare a firebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sharedPreferences = getSharedPreferences("oneTimeLogin", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        Boolean register_signin = intent.getBooleanExtra("register_signin",false);
        Button button = findViewById(R.id.buttonUser_loginActivity);
        if(register_signin) {
            button.setText("INICIAR SESIÓN");
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              signIn(view);
                                          }
            }
            );
        }else{
            button.setText("REGISTRARSE");
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              register(view);
                                          }
                                      }
            );
        }
        editTextEmail = findViewById(R.id.usuarioLoginActivity);
        editTextPassword = findViewById(R.id.passwordLoginActivity);

        ///initialize  firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }




    public  void signIn ( View view ){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if ( TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se requiere ingresar email",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Se requiere ingresar contraseña",Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("realizando consulta en linea...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            Toast.makeText(Login.this , " Bienvenido ", Toast.LENGTH_LONG).show();
                            //TODO make the call of the next activity
                        }else{
                            if ( task.getException() instanceof  FirebaseAuthUserCollisionException ) {
                                Toast.makeText(Login.this, "Usuario inexistente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Login.this, "no se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void register ( View view ){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if ( TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se requiere ingresar email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Se requiere ingresar contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("realizando consulta en linea...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            Toast.makeText(Login.this , " se ha registrado el email", Toast.LENGTH_LONG).show();
                        }else{
                            if ( task.getException() instanceof  FirebaseAuthUserCollisionException ) {
                                Toast.makeText(Login.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Login.this, "no se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    
}
