package com.example.diary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diary.model.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseFirestore fireStore;
    private RecyclerView rvNotes;
    private FloatingActionButton butAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        fireStore = FirebaseFirestore.getInstance();

        butAdd = findViewById(R.id.but_add);
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(myRef, Post.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Post, PostHolder>(options) {
            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.note_item, parent, false);
                return new PostHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostHolder holder, int position, Post model) {
                if (model != null){
                    holder.tvTitle.setText(model.getTitle());
                    holder.tvContent.setText(model.getContent());
                    if (model.getDate() != null)
                        holder.tvDate.setText(model.getDate());
                    if (model.getEmotion() != null)
                        holder.tvEmotion.setText(model.getEmotion());
                    holder.layoutNote.setBackgroundColor(Color.parseColor(model.getColor()));
                    ImageView ivAction = holder.itemView.findViewById(R.id.iv_action);

                    ivAction.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                            popupMenu.setGravity(Gravity.END);
                            popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(@NonNull MenuItem item) {
                                    AlertDialog.Builder mDialog = new AlertDialog.Builder(MainActivity.this);
                                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                                    View myView = inflater.inflate(R.layout.add_note, null);
                                    mDialog.setView(myView);

                                    AlertDialog dialog = mDialog.create();
                                    dialog.setCancelable(true);

                                    TextView tvAddNote = myView.findViewById(R.id.tv_add_note);
                                    tvAddNote.setText("Edit the note");
                                    Button butSave = myView.findViewById(R.id.but_save);
                                    butSave.setText("UPDATE");
                                    EditText etTitle = myView.findViewById(R.id.et_title);
                                    etTitle.setText(model.getTitle());
                                    EditText etContent = myView.findViewById(R.id.et_content);
                                    etContent.setText(model.getContent());

                                    RadioGroup radioGroup = myView.findViewById(R.id.radio_group);
                                    RadioButton rbHappy = myView.findViewById(R.id.rb_happy);
                                    RadioButton rbSad = myView.findViewById(R.id.rb_sad);
                                    RadioButton rbSurprise = myView.findViewById(R.id.rb_surprise);

                                    if (model.getEmotion().trim().equals("Happy")) {
                                        rbHappy.setChecked(true);
                                    }
                                    else if (model.getEmotion().trim().equals("Sad")) {
                                        rbSad.setChecked(true);
                                    }
                                    else if (model.getEmotion().trim().equals("Surprise")) {
                                        rbSurprise.setChecked(true);
                                    }

                                    butSave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String title = etTitle.getText().toString();
                                            String content = etContent.getText().toString();
                                            RadioButton checkedRb = myView.findViewById(radioGroup.getCheckedRadioButtonId() );
                                            String emotion = checkedRb.getText().toString().trim();

                                            myRef.child(model.getId()).setValue(new Post(model.getId(),title,content,getRandomColor(),model.getDate(), emotion))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(MainActivity.this, "Update note successfully", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(MainActivity.this, "Update note failed", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    return true;
                                }
                            });

                            popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(@NonNull MenuItem item) {
                                    myRef.child(model.getId()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                    return false;
                                }
                            });

                            popupMenu.show();
                        }
                    });
                }
            }
        };

        rvNotes.setAdapter(adapter);
        adapter.startListening();
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvDate;
        public TextView tvEmotion;
        public LinearLayout layoutNote;
        public PostHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvContent = view.findViewById(R.id.tv_content);
            tvDate = view.findViewById(R.id.tv_date);
            tvEmotion = view.findViewById(R.id.tv_emotion);
            layoutNote = view.findViewById(R.id.layout_note);
        }
    }

    public void addNote(){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.add_note, null);
        mDialog.setView(myView);

        AlertDialog dialog = mDialog.create();
        dialog.setCancelable(true);

        Button butSave = myView.findViewById(R.id.but_save);
        EditText etTitle = myView.findViewById(R.id.et_title);
        EditText etContent = myView.findViewById(R.id.et_content);

        RadioGroup radioGroup = myView.findViewById(R.id.radio_group);

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = myRef.push().getKey();
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                RadioButton checkedRb = myView.findViewById( radioGroup.getCheckedRadioButtonId() );
                String emotion = checkedRb.getText().toString().trim();

                Date date = new Date();

                myRef.child(id).setValue(new Post(id,title,content,getRandomColor(), getDateStr(date), emotion))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "Add note successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Add note failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private String getRandomColor(){
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#5aa06b");
        colors.add("#ffb2d6");
        colors.add("#f7744a");
        colors.add("#744cc0");
        colors.add("#e2cb89");
        colors.add("#67796a");
        colors.add("#904660");
        colors.add("#4b6f89");
        colors.add("#956060");

        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_logout:
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getDateStr(Date date){
        String str = null;
        str = "" + ((1900 + date.getYear()) + " - " + (1 + date.getMonth()) + " - " + date.getDate());
        return str;
    }

    //    private void login(String email, String password){
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Log.d("DEBUG", "successful");
//                        } else {
//                            Log.d("DEBUG", "failed");
//                        }
//                    }
//                });
//    }
//
//    private void createNewUser(String newEmail, String newPass){
//        mAuth.createUserWithEmailAndPassword(newEmail, newPass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Log.d("create", "successful");
//                        } else {
//                            Log.d("create", "failed");
//                        }
//                    }
//                });
//    }
//
//    private void resetPass(String email){
//        mAuth.sendPasswordResetEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Log.d("reset pw", "successful");
//                        } else {
//                            Log.d("reset pw", "failed");
//                        }
//                    }
//                });
//    }
//
//    private void signOut(){
//        mAuth.signOut();
//    }
//
//    private void postDataToRealtimeDB(String data){
//        myRef.setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Log.d("post", "successful");
//                        } else {
//                            Log.d("post", "failed");
//                        }
//                    }
//                });
//    }
//
//    private void readDataFromRealtimeDB(){
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                Log.d("Debug","Value is "+value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("Debug","Fail to read value");
//            }
//        });
//    }
//
//    private void postDataToFirestore(){
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//        // Add a new document with a generated ID
//        fireStore.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("DEBUG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("DEBUG", "Error adding document", e);
//                    }
//                });
//    }
//
//    public void addPostData(Post data){
//        DatabaseReference myRefRoot = database.getReference();
//        myRefRoot.child("posts").setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Log.d("post", "successful");
//                        } else {
//                            Log.d("post", "failed");
//                        }
//                    }
//                });
//    }
}