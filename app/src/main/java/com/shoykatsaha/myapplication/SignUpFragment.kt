package com.shoykatsaha.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shoykatsaha.myapplication.ViewModel.AuthViewModel
import com.shoykatsaha.myapplication.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnregister.setOnClickListener {
            val  name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmpassword = binding.etcomfirmPassword.text.toString().trim()

            if (confirmpassword == password && email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.registerUser(email, password,name,
                    onSuccess = { user ->
                        Toast.makeText(requireContext(), "it can success register" , Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment512)
                        // Handle successful registration, maybe navigate to another screen
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), "Can not register Yet" , Toast.LENGTH_SHORT).show()
                    }
                )
            }
            else{
                Toast.makeText(requireContext(), "password Can not Match", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnregister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.registerUser(email, password,
                    onSuccess = { user ->
                        Toast.makeText(requireContext(), "it can success register" , Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                        // Handle successful registration, maybe navigate to another screen
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), "it can not register" , Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }*/


}