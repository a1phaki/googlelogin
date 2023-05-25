package com.example.googlelogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.example.googlelogin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setSize(SignInButton.SIZE_STANDARD)
        binding.buttonSignIn.setOnClickListener {
            signIn()

        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("422298058550-ifrkk2i9sa3q77mvb2oiq639h1vt5sd8.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email
                val token = account?.idToken
                val name = account?.displayName
                binding.name.text=name
                binding.email.text=email
                Log.i("givemepass", "email:$email, token:$token")
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Log.i("givemepass", "signInResult:failed code=" + e.statusCode)
                Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.i("givemepass", "login fail")
            Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}
