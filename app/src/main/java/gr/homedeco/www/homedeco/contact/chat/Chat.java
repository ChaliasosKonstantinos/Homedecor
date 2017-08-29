package gr.homedeco.www.homedeco.contact.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.server.callbacks.GetConversationCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetMessageCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;

public class Chat extends AppCompatActivity {

    private EditText etMessage;
    private ListView lvPrivateChat;
    private List<PrivateMessage> conversation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etMessage = (EditText) findViewById(R.id.etMessage);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
        getConversation();
    }

    private void getConversation() {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.getConversation(new GetConversationCallback() {
            @Override
            public void done(List<PrivateMessage> returnedList) {
                conversation = returnedList;
                populateChatView(conversation);
            }
        });
    }

    private void populateChatView(List<PrivateMessage> messages) {
        ListAdapter myAdapter = new ChatAdapter(this, messages);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
        lvPrivateChat.setAdapter(myAdapter);
        lvPrivateChat.setItemsCanFocus(true);
    }

    //Sends the private Message
    public void sendMessage(View view) {
        final PrivateMessage message = new PrivateMessage();
        message.setMessage(etMessage.getText().toString());
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.sendMessage(message, new GetMessageCallback() {
            @Override
            public void done(String response) {
                etMessage.setText("");
                message.setIsUser(1);
                conversation.add(message);
                populateChatView(conversation);
            }
        });
    }
}
