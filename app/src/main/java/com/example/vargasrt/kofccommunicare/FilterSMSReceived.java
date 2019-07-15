package com.example.vargasrt.kofccommunicare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;

public class FilterSMSReceived extends Activity {

    private Realm realm;
    private ArrayList<Messagebox> messageboxes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String msgreceive = intent.getStringExtra("MSGRECEIVE");
//        Toast.makeText(getApplicationContext(),msgreceive,Toast.LENGTH_LONG).show();

        //get realm instance
//        this.realm = RealmControllerMessagebox.with(this).getRealm();

        String imageSenderKCUrl = getURLForResource(R.mipmap.ic_launcher_kcshield);

        //RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();
        //RealmQuery<Messagebox> query = realm.where(Messagebox.class).contains("msgparam1","BCINQ");
        //ArrayList<Messagebox> messageboxes = new ArrayList<>();
        messageboxes = new ArrayList<>();

        Messagebox messagebox = new Messagebox();
//                            messagebox.setMsgid((results.size() + 1) + System.currentTimeMillis());
        messagebox.setMsgid(Prefs.with(getApplicationContext()).getTexconnectCounter() + 1);
        messagebox.setImageSenderUrl(imageSenderKCUrl);
        messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
        messagebox.setMsgbound("INBOX");
        messagebox.setMsgparam1("BCINQ");
        messagebox.setMsgparam3(msgreceive);
        messageboxes.add(messagebox);

        for (Messagebox m : messageboxes) {
//            int insertIndex = (int) (Prefs.with(getContext()).getTexconnectCounter() + 1);
//            int insertIndex = (int) RealmControllerMessagebox.with(this).queryedBCINQMessageboxesCount();
            Prefs.with(getApplicationContext()).setTextconnectCounter((Prefs.with(getApplicationContext()).getTexconnectCounter() + 1));
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(m);
            realm.commitTransaction();
        }
        this.finish();
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
