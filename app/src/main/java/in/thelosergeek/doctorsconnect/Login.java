package in.thelosergeek.doctorsconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    public Button login;

    TextView reg;
    EditText email;
    EditText pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.btnlogin);
        reg = findViewById(R.id.btnreg);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();


        if (user != null) {
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate(email.getText().toString(), pass.getText().toString());
                // Intent intent = new Intent(Login.this,MainActivity.class);
                // startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                View mview = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                Button btnregdoc = mview.findViewById(R.id.btnregdoc);
                Button btnregpatient = mview.findViewById(R.id.btnregpatient);
                Button btncancel = mview.findViewById(R.id.btncancel);
                alert.setView(mview);
                final AlertDialog dialog = alert.create();
                dialog.show();

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

                btnregdoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Login.this, RegisterDoctor.class);
                        startActivity(intent);


                    }
                });

                btnregpatient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Login.this, RegisterPatient.class);
                        startActivity(intent);

                    }
                });
            }
        });


    }

    private void validate(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkEmailVerification();

                } else {
                    Toast.makeText(Login.this, "Enter correct email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(Login.this, MainActivity.class));

//        if(emailflag){
        //          finish();
        //        startActivity(new Intent(MainActivity.this,SecondActivity.class));
        //  }else{
        //    Toast.makeText(this,"Please verify your email address",Toast.LENGTH_SHORT).show();
        //  mAuth.signOut();
        //}

    }
}
