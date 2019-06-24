package com.example.contactslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;


    public ContactViewModel(@NonNull Application application) {
        super(application);


        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }

    public void insert(Contact contact){
        contactRepository.insert(contact);
    }
    public void update(Contact contact){
        contactRepository.update(contact);
    }
    public void delete(Contact contact){
        contactRepository.delete(contact);
    }
    public void deleteAllContacts(){
        contactRepository.deleteAll();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }
}
