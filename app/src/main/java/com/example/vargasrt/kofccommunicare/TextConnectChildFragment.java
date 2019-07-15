package com.example.vargasrt.kofccommunicare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.adapter.MessagesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmMessageboxAdapter;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.model.Profile;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.Manifest.permission.SEND_SMS;
import static android.content.Context.MODE_PRIVATE;


public class TextConnectChildFragment extends Fragment {

    private static final String ARGUMENT_POSITION = "argument_position";
    private static final int REQUEST_SMS = 0;
    private String theNumber = "09178255632";
    private String myMsg;
    private MessagesAdapter adapter;
    private Realm realm;
    private RecyclerView recycler;
    private BottomNavigationView navigation;
//    final Context context;
    private Pattern pattern;
    private Matcher matcher;
// ^((0[13578]|1[02])[\/.](0[1-9]|[12][0-9]|3[01])[\/.](18|19|20)[0-9]{2})|((0[469]|11)[\/.](0[1-9]|[12][0-9]|30)[\/.](18|19|20)[0-9]{2})|((02)[\/.](0[1-9]|1[0-9]|2[0-8])[\/.](18|19|20)[0-9]{2})|((02)[\/.]29[\/.](((18|19|20)(04|08|[2468][048]|[13579][26]))1900|2000))
    private static final String regexMMDDYYYY="^((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01])(18|19|20)[0-9]{2})|((0[469]|11)(0[1-9]|[12][0-9]|30)(18|19|20)[0-9]{2})|((02)(0[1-9]|1[0-9]|2[0-8])(18|19|20)[0-9]{2})|((02)29(((18|19|20)(04|08|[2468][048]|[13579][26]))1900|2000))";
    private static final String regexMMYYYY="^(0[1-9]|10|11|12)20[0-9]{2}$";
    private LayoutInflater inflater;
    private static TextConnectChildFragment instance = null;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedButton;
    private Integer limitremaining;
    private ArrayList<Messagebox> messageboxes;
    private String strAcctNo;
    private String strBirthDate;
    private String strDept;
    private String strDeptCode;
    IntentFilter intentFilter;
    private BroadcastReceiver sentStatusReceiver, deliveredStatusReceiver;

    public static TextConnectChildFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, position);
        TextConnectChildFragment fragment = new TextConnectChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
