package gr.homedeco.www.homedeco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends ArrayAdapter<PrivateMessage> {

    private LocalDatabase localDatabase;
    private String username;

    public ChatAdapter(Context context, List<PrivateMessage> message) {
        super(context, R.layout.custom_chat_bubble, message);
        // MOCKUP - TODO: MODIFY IT
//        username = localDatabase.getUsername();
        username = "Kostas";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        Button bMessage;
        PrivateMessage message = getItem(position);

        if (Objects.equals(message.getSender(), username)) {
            customView = myInflater.inflate(R.layout.custom_chat_bubble, parent, false);
            bMessage = (Button) customView.findViewById(R.id.bChatBubble);
            bMessage.setText(message.getMessage());
        } else {
            customView = myInflater.inflate(R.layout.custom_chat_bubble2, parent, false);
            bMessage = (Button) customView.findViewById(R.id.bChatBubble2);
            bMessage.setText(message.getMessage());
        }

        return customView;
    }

}
