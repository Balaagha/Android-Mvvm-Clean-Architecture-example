package com.example.data.database.feature.mobilenumber.repository

import com.example.data.database.feature.mobilenumber.dao.ContactListDao
import com.example.data.database.feature.mobilenumber.model.CachedMobileNumber
import com.example.data.database.feature.mobilenumber.model.MobileNumber
import com.example.data.database.utils.toCache
import com.example.data.database.utils.toDomain
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