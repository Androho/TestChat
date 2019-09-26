package io.github.androho.testchat.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.androho.testchat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder{
    public TextView textMessage, timeMessage;
    public ImageView avatarImage;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        textMessage=itemView.findViewById(R.id.text_message);
        timeMessage=itemView.findViewById(R.id.text_date);
        avatarImage=itemView.findViewById(R.id.profile_image);
    }
}
