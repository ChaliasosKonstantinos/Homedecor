package gr.homedeco.www.homedeco.contact.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import gr.homedeco.www.homedeco.R;

public class ChatAdapter extends ArrayAdapter<PrivateMessage> {

    public ChatAdapter(Context context, List<PrivateMessage> message) {
        super(context, R.layout.custom_chat_bubble, message);
    }

    /**
     * Populates the view holder of the individual chat message
     *
     * @param position of the holder on the list
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        Button bMessage;
        PrivateMessage message = getItem(position);

        if (message.getIsUser() == 1) {
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