/*
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_connect_child, container, false);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_messagesbox, container, false);
    }
*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mybcinq, container, false);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instance = this;
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_bcinq);

        //get realm instance
        this.realm = RealmControllerMessagebox.with(this).getRealm();
        setupRecycler();

        //intentFilter = new IntentFilter();
        //intentFilter.addAction("SMS_RECEIVED_ACTION");

        /*
        if (!Prefs.with(getContext()).getPreLoad()) {
            setRealmData();
        }
        */
        //SharedPreferences.Editor editor = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        //editor.putLong("TEXTCONNECT_COUNTER", 1958);

        // refresh the realm instance
        //RealmControllerMessagebox.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        //setRealmAdapter(RealmController.with(this).getBooks());
        //setRealmAdapter(RealmController.with(this).getProfiles());
        //setRealmAdapter(RealmControllerMessagebox.with(this).getMessageboxes());

        //TextView tvTextConnect = view.findViewById(R.id.tv_textconnect);

        final int position = getArguments().getInt(ARGUMENT_POSITION, -1);
        //tvTextConnect.setText(position == 0 ? R.string.do_not_stop_believing : R.string.life_is_a_very_funny_proposition_after_all);
        switch (position){
            case 0:
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedBCINQMessageboxes());
                //recycler.scrollToPosition(RealmControllerMessagebox.with(this).queryedBCINQMessageboxesCount());
                RealmControllerMessagebox.with(this).refresh();
                break;
            case 1:
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedALLWMessageboxes());
                //recycler.scrollToPosition(RealmControllerMessagebox.with(this).queryedALLWMessageboxesCount());
                RealmControllerMessagebox.with(this).refresh();
                break;
            case 2:
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedDEPTMessageboxes());
                //recycler.scrollToPosition(RealmControllerMessagebox.with(this).queryedDEPTMessageboxesCount());
                RealmControllerMessagebox.with(this).refresh();
                break;
        }

    }

    public static TextConnectChildFragment getInstance() {
        return instance;
    }


    public void setRealmAdapter(RealmResults<Messagebox> messageboxes) {
        //RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        RealmMessageboxAdapter realmAdapter = new RealmMessageboxAdapter(getContext().getApplicationContext(), messageboxes, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recycler.setLayoutManager(layoutManager);
        // create an empty adapter and add it to the recycler view
        //adapter = new ProfilesAdapter(this);
        //adapter = new MessagesAdapter(getContext());
        adapter = new MessagesAdapter(getContext());
        recycler.setAdapter(adapter);
    }

    public void refreshBCInquiry(String msgReceive) {
//        RealmControllerMessagebox.with(this).refresh();
//        recycler.getAdapter().notifyDataSetChanged();
//        Toast.makeText(getContext(), "refreshBCInquiry "+msgReceive , Toast.LENGTH_LONG).show();
//        recycler.invalidate();
//        recycler.clearFocus();

/*
        String imageSenderKCUrl = getURLForResource(R.mipmap.ic_launcher_kcshield);
        String msgcode = msgReceive.length() > 2 ? msgReceive.substring(msgReceive.trim().length() - 2) : null;
        msgcode = msgcode.trim();
        if (msgcode.equals("1V")) {
            strDeptCode = "VERSE";
        } else if (msgcode.equals("1E")) {
            strDeptCode = "NEWS";
        } else if (msgcode.equals("1N")) {
            strDeptCode = "NOTICE";
        } else if (msgcode.equals("1B")) {
            strDeptCode = "BCINFO";
        } else if (msgcode.equals("2A")) {
            strDeptCode = "ALLW";
        } else if (msgcode.equals("2B")) {
            strDeptCode = "BCINQ";
        } else if (msgcode.equals("3A")) {
            strDeptCode = "ACT";
        } else if (msgcode.equals("3B")) {
            strDeptCode = "ADM";
        } else if (msgcode.equals("3C")) {
            strDeptCode = "ADT";
        } else if (msgcode.equals("3D")) {
            strDeptCode = "SERVICE";
        } else if (msgcode.equals("3E")) {
            strDeptCode = "FBG";
        } else if (msgcode.equals("3F")) {
            strDeptCode = "FMAS";
        } else if (msgcode.equals("3G")) {
            strDeptCode = "FGJWF";
        } else if (msgcode.equals("3H")) {
            strDeptCode = "HRCC";
        } else if (msgcode.equals("3I")) {
            strDeptCode = "LEGAL";
        } else if (msgcode.equals("3J")) {
            strDeptCode = "MIS";
        } else if (msgcode.equals("3K")) {
            strDeptCode = "TSD";
        } else if (msgcode.equals("3L")) {
            strDeptCode = "UND";
        } else {
            strDeptCode = "NOTICE";
        }

        messageboxes = new ArrayList<>();
        Messagebox messagebox = new Messagebox();
        messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
        messagebox.setImageSenderUrl(imageSenderKCUrl);
        messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
        messagebox.setMsgbound("INBOX");
        messagebox.setMsgparam1(strDeptCode);
        messagebox.setMsgparam3(msgReceive);
        messageboxes.add(messagebox);

        for (Messagebox m : messageboxes) {
//            int insertIndex = (int) (Prefs.with(getContext()).getTexconnectCounter() + 1);
//            int insertIndex = (int) RealmControllerMessagebox.with(this).queryedBCINQMessageboxesCount();
//            int insertIndex = (int) realm.where(Messagebox.class).contains("msgparam1","BCINQ").count() + 1;
            Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(m);
            realm.commitTransaction();
            adapter.notifyDataSetChanged();
        }
*/
    }

    public void BCInquiry(){
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.sendbcinquiry, null);

        final EditText editAcctNmbr = (EditText) content.findViewById(R.id.acctnmbr);
        final EditText editBirthDate = (EditText) content.findViewById(R.id.birthdate);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content)
                .setTitle("BC Inquiry")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();
                        //RealmQuery<Messagebox> query = realm.where(Messagebox.class).contains("msgparam1","BCINQ");
