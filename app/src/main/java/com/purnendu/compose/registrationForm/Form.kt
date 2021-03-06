package com.purnendu.compose.registrationForm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.Matcher
import java.util.regex.Pattern

class Form : ComponentActivity() {

    private var firstName by mutableStateOf("")
    private var lastName by mutableStateOf("")
    private var mobileNo by mutableStateOf("")
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")
    private var confirmPassword by mutableStateOf("")

    private var isFirstNameError by mutableStateOf(false)
    private var isLastNameError by mutableStateOf(false)
    private var isMobileNumberError by mutableStateOf(false)
    private var isEmailError by mutableStateOf(false)
    private var isPasswordError by mutableStateOf(false)
    private var isConfirmPasswordError by mutableStateOf(false)

    private var firstNameErrorMessage by mutableStateOf("")
    private var lastNameErrorMessage by mutableStateOf("")
    private var mobileNoErrorMessage by mutableStateOf("")
    private var emailErrorMessage by mutableStateOf("")
    private var passwordErrorMessage by mutableStateOf("")
    private var confirmPasswordErrorMessage by mutableStateOf("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                val context = LocalContext.current

                Text(
                    text = "Sign Up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Want to sign up fill out this form!",
                    color = Color.Gray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(0.5.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = firstName,
                    labelContent = "First Name",
                    isError = isFirstNameError,
                    errorMessage = firstNameErrorMessage,
                    keyboardType = KeyboardType.Text,
                )
                {
                    firstName = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = lastName,
                    labelContent = "Last Name",
                    isError = isLastNameError,
                    errorMessage = lastNameErrorMessage,
                    keyboardType = KeyboardType.Text
                )
                {
                    lastName = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = mobileNo,
                    labelContent = "Mobile No",
                    isError = isMobileNumberError,
                    errorMessage = mobileNoErrorMessage,
                    keyboardType = KeyboardType.Phone
                )
                {
                    mobileNo = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = email,
                    labelContent = "Email",
                    isError = isEmailError,
                    errorMessage = emailErrorMessage,
                    keyboardType = KeyboardType.Email
                )
                {
                    email = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = password,
                    labelContent = "Password",
                    isError = isPasswordError,
                    errorMessage = passwordErrorMessage,
                    keyboardType = KeyboardType.Password
                )
                {
                    password = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    text = confirmPassword,
                    labelContent = "Confirm Password",
                    isError = isConfirmPasswordError,
                    errorMessage = confirmPasswordErrorMessage,
                    keyboardType = KeyboardType.Password
                )
                {
                    confirmPassword = it
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff74b9ff)),
                    onClick = {

                        if (firstName.isEmpty()) {
                            isFirstNameError = true
                            firstNameErrorMessage = "Required Field"
                            return@Button
                        }
                        if (!validateName(firstName)) {
                            isFirstNameError = true
                            firstNameErrorMessage = "More than 20 letters not allowed"
                            return@Button

                        }
                        isFirstNameError = false

                        if (lastName.isEmpty()) {
                            isLastNameError = true
                            lastNameErrorMessage = "Required Field"
                            return@Button
                        }
                        if (!validateName(lastName)) {
                            isLastNameError = true
                            lastNameErrorMessage = "More than 20 letters not allowed"
                            return@Button

                        }
                        isLastNameError = false

                        if (mobileNo.isEmpty()) {
                            isMobileNumberError = true
                            mobileNoErrorMessage = "Required Field"
                            return@Button
                        }
                        if (!validatePhoneNumber(mobileNo)) {
                            isMobileNumberError = true
                            mobileNoErrorMessage = "Not a valid Mobile Number"
                            return@Button
                        }
                        isMobileNumberError = false

                        if (email.isEmpty()) {
                            isEmailError = true
                            emailErrorMessage = "Required Field"
                            return@Button
                        }
                        if (!validateEmail(email)) {
                            isEmailError = true
                            emailErrorMessage = "Not a valid Email"
                            return@Button
                        }
                        isEmailError = false

                        if (password.isEmpty()) {
                            isPasswordError = true
                            passwordErrorMessage = "Required Field"
                            return@Button
                        }

                        if (password.length < 10) {
                            isPasswordError = true
                            passwordErrorMessage = "Password should be at least 10 characters long "
                            return@Button
                        }
                        isPasswordError = false

                        if (confirmPassword.isEmpty()) {
                            isConfirmPasswordError = true
                            confirmPasswordErrorMessage = "Required Field"
                            return@Button
                        }

                        if (!validatePassword()) {
                            isConfirmPasswordError = true
                            confirmPasswordErrorMessage = "Password not matching"
                            return@Button
                        }
                        isConfirmPasswordError = false

                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT)
                            .show()

                        firstName = ""
                        lastName = ""
                        mobileNo = ""
                        email = ""
                        password = ""
                        confirmPassword = ""


                    }) {
                    Text(
                        modifier = Modifier.padding(5.dp), text = "Sign Up"
                    )
                }


            }
        }
    }

    private fun validateName(name: String): Boolean = name.length < 20


    private fun validatePhoneNumber(number: String): Boolean {
        if (number.matches(Regex("[0-9]+"))) {
            if (number.length == 10)
                return true
        }
        return false
    }

    private fun validateEmail(email: String): Boolean {
        val matcher: Matcher =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email)
        return matcher.find()
    }

    private fun validatePassword(): Boolean = password == confirmPassword


}