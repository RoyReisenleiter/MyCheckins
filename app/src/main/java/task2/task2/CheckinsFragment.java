package task2.task2;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckinsFragment extends Fragment {

    private Checkins mCheckins;
    private EditText mTitleField;
    private EditText mPlaceField;
    private EditText mDetailsField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CheckBox mDislikeCheckBox;
    private Button mDeleteButton;
    private Button mSuspectButton;
    private Button mReportButton;
    private ImageButton mPhotoButton;
    private Button mContactButton;
    private ImageView mImageView;
    private GoogleApiClient mClient;
    private File mPhotoFile;
    private ImageView mPhotoView;
    private TextView mLocationField;
    private Button mShowMapButton;
    private  boolean mIsNewCrime;
    private static final String ARG_IS_NEW_CRIME = "is_new_crime";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String ARG_CRIME_ID = "crime_id";
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 2;



    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCheckins = new Checkins();
        UUID checkinId = (UUID) getActivity().getIntent()
                .getSerializableExtra(CheckinsActivity.EXTRA_CHECKIN_ID);
        mCheckins = CheckinsLab.get(getActivity()).getCheckin(checkinId);
        mPhotoFile = CheckinsLab.get(getActivity()).getPhotoFile(mCheckins);
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                       // if (mIsNewCrime){
                            LocationRequest request = LocationRequest.create();
                            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            request.setNumUpdates(1);
                            request.setInterval(0);
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            LocationServices.getFusedLocationProviderClient(getActivity())
                                    .requestLocationUpdates(request, new LocationCallback() {
                                        @Override
                                        public void onLocationResult(LocationResult locationResult) {
                                            super.onLocationResult(locationResult);
                                            Location location = locationResult.getLastLocation();
                                            setLocation(location);

                                        }
                                    }, null);
                       // }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
    }

    private void setLocation(Location location) {
        mCheckins.setLongitude(location.getLongitude());
        mCheckins.setLatitude(location.getLatitude());
        mLocationField.setText(
                getString(R.string.location_text,
                        mCheckins.getLatitude(),
                        mCheckins.getLongitude()));

    }


    @Override
    public void onPause() {
        super.onPause();

        CheckinsLab.get(getActivity())
                .updateCheckin(mCheckins);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activity, container, false);
        PackageManager packageManager = getActivity().getPackageManager();

        mLocationField = v.findViewById(R.id.location_label);
        mShowMapButton = v.findViewById(R.id.map_button);
        mShowMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MapsActivity.newIntent(getContext(),
                        mCheckins.getLatitude(), mCheckins.getLongitude());
                startActivity(intent);
            }
        });

        mDateButton = (Button) v.findViewById(R.id.activity_date);
        updateDate();
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCheckins.getDate());
                dialog.setTargetFragment(CheckinsFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        //pickContact.addCategory(Intent.CATEGORY_HOME); **this was supposed to grey out the choose suspect button**
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        //PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,
                PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mContactButton.setEnabled(false);
        }

        mPhotoButton = (ImageButton) v.findViewById(R.id.checkin_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "task2.task2.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.receipt_photo);
        updatePhotoView();


        mTitleField = (EditText) v.findViewById(R.id.checkin_title);
        mTitleField.setText(mCheckins.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this space is left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCheckins.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this one too
            }

        });

        mPlaceField = (EditText) v.findViewById(R.id.place_title);
        mPlaceField.setText(mCheckins.getPlace());
        mPlaceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mCheckins.setPlace(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDetailsField = (EditText) v.findViewById(R.id.details_title);
        mDetailsField.setText(mCheckins.getDetails());
        mDetailsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mCheckins.setDetails(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // need to use this button
        mDeleteButton = v.findViewById(R.id.remove_activity);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckinsLab.get(getActivity()).deleteCheckin(mCheckins);
                getActivity().finish();
            }
        });
        //this may not be needed
        if (mIsNewCrime){
            mDeleteButton.setVisibility(View.INVISIBLE);

        } else {
            mLocationField.setText(
                    getString(R.string.location_text,
                            mCheckins.getLatitude(),
                            mCheckins.getLongitude()));
        }

        /*mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCheckins.isLiked());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckins.setLiked(isChecked);
            }

        });*/

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.activity_solved);
        mSolvedCheckBox.setChecked(mCheckins.isLiked());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mDislikeCheckBox.isChecked()){
                    mDislikeCheckBox.setChecked(false);

                }
                mCheckins.setLiked(isChecked);
                //mSolvedCheckBox.setChecked(true);


            }

        });

        mDislikeCheckBox = (CheckBox)v.findViewById(R.id.dislike);
        mDislikeCheckBox.setChecked(mCheckins.isDisliked());
        mDislikeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mSolvedCheckBox.isChecked()){
                    mSolvedCheckBox.setChecked(false);

                }
                mCheckins.setDisliked(isChecked);
                //mDislikeCheckBox.setChecked(true);
            }

        });

        mReportButton = (Button) v.findViewById(R.id.activity_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCheckinReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.activity_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        if (mCheckins.getSuspect() != null) {
            mSuspectButton.setText(mCheckins.getSuspect());
        }

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCheckins.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
// Specify which fields you want your query to return
// values for
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
// Perform your query - the contactUri is like a "where"
// clause here
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);
            try {
// Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }
// Pull out the first column of the first row of data -
// that is your suspect's name
                c.moveToFirst();
                String suspect = c.getString(0);
                mCheckins.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            } finally {
                c.close();
            }
        }else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "task2.task2.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }

    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void updateDate() {
        mDateButton.setText(mCheckins.getDate().toString());
    }


    private String getCheckinReport() {
        String solvedString = null;
        if (mCheckins.isLiked()){
            solvedString = getString(R.string.activity_report_solved);
        }else{
            solvedString = getString(R.string.activity_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCheckins.getDate()).toString();

        String suspect = mCheckins.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.activity_report_no_suspect);
        }else{
            suspect = getString(R.string.activity_report_suspect, suspect);
        }

        String report = getString(R.string.activity_report, mCheckins.getTitle(), dateString, solvedString, suspect);

        return report;
    }

}
