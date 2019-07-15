package com.example.vargasrt.kofccommunicare.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vargasrt.kofccommunicare.MyProfilesFragment;
import com.example.vargasrt.kofccommunicare.Prefs;
import com.example.vargasrt.kofccommunicare.R;
import com.example.vargasrt.kofccommunicare.TextConnectChildFragment;
import com.example.vargasrt.kofccommunicare.model.Profile;
import com.example.vargasrt.kofccommunicare.realm.RealmController;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProfilesAdapter extends RealmRecyclerViewAdapter<Profile> {

    private String theNumber = "09178255632";
    private String myMsg;

    private static ProfilesAdapter instance = null;

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public ProfilesAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profiles, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        instance = this;

        // get the article
        final Profile profile = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        /*
        holder.textTitle.setText(book.getTitle());
        holder.textAuthor.setText(book.getAuthor());
        holder.textDescription.setText(book.getDescription());
        */
        holder.textLastname.setText(profile.getLastname());
        holder.textMiddlename.setText(profile.getMiddlename());
        holder.textFirstname.setText(profile.getFirstname());
        holder.textFullname.setText(profile.getLastname()+", "+profile.getFirstname()+" "+profile.getMiddlename());
        holder.textFccode.setText(profile.getFccode());
        holder.textPincode.setText(profile.getPincode());
        holder.switchRegistered.setChecked(profile.getRegistered());

        // load the background image
        if (profile.getImageUrl() != null) {
            Glide.with(context)
                    .load(profile.getImageUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(holder.imageBackground);
        }

        holder.switchRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.switchRegistered.isChecked()){
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View content = inflater.inflate(R.layout.register_profile, null);
                    final EditText editFccode = (EditText) content.findViewById(R.id.fccode);
                    final EditText editPincode = (EditText) content.findViewById(R.id.pincode);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(content)
                            .setTitle("Register Profile")
                            .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myMsg = "KCREG "+editFccode.getText().toString().trim()+" "+editPincode.getText().toString().trim()+" "+editPincode.getText().toString().trim();
                                    TextConnectChildFragment.getInstance().sendMsg(theNumber,myMsg);

                                    RealmResults<Profile> results = realm.where(Profile.class).findAll();

                                    realm.beginTransaction();
                                    results.get(position).setFccode(editFccode.getText().toString());
                                    results.get(position).setPincode(editPincode.getText().toString());
                                    realm.commitTransaction();

                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Your registration has been sent, Please wait for reply...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                            .setEnabled(false);

                    editFccode.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (!editFccode.getText().toString().isEmpty()){
                                if (editFccode.getText().toString().length() == 5){
                                    if (!editPincode.getText().toString().isEmpty()){
                                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                                    .setEnabled(true);
                                    } else {
                                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                                .setEnabled(false);
                                    }
                                } else {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setEnabled(false);
                                    if (editFccode.getText().toString().length() > 5){
                                        Toast.makeText(context, "Invalid FC Code", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(context, "FC Code required", Toast.LENGTH_SHORT).show();
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
                                if (editFccode.getText().toString().length() == 5){
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setEnabled(true);
                                } else {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setEnabled(false);
                                    if (editFccode.getText().toString().length() > 5){
                                        Toast.makeText(context, "Invalid FC Code", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(context, "PIN Code required", Toast.LENGTH_SHORT).show();
                                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);
                            }
                        }
                    });
                } else {
                    //Toast.makeText(context,"Already Registered",Toast.LENGTH_LONG).show();
                    //holder.switchRegistered.setChecked(true);
                }
            }
        });

        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<Profile> results = realm.where(Profile.class).findAll();

                // Get the book title to show it in toast message
                Profile p = results.get(position);
                String title = p.getLastname();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                // remove single match
                results.remove(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_profile, null);
                final EditText editLastname = (EditText) content.findViewById(R.id.lastname);
                final EditText editFirstname = (EditText) content.findViewById(R.id.firstname);
                final EditText editMiddlename = (EditText) content.findViewById(R.id.middlename);
                final EditText editThumbnail = (EditText) content.findViewById(R.id.thumbnail);
//                final EditText editFccode = (EditText) content.findViewById(R.id.fccode);
//                final EditText editPincode = (EditText) content.findViewById(R.id.pincode);

                editLastname.setText(profile.getLastname());
                editFirstname.setText(profile.getFirstname());
                editMiddlename.setText(profile.getMiddlename());
//                editFccode.setText(profile.getFccode());
//                editPincode.setText(profile.getPincode());
                editThumbnail.setText(profile.getImageUrl());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Profile")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Profile> results = realm.where(Profile.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setMiddlename(editMiddlename.getText().toString());
                                results.get(position).setFirstname(editFirstname.getText().toString());
                                results.get(position).setLastname(editLastname.getText().toString());
//                                results.get(position).setFccode(editFccode.getText().toString());
//                                results.get(position).setPincode(editPincode.getText().toString());
                                results.get(position).setImageUrl(editThumbnail.getText().toString());

                                realm.commitTransaction();
                                //Toast.makeText(context,"Position"+position,Toast.LENGTH_LONG).show();

                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public static ProfilesAdapter getInstance() {
        return instance;
    }

    public void UpdateRegistered(){

        RealmResults<Profile> results = realm.where(Profile.class).findAll();

        realm.beginTransaction();
        results.get(0).setRegistered(true);
        realm.commitTransaction();
        notifyDataSetChanged();
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        /*
        public TextView textTitle;
        public TextView textAuthor;
        public TextView textDescription;
        public ImageView imageBackground;
        */
        public TextView textFullname;
        public TextView textLastname;
        public TextView textMiddlename;
        public TextView textFirstname;
        public ImageView imageBackground;
        public TextView textFccode;
        public TextView textPincode;
        public Switch switchRegistered;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_profiles);
            textFullname = (TextView) itemView.findViewById(R.id.text_profile_fullname);
            textLastname = (TextView) itemView.findViewById(R.id.text_profile_lastname);
            textMiddlename = (TextView) itemView.findViewById(R.id.text_profile_middlename);
            textFirstname = (TextView) itemView.findViewById(R.id.text_profile_firstname);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
            textFccode = (TextView) itemView.findViewById(R.id.text_profile_fccode);
            textPincode = (TextView) itemView.findViewById(R.id.text_profile_pincode);
            switchRegistered = (Switch) itemView.findViewById(R.id.switchRegistered);
        }
    }
}
