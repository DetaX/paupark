package fr.univpau.paupark.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.util.Util;

public class RatingTask extends AsyncTask<Void, Void, String> {
	private String result="Erreur.";
	private double note;
	private int id;
	private Context context;
	private ArrayList<Tip> tips;
	private final static String url="http://detax.eu/paupark/index.php?action=fiabilite&id=";
	
	public RatingTask(Context context, double note, int id, ArrayList<Tip> tips) {
		this.note=note;
		this.id=id;
		this.context=context;
		this.tips=tips;
	}
	
	
	@Override
	protected String doInBackground(Void... params) {
		BufferedReader inStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(url + id + "&note=" + note);
			HttpResponse response = httpClient.execute(httpRequest);
			inStream = new BufferedReader(
				new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer buffer = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = inStream.readLine()) != null) {
				buffer.append(line + NL);
			}
			inStream.close();

			result = buffer.toString();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Toast toast;
		if (result.equals("Erreur.")) 
			toast = Toast.makeText(context, "Il y a eu une erreur", Toast.LENGTH_SHORT);
		else {
			String strToast = "Nouvelle fiabilité : " + result;
			toast = Toast.makeText(context, strToast, Toast.LENGTH_SHORT);
			for (int i=0;i<tips.size();i++) 
				if (tips.get(i).getId() == id) {
					Tip tip = tips.get(i);
					tip.setFiabilite(Float.valueOf(result));
					Util.dialog(context, tip,tips);
					Log.i("prout", tip.getTitre());
					break;
				}
		}
		toast.show();
		
		
	}
}
