package task2.task2;

import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.UUID;

public class CheckinsActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHECKIN_ID = "task2.task2.checkin_id";
    public static final int REQUEST_ERROR = 0;

    public static Intent newIntent(Context packageContext, UUID checkinId) {
        Intent intent = new Intent(packageContext, CheckinsActivity.class);
        intent.putExtra(EXTRA_CHECKIN_ID, checkinId);
        return intent;
    }

    /*public static final Intent newDelIntent(Context packageContext, UUID crimeId){
        Intent delIntent = new Intent(packageContext, CheckinsActivity.class);
        delIntent.removeExtra(EXTRA_CHECKIN_ID);
        return delIntent;
    }*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = new CheckinsFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }*/

    @Override
    protected Fragment createFragment() {
        return new CheckinsFragment();
    }

    @Override
    protected void onResume(){
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode,
                    REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });

            errorDialog.show();
        }
    }
}
