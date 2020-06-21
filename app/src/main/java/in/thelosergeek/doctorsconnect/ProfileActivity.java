package in.thelosergeek.doctorsconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName;
    TextView profileEmail;
    private TextView speciality, pspeciality;
    private TextView profileHeight , pheight;
    private TextView profileWeight , pweight;
    private TextView profileBlood_group, pblood_group;
    private TextView profileid , pprofileid;
    private Button changePassword;
    private ImageView profilepic;


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private StorageReference myref;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = (TextView) findViewById(R.id.tvname);
        profileEmail = (TextView) findViewById(R.id.tvEmail);
        speciality = findViewById(R.id.tvspeciality); //
        pspeciality = findViewById(R.id.speciality); //
        profileHeight = findViewById(R.id.tvheight);
        pheight = findViewById(R.id.height);
        pweight = findViewById(R.id.weight);
        pblood_group = findViewById(R.id.bloodgroup);
        pprofileid = findViewById(R.id.profileid);  //
        profileWeight = findViewById(R.id.tvweight);
        profileBlood_group = findViewById(R.id.tvbloodgroup);
        profileid = findViewById(R.id.tvprofileid); //
        changePassword = findViewById(R.id.btnchange);
        profilepic = findViewById(R.id.profilepic);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        myref = mStorage.getReference();
        firebaseUser = mAuth.getCurrentUser();


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(ProfileActivity.this, UpdateProfile.class));
            }
        });

        DatabaseReference databaseReference = mDatabase.getReference().child("Users_doctor");

        Query query1 = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String height = ""+ ds.child("Height").getValue();
                    String Weight = ""+ds.child("Weight").getValue();
                    String Blood_Group = ""+ds.child("Blood_Group").getValue();
                    String Speciality = ""+ds.child("Speciality").getValue();
                    String id = "" + ds.child("id").getValue();

                    profileName.setText(name);
                    profileEmail.setText(email);
                    profileHeight.setText(height);
                    profileWeight.setText(Weight);
                    profileHeight.setVisibility(View.INVISIBLE);
                    profileWeight.setVisibility(View.INVISIBLE);
                    profileBlood_group.setVisibility(View.INVISIBLE);
                    pheight.setVisibility(View.INVISIBLE);
                    pweight.setVisibility(View.INVISIBLE);
                    pblood_group.setVisibility(View.INVISIBLE);
                    profileBlood_group.setText(Blood_Group);

                    speciality.setText(Speciality);

                    profileid.setText(id);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });



        final DatabaseReference databaseReference1 = mDatabase.getReference().child("Users_patient");


        Query query = databaseReference1.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String height = ""+ ds.child("Height").getValue();
                    String Weight = ""+ds.child("Weight").getValue();
                    String Blood_Group = ""+ds.child("Blood_Group").getValue();

                    profileName.setText(name);
                    profileEmail.setText(email);
                    profileHeight.setText(height);
                    profileWeight.setText(Weight);
                    profileBlood_group.setText(Blood_Group);

                    //speciality.setText(Speciality);
                    speciality.setVisibility(View.INVISIBLE);
                    pspeciality.setVisibility(View.INVISIBLE);
                    profileid.setVisibility(View.INVISIBLE);
                    pprofileid.setVisibility(View.INVISIBLE);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

    }
}