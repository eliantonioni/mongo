package com.luxoft.services;

import com.luxoft.dao.ContactDao;
import com.luxoft.model.Contact;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactDao contactDao;

    public void insertOrUpdate(Contact contact) {
        if (StringUtils.isBlank(contact.getId())) {
            contact.setId(null);
            contactDao.save(contact);
        }
        else {
            contactDao.save(contact);
        }
    }

    public Contact get(Long id) {
        return contactDao.get(id);
    }

    public List<Contact> getAll() {
        return contactDao.getAll();
    }

    public void remove(Long id) {
        contactDao.remove(id);
    }
}
