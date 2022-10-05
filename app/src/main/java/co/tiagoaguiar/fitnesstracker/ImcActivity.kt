package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeigth: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeigth = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)

        btnSend.setOnClickListener {
            if (!validade()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val weigth = editWeight.text.toString().toInt()
            val heigth = editHeigth.text.toString().toInt()

            val result = calculateImc(weigth, heigth)
            Log.d("teste", "result:  $result")

            val imcResponseId = imcResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                //aqui vai rodar depois do click
                }
                .create()
                .show()

            //Esconder teclado
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //capturar serviço de hardware
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0) //
        }
    }

    private fun validade(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeigth.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeigth.text.toString().startsWith("0"))
    }

    private fun calculateImc(weigth: Int, heigth: Int): Double {
        var heigthInMeters = heigth / 100.0
        return weigth / (heigthInMeters * heigthInMeters)
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

}