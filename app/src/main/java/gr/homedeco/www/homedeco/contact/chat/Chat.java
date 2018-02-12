package gr.homedeco.www.homedeco.contact.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.homedeco.www.homedeco.Main;
import gr.homedeco.www.homedeco.R;
import gr.homedeco.www.homedeco.aboutUs.AboutUs;
import gr.homedeco.www.homedeco.server.callbacks.GetConversationCallback;
import gr.homedeco.www.homedeco.server.callbacks.GetMessageCallback;
import gr.homedeco.www.homedeco.server.requests.ServerRequests;
import gr.homedeco.www.homedeco.user.UserController;
import gr.homedeco.www.homedeco.user.profile.UserProfile;

public class Chat extends AppCompatActivity {

    private EditText etMessage;
    private ListView lvPrivateChat;
    private List<PrivateMessage> conversation = new ArrayList<>();
    private ServerRequests serverRequests;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etMessage = (EditText) findViewById(R.id.etMessage);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
        serverRequests = new ServerRequests(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                getConversation();
                handler.postDelayed(runnable, 1000);
            }
        };
        initConvThread();
    }

    /**
     * On Activity destroy destroys the conversation fetching runnable
     */
    @Override
    protected void onDestroy() {
        destroyConvThread();
        super.onDestroy();
    }

/* ========================================= HELPERS =============================================== */

    /**
     * Fetches user's conversation from the server
     */
    private void getConversation() {
        serverRequests.getConversation(new GetConversationCallback() {
            @Override
            public void done(List<PrivateMessage> returnedList) {
                conversation = returnedList;
                populateChatView(conversation);
            }
        });
    }

    /**
     * Populates the view
     */
    private void populateChatView(List<PrivateMessage> messages) {
        ListAdapter myAdapter = new ChatAdapter(this, messages);
        lvPrivateChat = (ListView) findViewById(R.id.lvPrivateChat);
        lvPrivateChat.setAdapter(myAdapter);
        lvPrivateChat.setItemsCanFocus(true);
    }

    /**
     * Sends the user's message to server and repopulates the view
     *
     * @param view the View containing the button that was clicked
     */
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

    /**
     * Starts runnable which fetch the conversation every 1 second
     */
    private void initConvThread() {
        runnable.run();
    }

    /**
     * Destroy runnable which fetch the conversation
     */
    private void destroyConvThread() {
        handler.removeCallbacks(runnable);
    }

/* ========================================= MENU =============================================== */

    /**
     * Creates Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_in_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Setting up menu listeners
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_userProfile:
                startActivity(new Intent(this, UserProfile.class));
                break;
            case R.id.action_logout:
                UserController uc = new UserController(this);
                uc.logoutUser();
                startActivity(new Intent(this, Main.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