//                        int insertIndex = (int) RealmControllerMessagebox.with(this).queryedBCINQMessageboxesCount();
//                        Toast.makeText(getContext(), "Record: "+ insertIndex, Toast.LENGTH_SHORT).show();

                        //ArrayList<Messagebox> messageboxes = new ArrayList<>();
                        messageboxes = new ArrayList<>();
                        strAcctNo = editAcctNmbr.getText().toString();
                        strBirthDate = editBirthDate.getText().toString();
                        String imageSenderMEUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

//                        if (!editAcctNmbr.getText().toString().isEmpty() && !editBirthDate.getText().toString().isEmpty()){
//                            Toast.makeText(getContext(), "Account Number and Birthdate are required!"+ Prefs.with(getContext()).getTexconnectCounter(), Toast.LENGTH_SHORT).show();
                            Messagebox messagebox = new Messagebox();
//                            messagebox.setMsgid((results.size() + 1) + System.currentTimeMillis());
                            messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
                            messagebox.setImageSenderUrl(imageSenderMEUrl);
                            messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                            messagebox.setMsgbound("OUTBOX");
                            messagebox.setMsgparam1("BCINQ");
                            messagebox.setMsgparam2(strAcctNo);
                            messagebox.setMsgparam3(strBirthDate);
                            messageboxes.add(messagebox);
                            myMsg = "BCINQ "+strAcctNo+" "+strBirthDate;
//                        } else {
                            //Toast.makeText(getContext(), "Account Number and Birthdate are required!"+ Prefs.with(getContext()).getTexconnectCounter(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), "Account Number and Birthdate are required!"+ results.size(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(navigation, "Account Number and Birthdate are required!", Snackbar.LENGTH_SHORT).show();
