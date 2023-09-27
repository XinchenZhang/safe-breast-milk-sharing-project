package com.example.breastmilkapplication

import android.provider.ContactsContract.CommonDataKinds.Phone

class Profile {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var phone: String? = null
    var address: String? = null
    var city: String? = null
    var state: String? = null
    var zip: String? = null
    var height: String? = null
    var weight: String? = null
    var age: String? = null
    var gender: String? = null

    constructor()

    constructor(
        name: String?, email: String?, uid: String?, phone: String?,
        address: String?, city: String?, state: String?, zip: String?,
        height: String?, weight: String?, age: String?, gender: String?) {
        this.name = name
        this.email = email
        this.uid = uid
        this.phone = phone
        this.address = address
        this.city = city
        this.state = state
        this.zip = zip
        this.height = height
        this.weight = weight
        this.age = age
        this.gender = gender
    }
}
