package com.example.abhijithneilabraham.firebaseandroid;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class ViewUploadsActivity extends AppCompatActivity {
    //the listview
    ListView listView;
    Context context;
   // String filename = "myfile";
    String userPath="gs://fir-android-c7a0d.appspot.com";
    String fileName="Summary.pdf";
    String DOWNLOAD_DIR="Download/";
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    //database reference to get uploads data


    //list to store uploads data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);
       Button button=findViewById(R.id.download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }
    private void download() {

        storageReference=firebaseStorage.getInstance().getReference();
        ref=storageReference.child("Summary.pdf");
       //StorageReference storageReference = storage.getReference();
       ref.child("Summary.pdf");
       ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               String url=uri.toString();
               downloadFiles(ViewUploadsActivity.this,"Summary",".pdf",DOWNLOAD_DIR,url);
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

}