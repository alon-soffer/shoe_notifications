package alon.soffer.shoenotifications;

import android.app.Application;
import android.content.SharedPreferences;

public class ShoeNotificationApp extends Application {
    public static final String ONBOARDED_KEY = "onboarded";
    private SharedPreferences sp = null;
    private boolean onboarded;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = this.getSharedPreferences("shoe_notifications_sp", MODE_PRIVATE);

        onboarded = sp.getBoolean(ONBOARDED_KEY, false);
    }

    public void setOnboardedToSp(){
        onboarded = true;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(ONBOARDED_KEY, true);
        editor.apply();
    }

    public boolean isOnboarded() {
        return onboarded;
    }

    public void setOnboarded(boolean onboarded) {
        this.onboarded = onboarded;
    }
}
