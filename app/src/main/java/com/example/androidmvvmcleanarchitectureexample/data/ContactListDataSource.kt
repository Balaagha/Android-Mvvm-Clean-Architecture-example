package com.example.androidmvvmcleanarchitectureexample.data

import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.CachedMobileNumber
import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.ContactListDao
import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.MobileNumber
import com.example.androidmvvmcleanarchitectureexample.data.database.utils.toCache
import com.example.androidmvvmcleanarchitectureexample.data.database.utils.toDomain
import javax.inject.Inject

class ContactListDataSource @Inject constructor(
    private val contactListDao: ContactListDao
) {

    suspend fun getContactList(): List<MobileNumber> {
        return contactListDao.getContactList().map(CachedMobileNumber::toDomain)
    }

    suspend fun saveContactList(contactList: List<MobileNumber>) {
        val contactListValue = contactList.map(MobileNumber::toCache)
        contactListDao.insert(contactListValue)
    }

    suspend fun saveContact(contact: MobileNumber) {
        val contactListValue = contact.toCache()
        contactListDao.insert(contactListValue)
    }

    suspend fun deleteAll() {
        contactListDao.clearAll()
    }

    suspend fun delete(contact: MobileNumber) {
        val contactValue = contact.toCache()
        contactListDao.delete(contactValue)
    }

}