package com.inanutshell.abhijithneilabraham.firebaseandroid;

import android.app.DownloadManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    //these are the views
    TextView textViewStatus;

    EditText editTextFilename;
    Button downloadbutton;
    ProgressBar progressBar;
    EditText email;
    Spinner dropdown;
    String Wordcount;




    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    Context context;
    // String filename = "myfile";
    
    String DOWNLOAD_DIR="Download/";
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.inanutshell.abhijithneilabraham.firebaseandroid.R.layout.activity_main);

        MobileAds.initialize(this,
                getString(com.inanutshell.abhijithneilabraham.firebaseandroid.R.string.admob_app_id));
        mAdView = findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);
        //getting firebase objects
        downloadbutton=(Button)findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.downloadfile);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //getting the views

        textViewStatus = (TextView) findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.editTextFileName);
        progressBar = (ProgressBar) findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.progressbar);
        email=(EditText)findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.email);
        dropdown = (Spinner) findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.wordcount);



downloadbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       // Toast.makeText(getApplicationContext(),Wordcount,Toast.LENGTH_LONG).show();
        download();
    }
});
        //attaching listeners to views
        findViewById(com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.buttonUploadFile).setOnClickListener(this);
       // findViewById(R.id.textViewUploads).setOnClickListener(this);
       // findViewById(R.id.downloadfile).setOnClickListener(this);
    }
private void del(){
    StorageReference delref=mStorageReference.child((email.getText().toString()+"/"+"final.txt"));
    delref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });
}
    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_CODE);
    }
    public void postData() {
        // Create a new HttpClient and Post Header
        RequestQueue queue = Volley.newRequestQueue(this);
        Wordcount=String.valueOf(dropdown.getSelectedItem());
        del();

        final   String url ="https://abhijithneilabraham.pythonanywhere.com/"+email.getText().toString()+"/"+Wordcount+"/";

        //  final String url = url1.replaceAll("\\s+", "");

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        downloadbutton.setText("Download");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                downloadbutton.setText("Download");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(email.getText().toString()+"/" + "test.pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");

                        Upload upload = new Upload("test.pdf", taskSnapshot.getStorage().getDownloadUrl().toString());
                        postData();
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }
    private void download() {

        storageReference=firebaseStorage.getInstance().getReference();
        ref=storageReference.child(email.getText().toString()+"/"+"final.txt");
        //StorageReference storageReference = storage.getReference();
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFiles(MainActivity.this,"final",".txt",DOWNLOAD_DIR,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        // StorageReference  islandRef = storage.ch ("/Summary.pdf");
        //  StorageReference downloadRef = storageRef.child(userPath+fileName);
        //    final File fileNameOnDevice = new File(DOWNLOAD_DIR+"/"+fileName);


    }
    public  void downloadFiles(Context context,String fileName,String FileExtension,String Destinationdir,String url){
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,Destinationdir,fileName+FileExtension);
        downloadManager.enqueue(request);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.inanutshell.abhijithneilabraham.firebaseandroid.R.id.buttonUploadFile:
                getPDF();
                break;


        }
    }
}