package com.example.imc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.text.MessageFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalc.setOnClickListener(View.OnClickListener {
           if( validarCamposBasicos()){
               val intent = Intent(this, ResultActivity::class.java)

               intent.putExtra(
                   "value",
                   BodyMassIndex(
                       name.text.toString(),
                       weight.text.toString().toDouble(),
                       height.text.toString().toDouble()
                   )
               )

               startActivity(intent)
           }
        })

        name.addTextChangedListener(clearErrorMessage(layoutEditNome))
        height.addTextChangedListener(clearErrorMessage(layoutEditAltura))
        weight.addTextChangedListener(clearErrorMessage(layoutEditPeso))

        btnCalc.setOnLongClickListener() {
            Toast.makeText(
                this,
                "Clique longo no botão",
                Toast.LENGTH_LONG
            ).show()
            true
        }
    }


    private fun exibirMensagemErro(editText: EditText, input: TextInputLayout, mensagem: String ) {
        input.error = mensagem
        editText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        editText.requestFocus();
    }

    fun exibirMensagemErroNome() {
        exibirMensagemErro(name, layoutEditNome, formatarMensagem("Campo"))
    }

    fun exibirMensagemErroPeso() {
        exibirMensagemErro(weight, layoutEditPeso, formatarMensagem("Campo"))
    }

    fun exibirMensagemErroAltura() {
        exibirMensagemErro(height, layoutEditAltura, formatarMensagem("Campo"))
    }

    private fun validarCamposBasicos(): Boolean {
        Log.e("ERRO", name.toString()+"dsbhfkjsdhf")
        clearErrorMessage(layoutEditNome)
        val tam =name.text.toString().length
        if (name.text.toString().isEmpty() || tam < 3) {
                exibirMensagemErroNome()
                return false
        }
        clearErrorMessage(layoutEditPeso)

        if (weight.text.toString().isEmpty()) {
            exibirMensagemErroPeso()
            return false
        }
        clearErrorMessage(layoutEditAltura)

        if (height.text.toString().isEmpty()) {
            exibirMensagemErroAltura()
            return false
        }
        return true
    }

    private fun formatarMensagem(campo:String) : String{
        return  MessageFormat.format("Informe o campo", campo)
    }

    private fun clearErrorMessage(text:TextInputLayout): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int ) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int ) {
                text.error = ""
            }
        }
    }
}
