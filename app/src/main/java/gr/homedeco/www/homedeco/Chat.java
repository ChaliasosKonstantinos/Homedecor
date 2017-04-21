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
    private String sender, receiver, message;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // MOCKUP - TODO: MODIFY IT
        User user = new User();
        user.setUsername("Konstantinos");
//        localDatabase.setUserDetails(user);
//        sender = localDatabase.getUsername();
        receiver = "Christina";
        setTitle(receiver);

        List<PrivateMessage> messages = new ArrayList<>();

        PrivateMessage message = new PrivateMessage();
        message.setReceiver("Kostas");
        message.setSender("Christina");
        message.setMessage("Καλησπέρα");
        messages.add(message);

        PrivateMessage message1 = new PrivateMessage();
        message1.setReceiver("Kostas");
        message1.setSender("Christina");
        message1.setMessage("Θα ήθελα να κάνω μια ερώητηση..");
        messages.add(message1);

        PrivateMessage message2 = new PrivateMessage();
        message2.setReceiver("Kostas");
        message2.setSender("Christina");
        message2.setMessage("Κάνετε αποστολές κατ οίκον ή πρέπει να περάσουμε από εκεί να παραλάβουμε τα έπιπλα;");
        messages.add(message2);

        PrivateMessage message3 = new PrivateMessage();
        message3.setReceiver("Christina");
        message3.setSender("Kostas");
        message3.setMessage("Καλησπέρα σας κ. Χριστίνα");
        messages.add(message3);

        PrivateMessage message4 = new PrivateMessage();
        message4.setReceiver("Christina");
        message4.setSender("Kostas");
        message4.setMessage("Φυσικά και κάνουμε κατ οίκον αποστολή και με αγορές άνω των 100€ η αποστολή είναι ΔΩΡΕΑΝ!");
        messages.add(message4);

        PrivateMessage message5 = new PrivateMessage();
        message5.setReceiver("Kostas");
        message5.setSender("Christina");
        message5.setMessage("Ευχαριστούμε που μας προτιμήσατε!");
        messages.add(message5);

        populateChatView(messages);


        etMessage = (EditText) findViewById(R.id.etMessage);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
    }

    private void populateChatView(List<PrivateMessage> messages) {

        ListAdapter myAdapter = new ChatAdapter(this, messages);
        ListView privateChatListView = (ListView) findViewById(R.id.lvPrivateChat);
        privateChatListView.setAdapter(myAdapter);
        privateChatListView.setItemsCanFocus(true);
    }

    //Sends the private Message
    public void sendMessage(View view) {

    }
}
