package gr.homedeco.www.homedeco;

import java.util.List;

public interface GetConversationCallback {
    void done(List<PrivateMessage> returnedList);
}
