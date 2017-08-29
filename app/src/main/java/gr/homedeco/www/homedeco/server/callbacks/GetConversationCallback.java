package gr.homedeco.www.homedeco.server.callbacks;

import java.util.List;

import gr.homedeco.www.homedeco.contact.chat.PrivateMessage;

public interface GetConversationCallback {
    void done(List<PrivateMessage> returnedList);
}
