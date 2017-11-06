/*
 * Copyright (c) 2017 大前良介(OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.android.vmb

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast

/**
 * @author <a href="mailto:ryo@mm2d.net">大前良介 (OHMAE Ryosuke)</a>
 */
class RecognizerDialog : DialogFragment() {
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var beatingView: BeatingView

    interface RecognizeListener {
        fun onRecognize(results: ArrayList<String>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onBufferReceived(buffer: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
            }

            override fun onRmsChanged(rmsdB: Float) {
                beatingView.onRmsChanged(rmsdB)
            }

            override fun onError(error: Int) {
                Toast.makeText(context, R.string.toast_voice_input_fail, Toast.LENGTH_LONG).show()
                dismiss()
            }

            override fun onPartialResults(partialResults: Bundle?) {
                if (partialResults == null) {
                    return
                }
                val list = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (list.sumBy { it.length } > 0) {
                    recognizer.stopListening()
                }
            }

            override fun onResults(results: Bundle?) {
                if (results == null) {
                    return
                }
                val list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                (targetFragment as? RecognizeListener)!!.onRecognize(list)
                dismiss()
            }
        })
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        recognizer.startListening(intent)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_recognizer, null, false);
        beatingView = view.findViewById(R.id.beating_view);
        return AlertDialog.Builder(context)
                .setView(view)
                .create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        recognizer.stopListening()
        recognizer.destroy()
    }

    companion object {
        fun newInstance(): RecognizerDialog {
            return RecognizerDialog()
        }
    }
}