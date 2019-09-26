package io.github.androho.testchat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.library.bubbleview.BubbleLinearLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import io.github.androho.testchat.R;
import io.github.androho.testchat.adapter.MessagesAdapter;
import io.github.androho.testchat.holder.ChatViewHolder;
import io.github.androho.testchat.model.Message;

public class MainActivity extends BaseActivity {

    private Button showReward;
    private BubbleLinearLayout bubbleLinearLayout;
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseRecyclerAdapter<Message, ChatViewHolder> firebaseRecyclerAdapter;
    private RecyclerView rvMesageList;
    private FloatingActionButton btnSend;
    private ConstraintLayout activity_main;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Message message;
    private Query query;
    private FirebaseUser user;
    public FirebaseRecyclerOptions<Message> options;
    public LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = findViewById(R.id.activity_main);
        showReward = findViewById(R.id.btn_show_rew);
        mAdView = findViewById(R.id.adView);
        rvMesageList = findViewById(R.id.rv_message_list);
        btnSend = findViewById(R.id.btn_send);
        bubbleLinearLayout = findViewById(R.id.btn_reward_video);
        mAdView.loadAd(adRequest);

        layoutManager = new LinearLayoutManager(this);
        rvMesageList.setLayoutManager(layoutManager);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("messages");
        query = mDatabaseReference;
        options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class).build();


        View.OnClickListener onClickShow = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                loadRewardedVideoAd();
                bubbleLinearLayout.setVisibility(View.GONE);
            }
        };
        showReward.setOnClickListener(onClickShow);

        View.OnClickListener onClickSend = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkCurrentUser();
                String userPhotoUrl;
                String userMail = "";
                if (user.getPhotoUrl() != null) {
                    userPhotoUrl = user.getPhotoUrl().toString();
                } else
                    userMail = user.getEmail();
                    userPhotoUrl = String.valueOf(R.drawable.no_avatar);
                EditText input = findViewById(R.id.editText);
                message = new Message(input.getText().toString(),
                        userMail, userPhotoUrl, true);
                mDatabaseReference.push().setValue(message);
                input.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                displayChat();
                scrollToEnd();
            }
        };
        btnSend.setOnClickListener(onClickSend);
        isLogIn();
    }

    private void isLogIn() {
        checkCurrentUser();
        if (user == null) {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(), SIGN_IN_REQUEST_CODE);
        } else {
            displayChat();
        }
    }

    public void checkCurrentUser(){
        if (user==null)
            user=FirebaseAuth.getInstance()
                    .getCurrentUser();
    }

    private void scrollToEnd() {
        rvMesageList.scrollToPosition(firebaseRecyclerAdapter.getItemCount()-1);
    }

    private void displayChat() {
        firebaseRecyclerAdapter = new MessagesAdapter(options);
        rvMesageList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(activity_main, getString(R.string.login), Snackbar.LENGTH_SHORT).show();
                displayChat();
            } else {
                Snackbar.make(activity_main, getString(R.string.logout), Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(activity_main, getString(R.string.login), Snackbar.LENGTH_SHORT).show();
                            user=null;
                            isLogIn();
                        }
                    });
        }
        return true;
    }

    @Override
    public void onStart() {
        firebaseRecyclerAdapter.startListening();
        if (firebaseRecyclerAdapter == null) {
            displayChat();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
        super.onStop();
    }
}
