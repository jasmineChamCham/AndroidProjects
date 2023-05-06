package com.example.contactapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>  {
    private ArrayList<Contact> contactList;

    public OnItemLongClickListener longClickListener;
    public OnItemClickListener clickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Contact contact);
    }

    public interface OnItemClickListener{
        void onItemClick(Contact contact);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ContactAdapter(ArrayList<Contact> contactList){
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        if (contactList.get(position).getFirstName().length() > 0)
            holder.tvName1.setText("" + contactList.get(position).getFirstName().charAt(0));
        if (contactList.get(position).getLastName().length() > 0)
            holder.tvName2.setText("" + contactList.get(position).getFirstName().charAt(0));
        if (contactList.get(position).getFullName().length() > 0)
            holder.tvFullName.setText(contactList.get(position).getFullName());

        Log.d("add with ava ", "position="+position);
        if (!contactList.get(position).getAvatar().equals("")) {
            File imgFile = new File(contactList.get(position).getAvatar());
            if (imgFile!=null){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    Log.d("myBitmap=",myBitmap.toString());
                    holder.ivAvatarRow.setImageBitmap(myBitmap);
                    holder.tvName2.setText("");
                    holder.fabRow.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            Log.d("add without ava", "");
        }
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName1;
        public TextView tvName2;
        public TextView tvFullName;
        public ImageView ivAvatarRow;
        public FloatingActionButton fabRow;
        public ViewHolder(View view){
            super(view);
            tvName1 =  view.findViewById(R.id.tv_name_1);
            tvName2 = view.findViewById(R.id.tv_name_2);
            tvFullName = view.findViewById(R.id.tv_full_name);
            ivAvatarRow = view.findViewById(R.id.ivAvatarRow);
            fabRow = view.findViewById(R.id.fab_row);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && longClickListener != null) {
                        longClickListener.onItemLongClick(contactList.get(position));
                    }
                    return true;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && clickListener != null) {
                        clickListener.onItemClick(contactList.get(position));
                    }
                }
            });

        }
    }


}