//                        }

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(bc.this, Manifest.permission.READ_CONTACTS)
//                            != PackageManager.PERMISSION_GRANTED) {
                            if (!checkPermission()) {
                                if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                                    showMessageOKCancel("You need to allow access to Send SMS",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                                                REQUEST_SMS);
                                                    }
                                                }
                                            });
                                    return;
                                }
                                requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                        REQUEST_SMS);
                                return;
                            }
                        }


                        for (Messagebox m : messageboxes) {
                            sendMsg(theNumber, myMsg);
                            Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                            // Persist your data easily
                            realm.beginTransaction();
                            realm.copyToRealm(m);
                            realm.commitTransaction();
                            adapter.notifyDataSetChanged();
//                            int insertIndex = (int) realm.where(Messagebox.class).contains("msgparam1","BCINQ").count();
//                            ScrollToBottom(insertIndex);

//                            adapter.notifyItemInserted(insertIndex - 1);
//                            recycler.scrollToPosition(insertIndex);
//                            recycler.
//                            adapter.notifyItemInserted(insertIndex);
//                            adapter.notifyDataSetChanged();
                        }
                        //recycler.getAdapter().getItemCount() ;
                        //int icount = (int) query.count();
                        //recycler.requestFocus();
                        //recycler.scrollToPosition(icount);
                        //
                        // recycler.smoothScrollToPosition(icount);
                        //Toast.makeText(getContext(), "Count "+ getContext(), Toast.LENGTH_SHORT).show();
                        //LatestRecord(icount);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);

        editAcctNmbr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editAcctNmbr.getText().toString().isEmpty()){
                    if (editAcctNmbr.getText().toString().length() > 7){
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                        if (editAcctNmbr.getText().toString().length() > 7) {
                            Toast.makeText(getContext(), "Invalid Account/BC Number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (editBirthDate.getText().toString().length() > 7) {
                            matcher = Pattern.compile(regexMMDDYYYY).matcher(editBirthDate.getText().toString());
                            if (matcher.matches()) {
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(true);
                            } else {
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);
                                Toast.makeText(getContext(), "Not a valid Birth Date", Toast.LENGTH_SHORT).show();
                                //editBirthDate.requestFocus();
                            }
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                        }
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });

        editBirthDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editBirthDate.getText().toString().isEmpty()){
                    if (editBirthDate.getText().toString().length() > 7) {
                        matcher = Pattern.compile(regexMMDDYYYY).matcher(editBirthDate.getText().toString());
                        if (matcher.matches()) {
                            if (!editAcctNmbr.getText().toString().isEmpty()){
                                if (editAcctNmbr.getText().toString().length() > 7) {
                                    Toast.makeText(getContext(), "Invalid Account/BC Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setEnabled(true);
                                }
                            } else {
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);
                            }
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                            Toast.makeText(getContext(), "Not a valid Birth Date, Ex: 04291970", Toast.LENGTH_SHORT).show();
                            //editBirthDate.requestFocus();
                        }
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });
    }

    public void ALLWInquiry() {
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.sendallwinquiry, null);

        RadioGroup radioGroup = (RadioGroup) content.findViewById(R.id.radioAllwSrp);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) content.findViewById(selectedId);
        selectedButton = (String) radioButton.getText();

        final EditText editFCCode = (EditText) content.findViewById(R.id.fccode);
        final EditText editPincode = (EditText) content.findViewById(R.id.pincode);
        final EditText editSRPDate = (EditText) content.findViewById(R.id.srpdate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioAllw:
                        selectedButton = "Allowance";
                        editPincode.setVisibility(View.VISIBLE);
                        editSRPDate.setVisibility(View.GONE);
                        editPincode.setText(null);
                        editSRPDate.setText(null);
                        break;
                    case R.id.radioSRP:
                        selectedButton = "SRP";
                        editPincode.setVisibility(View.GONE);
                        editSRPDate.setVisibility(View.VISIBLE);
                        editPincode.setText(null);
                        editSRPDate.setText(null);
                        break;
                }
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content)
                .setTitle("Allowance or Submitted, Released and Paid BCs Inquiry")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();
                        RealmQuery<Messagebox> query = realm.where(Messagebox.class).contains("msgparam1","ALLW").or().contains("msgparam1", "SRP");
                        ArrayList<Messagebox> messageboxes = new ArrayList<>();
                        String imageSenderMEUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

                        if (!editFCCode.getText().toString().isEmpty() && (!editPincode.getText().toString().isEmpty() || !editSRPDate.getText().toString().isEmpty())){
                            Messagebox messagebox = new Messagebox();
                            messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
                            messagebox.setImageSenderUrl(imageSenderMEUrl);
                            messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                            messagebox.setMsgbound("OUTBOX");
                            if (selectedButton.equals("Allowance")){
                                messagebox.setMsgparam1("ALLW");
                                messagebox.setMsgparam2(editFCCode.getText().toString());
                                messagebox.setMsgparam3(editPincode.getText().toString());
                                myMsg = "ALLW "+editFCCode.getText().toString().trim()+" "+editPincode.getText().toString().trim();
                            } else {
                                messagebox.setMsgparam1("SRP");
                                messagebox.setMsgparam2(editFCCode.getText().toString());
                                messagebox.setMsgparam3(editSRPDate.getText().toString());
                                myMsg = "SRP "+editFCCode.getText().toString().trim()+" "+editSRPDate.getText().toString().trim();
                            }
                            messageboxes.add(messagebox);
                        } else {
                        }
                        for (Messagebox m : messageboxes) {
                            Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                            // Persist your data easily
                            sendMsg(theNumber, myMsg);
                            realm.beginTransaction();
                            realm.copyToRealm(m);
                            realm.commitTransaction();
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);

        editFCCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editFCCode.getText().toString().isEmpty()){
                    if (editFCCode.getText().toString().length() == 5){
                        if (!editPincode.getText().toString().isEmpty()){
                            if (selectedButton.equals("Allowance")){
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(true);
                            } else {
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);
                            }
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                        }
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                        if (editFCCode.getText().toString().length() > 5){
                            Toast.makeText(getContext(), "Invalid FC Code", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });

        editPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editPincode.getText().toString().isEmpty()){
                    if (editFCCode.getText().toString().length() == 5){
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(true);
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                        if (editFCCode.getText().toString().length() > 5){
                            Toast.makeText(getContext(), "Invalid FC Code", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });

        editSRPDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editSRPDate.getText().toString().isEmpty()){
                    if (editSRPDate.getText().toString().length()==6){
                        matcher = Pattern.compile(regexMMYYYY).matcher(editSRPDate.getText().toString());
                        if (matcher.matches()) {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);
                        } else{
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                            Toast.makeText(getContext(), "Invalid Date parameter", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                        if (editSRPDate.getText().toString().length() > 6){
                            Toast.makeText(getContext(), "Invalid Date parameter", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });
    }

    public void DeptInquiry(){
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.senddeptinquiry, null);

        RadioGroup radioGroup = (RadioGroup) content.findViewById(R.id.radioDepartment);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) content.findViewById(selectedId);
        selectedButton = (String) radioButton.getText();

        final TextView charlimit = (TextView) content.findViewById(R.id.charlimit);
        final EditText editSenderName = (EditText) content.findViewById(R.id.sendername);
        final EditText editSenderMsg = (EditText) content.findViewById(R.id.sendermsg);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioUDW:
                        selectedButton = "Underwriting";
                        break;
                    case R.id.radioFBG:
                        selectedButton = "FBG";
                        break;
                    case R.id.radioFC:
                        selectedButton = "FC's Accounts";
                        break;
                    case R.id.radioBRO:
                        selectedButton = "BC Relations Office";
                        break;
                    case R.id.radioFGJWF:
                        selectedButton = "Foundations";
                        break;
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content)
                .setTitle("Send text to a particular Department")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();
                        RealmQuery<Messagebox> query = realm.where(Messagebox.class).contains("msgparam1", "UND").or().contains("msgparam1", "FBG").or().contains("msgparam1", "FMAS").or().contains("msgparam1", "SERVICE").or().contains("msgparam1", "FGJWF");
                        ArrayList<Messagebox> messageboxes = new ArrayList<>();
                        String imageSenderMEUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

                        if (!editSenderMsg.getText().toString().isEmpty() && !editSenderName.getText().toString().isEmpty()){
                            Messagebox messagebox = new Messagebox();
                            messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
                            messagebox.setImageSenderUrl(imageSenderMEUrl);
                            messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                            messagebox.setMsgbound("OUTBOX");
                            switch (selectedButton) {
                                case "Underwriting":
                                    messagebox.setMsgparam1("UND");
                                    strDept = "UND";
                                    break;
                                case "FBG":
                                    messagebox.setMsgparam1("FBG");
                                    strDept = "FBG";
                                    break;
                                case "FC's Accounts":
                                    messagebox.setMsgparam1("FMAS");
                                    strDept = "FMAS";
                                    break;
                                case "BC Relations Office":
                                    messagebox.setMsgparam1("SERVICE");
                                    strDept = "SERVICE";
                                    break;
                                case "Foundations":
                                    messagebox.setMsgparam1("FGJWF");
                                    strDept = "FGJWF";
                                    break;
                            }
                            messagebox.setMsgparam2(editSenderName.getText().toString());
                            messagebox.setMsgparam3(editSenderMsg.getText().toString());
                            messageboxes.add(messagebox);
                            myMsg = strDept+" "+editSenderName.getText().toString().replaceAll(" ","_")+" "+editSenderMsg.getText().toString();
                        } else {
                        }
                        for (Messagebox m : messageboxes) {
                           sendMsg(theNumber, myMsg);
                            Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                            // Persist your data easily
                            realm.beginTransaction();
                            realm.copyToRealm(m);
                            realm.commitTransaction();
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);

        editSenderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                limitremaining = ((150 - editSenderName.getText().toString().length()) - editSenderMsg.getText().toString().length());
                if (!editSenderName.getText().toString().isEmpty()){
                    charlimit.setText("Character limit remaining ("+ limitremaining +")");
                    if (!editSenderMsg.getText().toString().isEmpty()) {
                        if (limitremaining < 0){
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                            Toast.makeText(getContext(), "Message size limit reached!", Toast.LENGTH_LONG).show();
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);
                        }
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });

        editSenderMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                limitremaining = ((150 - editSenderName.getText().toString().length()) - editSenderMsg.getText().toString().length());
                if (!editSenderMsg.getText().toString().isEmpty()){
                    charlimit.setText("Character limit remaining ("+ limitremaining +")");
                    if (!editSenderName.getText().toString().isEmpty()){
                        if (limitremaining < 0){
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);
                            Toast.makeText(getContext(), "Message size limit reached!", Toast.LENGTH_LONG).show();
                        } else {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);
                        }
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                .setEnabled(false);
                    }
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);
                }
            }
        });
    }

    public void sendMsg (String theNumber, final String myMsg) {
        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";
        final String thisMsg = myMsg;

        PendingIntent sentPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(DELIVERED), 0);

        getContext().getApplicationContext().registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Unknown Error";
                Boolean resultok = false;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Sent Successfully !!";
                        resultok = true;
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error : No Service Available";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error : Null PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error : Radio is off";
                        break;
                    default:
                        break;
                }

                //Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                if (!resultok){
                    String imageSenderMEUrl = getURLForResource(R.drawable.ic_profile_face_48dp);
                    Messagebox messagebox = new Messagebox();
                    messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
                    messagebox.setImageSenderUrl(imageSenderMEUrl);
                    messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                    messagebox.setMsgbound("INBOX");
                    messagebox.setMsgparam1("NOTICE");
                    messagebox.setMsgparam3("Unable to send message\n \""+thisMsg+"\"\n\n"+s);
                    Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                    // Persist your data easily
                    realm.beginTransaction();
                    realm.copyToRealm(messagebox);
                    realm.commitTransaction();
                    adapter.notifyDataSetChanged();
                }
            }
        }, new IntentFilter(SENT));
        getContext().getApplicationContext().registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Message Not Delivered";
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Delivered Successfully";
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                String imageSenderMEUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

                Messagebox messagebox = new Messagebox();
                messagebox.setMsgid(Prefs.with(getContext()).getTexconnectCounter() + 1);
                messagebox.setImageSenderUrl(imageSenderMEUrl);
                messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
                messagebox.setMsgbound("INBOX");
                messagebox.setMsgparam1("NOTICE");
                messagebox.setMsgparam3(s);
                Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                // Persist your data easily
                realm.beginTransaction();
                realm.copyToRealm(messagebox);
                realm.commitTransaction();
                adapter.notifyDataSetChanged();
            }
        }, new IntentFilter(DELIVERED));
        //getContext().getApplicationContext().registerReceiver(sentStatusReceiver, new IntentFilter("SENT"));
        //getContext().getApplicationContext().registerReceiver(deliveredStatusReceiver, new IntentFilter("DELIVERED"));

        Toast.makeText(getContext(), "Sending Message ...", Toast.LENGTH_LONG).show();
        SmsManager sms = SmsManager.getDefault();
        //display the message
        sms.sendTextMessage(theNumber, null, myMsg, sentPI, deliveredPI);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
