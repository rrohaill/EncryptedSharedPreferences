package io.rohail.encryptedsharedpreferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.rohail.encryptedsharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val preferenceManager: EncryptedPreferenceManager by lazy { EncryptedPreferenceManager(this) }
    private val INPUT_KEY = "input"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            button.setOnClickListener {
                preferenceManager.save(INPUT_KEY, etInput.text.toString())
            }
        }
    }
}