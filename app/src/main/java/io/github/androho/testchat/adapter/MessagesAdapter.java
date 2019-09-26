package io.github.androho.testchat.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.github.androho.testchat.R;
import io.github.androho.testchat.holder.ChatViewHolder;
import io.github.androho.testchat.model.Message;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MessagesAdapter extends FirebaseRecyclerAdapter<Message, ChatViewHolder> {
    public View view;
    public String userMail;
    private List<Message> messages;
    private Message message;
    private String senderMail;


    public MessagesAdapter(FirebaseRecyclerOptions<Message> options) {
        super(options);
        this.messages = options.getSnapshots();
        userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("\\s+", "");
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int position = viewType;
        senderMail = new String(getItem(position).getUserMail().replaceAll("\\s+", ""));
        boolean isSend = userMail.equals(senderMail);
        if (isSend) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_in, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_out, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull final Message message) {
        holder.textMessage.setText(message.getTextMessage());
        holder.timeMessage.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getTimeMessage()));
        String photoUrl;
        final String sMail;
        sMail = message.getUserMail().replaceAll("\\s+", "");
        if (message.getUserPhotoUrl() != null) {
            photoUrl = message.getUserPhotoUrl();
        } else
            photoUrl = String.valueOf(R.drawable.no_avatar);
        Glide.with(view)
                .load(photoUrl)
                .error(Glide.with(view)
                        .load(R.drawable.no_avatar))
                .into(holder.avatarImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean compare =userMail.equals(sMail);
                if (compare) {
                    @SuppressLint("RestrictedApi") Toast toast = Toast.makeText(getApplicationContext(),
                            v.getContext().getResources().getString(R.string.toast_mess) + " " + sMail, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Message getItem(int position) {
        message = messages.get(position);
        return message;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