/*
        TextView inTxt = (TextView) findViewById(R.id.textView4);

        String myMonth = String.format("%02d",Integer.parseInt(String.valueOf(birth_mo)));
        String myDay = String.format("%02d",Integer.parseInt(String.valueOf(birth_dy)));
        String myAcctNo = tvAcctNo.getText().toString();
        String myBirthDate = tvBirthDate.getText().toString();
        String myMsg = "BCINQ "+edittvAcctNo.getText().toString()+" "+myMonth+myDay+birth_yr;
        String theNumber = "09178255632";
*/

        switch (requestCode) {
            case REQUEST_SMS:
                if (grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permission Granted, Now you can access sms", Toast.LENGTH_SHORT).show();
//                    sendMySMS();
                    if (!myMsg.isEmpty()) {
                        for (Messagebox m : messageboxes) {
                            Prefs.with(getContext()).setTextconnectCounter((Prefs.with(getContext()).getTexconnectCounter() + 1));
                            // Persist your data easily
                            realm.beginTransaction();
                            realm.copyToRealm(m);
                            realm.commitTransaction();
                            sendMsg(theNumber, myMsg);
                        }
                    }

                }else {
                    Toast.makeText(getContext(), "Permission Denied, You cannot access and sms", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(SEND_SMS)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
/*
        new android.support.v7.app.AlertDialog.Builder(bc.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
*/
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
//        return ( ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS ) == PackageManager.PERMISSION_GRANTED);
        return ( ContextCompat.checkSelfPermission(getContext(), SEND_SMS ) == PackageManager.PERMISSION_GRANTED);
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}