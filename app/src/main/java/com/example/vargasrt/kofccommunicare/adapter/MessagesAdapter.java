package com.example.vargasrt.kofccommunicare.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.Prefs;
import com.example.vargasrt.kofccommunicare.R;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmController;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MessagesAdapter extends RealmRecyclerViewAdapter<Messagebox> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private int imageSender;
    private int padShort;
    private int padMedium;
    private int padLarge;
    private int padMsg;


    public MessagesAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagesbox, parent, false);

        padShort = (int) (parent.getWidth() * 0.3);
        padMedium = (int) (parent.getWidth() * 0.2);
        padLarge = (int) (parent.getWidth() * 0.1);
        // added for 70percent

/*
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.7);
        view.setLayoutParams(layoutParams);
*/
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        //realm = RealmController.getInstance().getRealm();
        realm = RealmControllerMessagebox.getInstance().getRealm();

        // get the article
        final Messagebox messagebox = getItem(position);

        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        Uri imgUriSender = Uri.parse(messagebox.getImageSenderUrl());
        //Uri imgUriSender = Uri.parse(getURLForResource(R.mipmap.ic_launcher_kcshield));

        holder.textMsgboxtimestamp.setText(messagebox.getMsgtimestamp());
        holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
        Integer msglength = messagebox.getMsgparam3().toString().length();
        if (msglength < 51) {
            padMsg = padShort;
        } else {
            if (msglength > 50 && msglength < 100) {
                padMsg = padMedium;
            } else {
                padMsg = padLarge;
            }
        }

        //FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.linearLayoutMsgboxchild.getLayoutParams();
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(holder.linearLayoutMsgboxparent.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.linearLayoutMsgboxparent.getLayoutParams();
        //params.gravity = Gravity.RIGHT;
        //holder.linearLayoutMsgboxparent.setLayoutParams(params);

        if (messagebox.getMsgbound().equals("OUTBOX")) {
            holder.imageMsgboxSender.setVisibility(View.GONE);
//            holder.imageMsgboxSenderME.setVisibility(View.VISIBLE);
//            holder.imageMsgboxSenderME.setImageURI(imgUriSender);
            holder.linearLayoutMsgboxparent.setPadding(padMsg,0,0,0);
            //holder.linearLayoutMsgboxparent.setHorizontalGravity(Gravity.END);

            //holder.linearLayoutMsgboxchild.setGravity(Gravity.END);
            //holder.linearLayoutMsgboxchild.setGravity(Gravity.END);

           // holder.linearLayoutMsgboxchild.setBackgroundColor(Color.parseColor("#c0f7c0"));
        } else {
//            holder.linearLayoutMsgboxparent.setBackgroundColor(Color.parseColor("#90EE90"));
//            holder.imageMsgboxSenderME.setVisibility(View.GONE);
            holder.imageMsgboxSender.setVisibility(View.VISIBLE);
            holder.imageMsgboxSender.setImageURI(imgUriSender);
            holder.linearLayoutMsgboxparent.setPadding(0,0,padMsg,0);
            //holder.linearLayoutMsgboxparent.setPadding(0,0,96,0);
            //holder.linearLayoutMsgboxchild.setGravity(Gravity.START);
            //holder.card.setPadding(0,0,300,0);
            //holder.linearLayoutMsgboxparent.setGravity(Gravity.START);
            //holder.linearLayoutMsgboxchild.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }


/*
        if (messagebox.getMsgbound().equals("OUTBOX")) {
            holder.linearLayoutMsgbox.setVisibility(View.GONE);
            holder.imageMsgboxSender.setVisibility(View.GONE);
            holder.imageMsgboxSender.setImageURI(null);
            holder.imageMsgboxSenderME.setVisibility(View.VISIBLE);
            holder.imageMsgboxSenderME.setImageURI(imgUriSender);
        } else {
            holder.linearLayoutMsgbox.setVisibility(View.VISIBLE);
        }
*/

        switch (messagebox.getMsgparam1()){
            case "VERSE":
                holder.textMsgboxfrom.setText(null);
                holder.textMsgboxfrom.setVisibility(View.GONE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                break;
            case "NEWS":
                holder.textMsgboxfrom.setText(null);
                holder.textMsgboxfrom.setVisibility(View.GONE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                break;
            case "NOTICE":
                holder.textMsgboxfrom.setText(null);
                holder.textMsgboxfrom.setVisibility(View.GONE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                break;
            case "BCINFO":
                holder.textMsgboxfrom.setText(null);
                holder.textMsgboxfrom.setVisibility(View.GONE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                break;
            case "MIS":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: Management Information Systems");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3().trim() + "\n\nFROM: MIS");
                }
                break;
            case "TSD":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: Treasury Service Department");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: TSD");
                }
                break;
            case "BCINQ":
                holder.textMsgboxfrom.setVisibility(View.GONE);
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam1() + " " + messagebox.getMsgparam2() + " " + messagebox.getMsgparam3());
                } else {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                }
                break;
            case "ALLW":
                holder.textMsgboxfrom.setVisibility(View.GONE);
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam1() + " " + messagebox.getMsgparam2() + " " + messagebox.getMsgparam3());
                } else {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                }
                break;
            case "SRP":
                holder.textMsgboxfrom.setVisibility(View.GONE);
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam1() + " " + messagebox.getMsgparam2() + " " + messagebox.getMsgparam3());
                } else {
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
                }
                break;
            case "UND":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: Underwriting Department");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\nFROM: Underwriting");
                }
                break;
            case "SERVICE":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: Business Relations Office");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\nFROM: BRO" );
                }
                break;
            case "FMAS":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: FMAS Department");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\nFROM: FMAS" );
                }
                break;
            case "FBG":
                if (messagebox.getMsgbound().equals("OUTBOX")) {
                    holder.textMsgboxfrom.setText("TO: FBG Department");
                    holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: " + messagebox.getMsgparam2());
                } else {
                    holder.textMsgboxfrom.setText(null);
                    holder.textMsgboxfrom.setVisibility(View.GONE);
                    holder.textMsgboxmessage.setText(messagebox.getMsgparam3() + "\n\nFROM: FBG" );
                }
                break;
            case "FGJWF":
                holder.textMsgboxfrom.setText("TO: KC Fr. George J. Willmann Charities, Inc.");
                holder.textMsgboxfrom.setVisibility(View.VISIBLE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3()+"\n\nFROM: "+messagebox.getMsgparam2());
                break;
            default:
                holder.textMsgboxfrom.setText(null);
                holder.textMsgboxfrom.setVisibility(View.GONE);
                holder.textMsgboxmessage.setText(messagebox.getMsgparam3());
        }


        /*
        if (messagebox.getImageSenderUrl() != null ) {
            Glide.with(context)
                    .load(messagebox.getImageSenderUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(holder.imageMsgboxSender);
        }
        */

        /*
        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();

                // Get the book title to show it in toast message
                Messagebox m = results.get(position);
                String title = m.getMsgparam1()+" "+m.getMsgparam2()+" "+m.getMsgparam3();

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
        */


        /*
        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_message, null);
                final EditText editMsgid = (EditText) content.findViewById(R.id.edt_msgbox_id);
                final EditText editMsgtimestamp = (EditText) content.findViewById(R.id.edt_msgbox_timestamp);
                final EditText editMsgbound = (EditText) content.findViewById(R.id.edt_msgbox_msgbound);
                final EditText editMsgparam1 = (EditText) content.findViewById(R.id.edt_msgbox_msgparam1);
                final EditText editMsgparam2 = (EditText) content.findViewById(R.id.edt_msgbox_msgparam2);
                final EditText editMsgparam3 = (EditText) content.findViewById(R.id.edt_msgbox_msgparam3);

                String msgid = String.valueOf(messagebox.getMsgid());
                editMsgid.setText(msgid);
                editMsgtimestamp.setText(messagebox.getMsgtimestamp());
                editMsgbound.setText(messagebox.getMsgbound());
                editMsgparam1.setText(messagebox.getMsgparam1());
                editMsgparam2.setText(messagebox.getMsgparam2());
                editMsgparam3.setText(messagebox.getMsgparam3());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Message")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Messagebox> results = realm.where(Messagebox.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setMsgtimestamp(editMsgtimestamp.getText().toString());
                                results.get(position).setMsgbound(editMsgbound.getText().toString());
                                results.get(position).setMsgparam1(editMsgparam1.getText().toString());
                                results.get(position).setMsgparam2(editMsgparam2.getText().toString());
                                results.get(position).setMsgparam3(editMsgparam3.getText().toString());

                                realm.commitTransaction();

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
        */
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount() ;
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;

        public TextView textMsgboxtimestamp;
        public TextView textMsgboxfrom;
        public ImageView imageMsgboxSender;
        public ImageView imageMsgboxSenderME;
        public TextView textMsgboxmessage;
        public LinearLayout linearLayoutMsgboxparent;
        public LinearLayout linearLayoutMsgboxchild;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_messagebox);
            textMsgboxtimestamp = (TextView) itemView.findViewById(R.id.text_msgbox_timestamp);
            textMsgboxfrom = (TextView) itemView.findViewById(R.id.text_msgbox_from);
            textMsgboxmessage = (TextView) itemView.findViewById(R.id.text_msgbox_message);
            imageMsgboxSender = (ImageView) itemView.findViewById(R.id.image_sender);
//            imageMsgboxSenderME = (ImageView) itemView.findViewById(R.id.image_senderme);
            linearLayoutMsgboxparent = (LinearLayout) itemView.findViewById(R.id.layout_msgboxparent);
            linearLayoutMsgboxchild = (LinearLayout) itemView.findViewById(R.id.layout_msgboxchild);
        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
