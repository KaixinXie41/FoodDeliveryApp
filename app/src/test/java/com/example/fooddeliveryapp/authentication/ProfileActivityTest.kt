package com.example.fooddeliveryapp.authentication

import com.example.fooddeliveryapp.model.local.validation.ValidateProfileInput
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test


internal class ProfileActivityTest {

    // Test Empty entry data
    @Test
    fun `When email is empty then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            EMPTY_STRING,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When mobile is empty then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            RegistrationActivityTest.TEST_EMAIL,
            RegistrationActivityTest.EMPTY_STRING,
            RegistrationActivityTest.TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When username is empty then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            RegistrationActivityTest.TEST_EMAIL,
            RegistrationActivityTest.TEST_MOBILE,
            RegistrationActivityTest.EMPTY_STRING
        )
        assertFalse(output)
    }


    @Test
    fun `When email is not equal to old email and already exists then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            TEST_EXISTS_EMAIL,
            TEST_MOBILE,
            TEST_USERNAME
            )
        assertFalse(output)
    }

    @Test
    fun `When mobile is not equal to old mobile and already exists then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            TEST_EMAIL,
            TEST_EXISTS_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When username is not equal to old username and already exists then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            TEST_EMAIL,
            TEST_MOBILE,
            TEST_EXISTS_USERNAME
        )
        assertFalse(output)
    }


    @Test
    fun `When Password is not match email pattern then return false`(){
        val output = ValidateProfileInput.isValidProfileInput(
            TEST_INVALID_EMAIL,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When username,mobile,email is correct then return true`(){
        val output = ValidateProfileInput.isValidProfileInput(
            RegistrationActivityTest.TEST_EMAIL,
            RegistrationActivityTest.TEST_MOBILE,
            RegistrationActivityTest.TEST_USERNAME
        )
        assertTrue(output)
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    companion object{
        const val TEST_USERNAME = "test"
        const val TEST_MOBILE = "2140090097"
        const val TEST_EMAIL = "test@gmail.com"
        const val TEST_INVALID_EMAIL = "test.com"
        const val EMPTY_STRING = ""
        const val TEST_EXISTS_USERNAME = "Mark"
        const val TEST_EXISTS_MOBILE = "2140090089"
        const val TEST_EXISTS_EMAIL = "kaixin@yahoo.com"

    }
}