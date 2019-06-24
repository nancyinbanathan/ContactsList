package com.example.contactslist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;
    //private ContactDatabase contactDatabase;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        contactDao = contactDatabase.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void delete(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public void update(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteAll() {
        new DeleteAllContactsAsyncTask(contactDao).execute();

    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        public InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }



        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        public UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }



        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        public DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }


        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }

    }

    private static class DeleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactDao contactDao;

        public DeleteAllContactsAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }



        @Override
        protected Void doInBackground(Void... Voids) {
            contactDao.deleteAllContacts();
            return null;
        }

    }
}
