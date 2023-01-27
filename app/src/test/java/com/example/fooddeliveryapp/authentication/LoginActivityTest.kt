package com.example.fooddeliveryapp.authentication

import com.example.fooddeliveryapp.model.local.validation.ValidateLoginInput
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test


internal class LoginActivityTest {

    // Test Empty entry data
    @Test
    fun `When email is empty then return false`(){
        val output = ValidateLoginInput.isValidLoginInput(
            EMPTY_STRING,
            TEST_PASSWORD1
        )
        assertFalse(output)
    }

    @Test
    fun `When password is empty then return false`(){
        val output = ValidateLoginInput.isValidLoginInput(
            TEST_EXISTS_EMAIL1,
            EMPTY_STRING
        )
        assertFalse(output)
    }

    @Test
    fun `When email is not exists then return false`(){
        val output = ValidateLoginInput.isValidLoginInput(
            TEST_EMAIL,
            TEST_PASSWORD1
        )
        assertFalse(output)
    }

    @Test
    fun `When password is less then six char then return false`(){
        val output = ValidateLoginInput.isValidLoginInput(
            TEST_EXISTS_EMAIL1,
            TEST_INVALID_PASSWORD
        )
        assertFalse(output)
    }

    @Test
    fun `When email is not match with password then return false`(){
        val output = ValidateLoginInput.isValidLoginInput(
            TEST_EXISTS_EMAIL1,
            TEST_PASSWORD2
        )
        assertFalse(output)
    }

    @Test
    fun `When email,password is correct then return true`(){
        val output = ValidateLoginInput.isValidLoginInput(
            TEST_EXISTS_EMAIL1,
            TEST_PASSWORD1
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
        const val TEST_PASSWORD1 = "12345678"
        const val TEST_PASSWORD2 = "87654321"
        const val TEST_EMAIL = "xkx@gmail.com"
        const val TEST_INVALID_PASSWORD = "12345"
        const val EMPTY_STRING = ""
        const val TEST_EXISTS_EMAIL1 = "kaixin@yahoo.com"
    }
}