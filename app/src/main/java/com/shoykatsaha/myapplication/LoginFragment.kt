package com.shoykatsaha.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoykatsaha.myapplication.ViewModel.AuthViewModel
import com.shoykatsaha.myapplication.databinding.FragmentLoginBinding
import com.shoykatsaha.myapplication.models.User

class LoginFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.loginUser(email, password,
                    onSuccess = { user ->
                        if (user.uid == "IPbsODrKNFaciVPAwencrl1RBEm2") {
                            // Navigate to Admin screen
                            findNavController().navigate(R.id.action_loginFragment_to_homeAdminFragment)
                            Toast.makeText(requireContext(), "Login Success As A Admin", Toast.LENGTH_SHORT).show()
                        } else {
                            // Navigate to normal user screen
                            findNavController().navigate(R.id.action_loginFragment_to_homeUserFragment)
                            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }


}



