package com.example.testetexttospeech;

import java.io.File;
import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnInitListener{
	private TextToSpeech tts;
	private Button btnFalar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tts = new TextToSpeech(this, this);
		btnFalar = (Button)findViewById(R.id.button1);
		
	}
	
	
	@Override
	public void onInit(int status) {
		// TVerifica se TTS foi carregado
		if (status == TextToSpeech.SUCCESS) {
			//habilita o botão
			btnFalar.setEnabled(true);
		}		
	}
	
	
	public void falarTexto(View view) {
		EditText editText =(EditText)findViewById(R.id.editText1);
		//Log.i("script", "Máximo número de caracteres: "+TextToSpeech.getMaxSpeechInputLength());
		
		//verifica se o idioma portugues é suportado
		int codigoidioma = tts.isLanguageAvailable(new Locale("pt", "BR"));
		if (codigoidioma == TextToSpeech.LANG_AVAILABLE) {
			tts.setLanguage(new Locale("pt", "BR"));
		}else{
			tts.setLanguage(Locale.ENGLISH);
			Toast.makeText(MainActivity.this, "Codigo: "+ codigoidioma, Toast.LENGTH_SHORT).show();
		}
		//Tira todas as palavras da fila e fala o texto do editText
		tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
		
		//Adiciona palavras na fila para serem reproduzidas
		tts.speak("Hello", TextToSpeech.QUEUE_ADD, null);
		tts.speak("my faforite band is", TextToSpeech.QUEUE_ADD, null);
		tts.speak("Red Hot Chilli Peppers.", TextToSpeech.QUEUE_ADD, null);
		
		//Faz uma gravação em .wav do texto falado 
		File file = android.os.Environment.getExternalStorageDirectory();
		tts.synthesizeToFile(editText.getText().toString(), null, file.getAbsolutePath()+"/testeTTS.wav");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		tts.shutdown();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
}
