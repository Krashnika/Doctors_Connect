package in.thelosergeek.doctorsconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterDoctor extends AppCompatActivity {

    private TextInputEditText mname, memail, mpassword, mrepassword, regno, doctor_speciaity;
    private FirebaseAuth mAuth;
    private ProgressDialog Regprogess;
    private DatabaseReference firebaseDatabase;
    ProgressDialog progressDialog;
    TextView login;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        mname = (TextInputEditText) findViewById(R.id.name);
        memail = (TextInputEditText) findViewById(R.id.email);
        mpassword = (TextInputEditText) findViewById(R.id.password);
        mrepassword = (TextInputEditText) findViewById(R.id.repassword);
        regno = (TextInputEditText) findViewById(R.id.regno);
        doctor_speciaity = (TextInputEditText) findViewById(R.id.speciality);
        login = findViewById(R.id.btnlogin);
        reg = findViewById(R.id.btnreg);
        mAuth = FirebaseAuth.getInstance();
        Regprogess = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User");

        login = findViewById(R.id.btnlogin);
        reg = findViewById(R.id.btnreg);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterDoctor.this, Login.class);
                startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = mname.getEditableText().toString();
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String id = regno.getText().toString().trim();
                String speciaity = doctor_speciaity.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    memail.setError("Invalid Email");
                    memail.setFocusable(true);
                } else if (password.length() < 6) {
                    mpassword.setError("Password length at least 6 characters");
                } else {
                    registerUserWithEmail(email, password, display_name, id, speciaity);

                }

            }
        });

    }

    private void registerUserWithEmail(String email, String password, final String display_name, final String id, final String speciality) {

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();


                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", display_name);
                            hashMap.put("id", id);
                            hashMap.put("Speciality", speciality);

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = firebaseDatabase.getReference("Users_doctor");

                            databaseReference.child(uid).setValue(hashMap);


                            Toast.makeText(RegisterDoctor.this, "Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterDoctor.this, MainActivity.class));
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterDoctor.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterDoctor.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}