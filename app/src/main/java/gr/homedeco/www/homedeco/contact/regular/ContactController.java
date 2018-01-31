package gr.homedeco.www.homedeco.contact.regular;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

class ContactController {

    private Context context;

    ContactController(Context context) {
        this.context = context;
    }

    boolean sendEmail(String name, String email, String message) {
        String fMessage = "Όνομα: " + name + "\n\n";
        fMessage += "Email: " + email + "\n\n\n";
        fMessage += "Μήνυμα:\n\n" + message;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android App | Επικοινωνία");
        intent.putExtra(Intent.EXTRA_TEXT, fMessage);
        intent.setData(Uri.parse("mailto:chaliasos@gmail.com"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (android.content.ActivityNotFoundException ex) {
            return false;
        }
    }
}
