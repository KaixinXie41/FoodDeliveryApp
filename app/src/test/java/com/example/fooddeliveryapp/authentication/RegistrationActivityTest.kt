package com.example.fooddeliveryapp.authentication

import com.example.fooddeliveryapp.model.local.validation.ValidateRegistrationInput
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test


internal class RegistrationActivityTest {

    // Test Empty entry data
    @Test
    fun `When email is empty then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            EMPTY_STRING,
            TEST_PASSWORD,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When password is empty then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            EMPTY_STRING,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }
    @Test
    fun `When mobile is empty then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_PASSWORD,
            EMPTY_STRING,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When username is empty then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_MOBILE,
            EMPTY_STRING
        )
        assertFalse(output)
    }


    @Test
    fun `When username,password,mobile,email is correct then return true`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertTrue(output)
    }

    @Test
    fun `When email already exists then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EXISTS_EMAIL,
            TEST_PASSWORD,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When mobile already exists then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_EXISTS_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Test
    fun `When username already exists then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_PASSWORD,
            TEST_MOBILE,
            TEST_EXISTS_USERNAME
        )
        assertFalse(output)
    }


    @Test
    fun `When Password is less then six char then return false`(){
        val output = ValidateRegistrationInput.isValidRegistrationInput(
            TEST_EMAIL,
            TEST_INVALID_PASSWORD,
            TEST_MOBILE,
            TEST_USERNAME
        )
        assertFalse(output)
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    companion object{
        const val TEST_USERNAME = "test"
        const val TEST_PASSWORD = "12345678"
        const val TEST_MOBILE = "2140090087"
        const val TEST_EMAIL = "xkx@gmail.com"
        const val TEST_INVALID_PASSWORD = "12345"
        const val EMPTY_STRING = ""
        const val TEST_EXISTS_USERNAME = "Kai"
        const val TEST_EXISTS_MOBILE ="2140090089"
        const val TEST_EXISTS_EMAIL = "kxie0766@gmail.com"
    }
}