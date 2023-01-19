package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.whatsapp.Adapter.ChatAdapter;
import com.example.whatsapp.databinding.ActivityChatdetailBinding;
import com.example.whatsapp.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatdetailActivity extends AppCompatActivity {

FirebaseDatabase database;
    ActivityChatdetailBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   database=FirebaseDatabase.getInstance();
   auth=FirebaseAuth.getInstance();
   final String senderId= auth.getUid();
   String receiveId=getIntent().getStringExtra("userid");
   String username=getIntent().getStringExtra("username");
   String profilepic =getIntent().getStringExtra("profilepic");
   binding.Usernameid.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.ic_avatar).into(binding.profileImage);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chatdetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this,receiveId);
        binding.chatrecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatrecyclerView.setLayoutManager(layoutManager);

        final String senderroom=senderId+receiveId;
        final String receiverroom=receiveId+senderId;

        database.getReference().child("chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    model.setMessageId(snapshot.getKey());
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etmeassage.getText().toString().isEmpty()){
                    binding.etmeassage.setError("Type your message");
                    return;

                }
               String message= binding.etmeassage.getText().toString();
               final MessageModel model=new MessageModel(senderId,message);
               //time
                model.setTimestamp(new Date().getTime());
                // text send krnai kai baad edittext empty ho jai
                binding.etmeassage.setText(" ");
                // Create in database a new node
                database.getReference().child("chats").child(senderroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats").child(receiverroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });

    }
}