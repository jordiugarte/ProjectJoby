package com.digitalstring.projectjoby;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignInUserActivity extends AppCompatActivity {

    //Atributes
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;


    //Declare a firebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);
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
                            Toast.makeText(SignInUserActivity.this , " Bienvenido ", Toast.LENGTH_LONG).show();
                            //TODO make the call of the next activity
                        }else{
                            if ( task.getException() instanceof  FirebaseAuthUserCollisionException ) {
                                Toast.makeText(SignInUserActivity.this, "Usuario inexistente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignInUserActivity.this, "no se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

}
