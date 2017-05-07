package gr.homedeco.www.homedeco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private EditText etMessage;
    private ListView lvPrivateChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etMessage = (EditText) findViewById(R.id.etMessage);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
        getConversation();
    }

    private void getConversation() {
        // TODO: HTTP 401 ERROR
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.getConversation(new GetConversationCallback() {
            @Override
            public void done(List<PrivateMessage> returnedList) {
                populateChatView(returnedList);
            }
        });
    }

    private void populateChatView(List<PrivateMessage> messages) {

        ListAdapter myAdapter = new ChatAdapter(this, messages);
        ListView privateChatListView = (ListView) findViewById(R.id.lvPrivateChat);
        privateChatListView.setAdapter(myAdapter);
        privateChatListView.setItemsCanFocus(true);
    }

    //Sends the private Message
    public void sendMessage(View view) {
        // TODO: HTTP 401 ERROR
        PrivateMessage message = new PrivateMessage();
        message.setMessage(etMessage.getText().toString());
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.sendMessage(message, new GetMessageCallback() {
            @Override
            public void done(String response) {
                etMessage.setText("");
            }
        });
    }
}
