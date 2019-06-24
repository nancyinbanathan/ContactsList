package com.example.contactslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item,viewGroup,false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder contactHolder, int i) {
        Contact currentList = contacts.get(i);
        contactHolder.textViewName.setText(currentList.getContactName());
        contactHolder.textViewPhone.setText(currentList.getContactPhone());
        contactHolder.textViewEmail.setText(currentList.getContactEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }
    public Contact getContactAt(int i){
        return contacts.get(i);
    }


    class ContactHolder extends RecyclerView.ViewHolder{

        private TextView textViewName;
        private TextView textViewPhone;
        private TextView textViewEmail;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.contact_name);
            textViewPhone = itemView.findViewById(R.id.contact_phone);
            textViewEmail = itemView.findViewById(R.id.contact_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{

        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
