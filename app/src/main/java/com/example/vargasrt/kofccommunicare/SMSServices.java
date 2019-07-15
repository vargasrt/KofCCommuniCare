package com.example.vargasrt.kofccommunicare;

import android.app.FragmentManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.adapter.MessagesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.ProfilesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmMessageboxAdapter;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmController;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerProfiles;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SMSServices extends Service {

    private Realm realm;
    private Realm regrealm;
    private ArrayList<Messagebox> messageboxes;
    private MessagesAdapter adapter;
    private String msgcode;
    private String msgregistration;
    private String strDeptCode;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String msgreceive = intent.getStringExtra("MSGRECEIVE");
//        msgcode = msgreceive.length() > 9 ? msgreceive.substring(msgreceive.trim().length() - 9) : null;
        msgcode = msgreceive.length() > 2 ? msgreceive.substring(msgreceive.trim().length() - 2) : null;
//        Toast.makeText(getApplicationContext(),msgreceive+" nadali ang pancit",Toast.LENGTH_LONG).show();
        //get realm instance
        msgregistration = msgreceive.length() > 25 ? msgreceive.substring(0,26) : null;

        if (!msgregistration.equals("null")) {
            msgregistration = msgregistration.trim();
            if (msgregistration.equals("You are already registered")) {
                ProfilesAdapter.getInstance().UpdateRegistered();
            }
        }

        this.realm = RealmControllerMessagebox.with(this).getRealm();
        realm.setAutoRefresh(true);
        //this.regrealm = RealmControllerProfiles.with(this).getRealm();
        adapter = new MessagesAdapter(getApplicationContext());

        msgcode = msgcode.trim();
        //Toast.makeText(getApplicationContext(), "Message Code: "+ msgcode +" "+msgcode.length(), Toast.LENGTH_LONG).show();

        //RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();
        //RealmQuery<Messagebox> query = realm.where(Messagebox.class).contains("msgparam1","BCINQ");

        String imageSenderKCUrl = getURLForResource(R.mipmap.ic_launcher_kcshield);

        if (msgcode.equals("1V")){
            strDeptCode = "VERSE";
        } else if (msgcode.equals("1E")){
            strDeptCode = "NEWS";
        } else if (msgcode.equals("1N")){
            strDeptCode = "NOTICE";
        } else if (msgcode.equals("1B")){
            strDeptCode = "BCINFO";
        } else if (msgcode.equals("2A")){
            strDeptCode = "ALLW";
        } else if (msgcode.equals("2B")){
            strDeptCode = "BCINQ";
        } else if (msgcode.equals("3A")){
            strDeptCode = "ACT";
        } else if (msgcode.equals("3B")){
            strDeptCode = "ADM";
        } else if (msgcode.equals("3C")){
            strDeptCode = "ADT";
        } else if (msgcode.equals("3D")){
            strDeptCode = "SERVICE";
        } else if (msgcode.equals("3E")){
            strDeptCode = "FBG";
        } else if (msgcode.equals("3F")){
            strDeptCode = "FMAS";
        } else if (msgcode.equals("3G")){
            strDeptCode = "FGJWF";
        } else if (msgcode.equals("3H")){
            strDeptCode = "HRCC";
        } else if (msgcode.equals("3I")){
            strDeptCode = "LEGAL";
        } else if (msgcode.equals("3J")){
            strDeptCode = "MIS";
        } else if (msgcode.equals("3K")){
            strDeptCode = "TSD";
        } else if (msgcode.equals("3L")){
            strDeptCode = "UND";
        } else {
            strDeptCode = "NOTICE";
        }

        messageboxes = new ArrayList<>();
        Messagebox messagebox = new Messagebox();
        messagebox.setMsgid(Prefs.with(getApplicationContext()).getTexconnectCounter() + 1);
        messagebox.setImageSenderUrl(imageSenderKCUrl);
        messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
        messagebox.setMsgbound("INBOX");
        messagebox.setMsgparam1(strDeptCode);
        messagebox.setMsgparam3(msgreceive);
        messageboxes.add(messagebox);

        for (Messagebox m : messageboxes) {
//            int insertIndex = (int) (Prefs.with(getContext()).getTexconnectCounter() + 1);
//            int insertIndex = (int) RealmControllerMessagebox.with(this).queryedBCINQMessageboxesCount();
//            int insertIndex = (int) realm.where(Messagebox.class).contains("msgparam1","BCINQ").count() + 1;
            Prefs.with(getApplicationContext()).setTextconnectCounter((Prefs.with(getApplicationContext()).getTexconnectCounter() + 1));
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(m);
            realm.commitTransaction();
            adapter.notifyDataSetChanged();
            //setRealmAdapter(RealmControllerMessagebox.with(this).queryedBCINQMessageboxes());
            //adapter.notifyItemInserted(insertIndex);
            switch (strDeptCode){
                case "VERSE":
                    break;
                case "BCINQ":
                    //setRealmAdapter(RealmControllerMessagebox.with(this).queryedBCINQMessageboxes());
                    //RealmControllerMessagebox.with(this).refresh();
                    //RealmControllerMessagebox.getInstance().queryedBCINQMessageboxes();
//                    TextConnectChildFragment.getInstance().refreshBCInquiry(msgreceive);
//                    Toast.makeText(getApplicationContext(), "Dept Code: "+ strDeptCode , Toast.LENGTH_LONG).show();
                    break;
                case "MIS":
                    //setRealmAdapter(RealmControllerMessagebox.with(this).queryedBCINQMessageboxes());
                    //RealmControllerMessagebox.with(this).refresh();
                    //RealmControllerMessagebox.getInstance().queryedBCINQMessageboxes();
                    //TextConnectChildFragment.getInstance().refreshBCInquiry();
//                    Toast.makeText(getApplicationContext(), "Dept Code: "+ strDeptCode , Toast.LENGTH_LONG).show();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
