package in.jainakshat.pro_trackr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.filepicker.Filepicker;
import io.filepicker.FilepickerCallback;
import io.filepicker.models.FPFile;

/**
 * Created by Akshat Jain on 1/15/2016.
 */
public class DashboardActivityTab1 extends AppCompatActivity {

    private Button btn_captureAndAddEntry;
    private EditText et_doctorName;
    private FPFile mFPFile;
    private Calendar mCurrentCalendar;

    private Switch sw_toggleCamera;

    private ProgressDialog progressDialog;

    private static final String TAG = "PRO TrackR";
    private File mFile;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private Camera.PictureCallback mJpegCallback;
    private boolean mOpenCameraFlag = false;

    private Firebase ref = new Firebase("https://girlsapp.firebaseio.com/");

    private Session mSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dashboard_tab1);
        mSession = new Session(DashboardActivityTab1.this);

        btn_captureAndAddEntry = (Button) findViewById(R.id.button5);
        et_doctorName = (EditText) findViewById(R.id.editText4);
        mSurfaceView = (SurfaceView) findViewById(R.id.texture);
        sw_toggleCamera = (Switch) findViewById(R.id.switch1);
        sw_toggleCamera.setChecked(false);
        mFile = new File(DashboardActivityTab1.this.getExternalFilesDir(null), "file.jpg");

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceHolderCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        progressDialog = new ProgressDialog(DashboardActivityTab1.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait..");

        sw_toggleCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && mOpenCameraFlag) {
                    openCamera();
                } else if(isChecked && !mOpenCameraFlag) {
                    showToast("Error: Try closing the application and try again.");
                    Log.e(TAG, "Error: Try closing the application and try again.");
                } else if(!isChecked) {
                    closeCamera();
                }
            }
        });

        btn_captureAndAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_doctorName.getText().toString().equals("") || !mOpenCameraFlag || mCamera==null){
                    showToast("Error: Either text fiels is empty or camera is closed.");
                    Log.e(TAG, "Error: Either text fiels is empty or camera is closed.");
                    return;
                }
                progressDialog.show();
                mCurrentCalendar = Calendar.getInstance();
                captureImage();
            }
        });

        mJpegCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(mFile);
                    outStream.write(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outStream.close();
                        Filepicker.uploadLocalFile(Uri.fromFile(mFile), DashboardActivityTab1.this, new FilepickerCallback() {
                            @Override
                            public void onFileUploadSuccess(FPFile fpFile) {
                                mFPFile = fpFile;
                                writeData();
                            }

                            @Override
                            public void onFileUploadError(Throwable error) {
                                System.out.println("Filepicker Error: " + error);
                            }

                            @Override
                            public void onFileUploadProgress(Uri uri, float progress) {
                                // Do something on progress
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mOpenCameraFlag = true;
        sw_toggleCamera.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        Filepicker.unregistedLocalFileUploadCallbacks();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        closeCamera();
        mOpenCameraFlag = false;
        super.onPause();
    }

    private final SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mOpenCameraFlag = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { refreshCamera(); }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) { /*closeCamera();*/ }
    };

    public void captureImage() {
        mCamera.takePicture(null, null, mJpegCallback);
    }

    public void refreshCamera() {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mOpenCameraFlag = true;
    }

    private void openCamera() {
        try {
            mCamera = Camera.open();
        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }
        Camera.Parameters param = mCamera.getParameters();
        param.setRotation(90);
        param.setJpegQuality(100);
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(param);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

    private void closeCamera() {
        if(mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void showToast(final String text) {
        final Activity activity = DashboardActivityTab1.this;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void writeData() {
        // Save the details to the database and fetch them according to the Rajeev's need.

        final Firebase push_ref = ref.child("visits").push();
        Map<String, String> data = new HashMap<String, String>();
        data.put("doctor_name", et_doctorName.getText().toString());
        data.put("image_url", mFPFile.getUrl());
        data.put("date", mCurrentCalendar.get(Calendar.DATE)+":"+mCurrentCalendar.get(Calendar.MONTH)+":"+mCurrentCalendar.get(Calendar.YEAR));
        data.put("time", mCurrentCalendar.get(Calendar.HOUR)+":"+mCurrentCalendar.get(Calendar.MINUTE));
        data.put("user_phone", mSession.getphone());
        push_ref.setValue(data, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                ref.child("Users").child(mSession.getUserId()).child("allVisits").push().setValue(push_ref.getKey());
                ref.child("Users").child(mSession.getUserId()).child("visits").child( mCurrentCalendar.get(Calendar.DATE)+":"+mCurrentCalendar.get(Calendar.MONTH)+":"+mCurrentCalendar.get(Calendar.YEAR)).push().setValue(push_ref.getKey(), new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        mOpenCameraFlag = true;
                        sw_toggleCamera.setChecked(false);
                        et_doctorName.setText("");
                        closeCamera();
                        progressDialog.dismiss();
                        showToast(mSession.getUserId()+"jain");
                    }
                });
            }
        });
    }
}
