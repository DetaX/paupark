package fr.univpau.paupark.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import fr.univpau.paupark.pojo.Tip;

public class TipAddTask extends AsyncTask<Void, Void, String> {

	private String result="Erreur.";
	private Context context;
	private ArrayList<Tip> tips;
	private Tip tip;
	private final static String url="http://detax.eu/paupark/index.php?action=add&titre=";
	
	public TipAddTask(Context context, ArrayList<Tip> tips, Tip tip) {
		this.context=context;
		this.tips=tips;
		this.tip=tip;
	}
	
	
	@Override
	protected String doInBackground(Void... params) {
		BufferedReader inStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(url + 
					URLEncoder.encode(tip.getTitre(), "UTF-8") + "&adresse=" + 
					URLEncoder.encode(tip.getAdresse(), "UTF-8") + "&commune=" + 
					URLEncoder.encode(tip.getCommune(), "UTF-8") + "&capacite=" + 
					URLEncoder.encode(String.valueOf(tip.getCapacite()), "UTF-8") + "&commentaire=" + 
					URLEncoder.encode(tip.getCommentaire(), "UTF-8") + "&pseudo=" + 
					URLEncoder.encode(tip.getPseudo(), "UTF-8"));			
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
		try {
			int id =Integer.parseInt(result.substring(0, result.length() - 1));
			toast = Toast.makeText(context, "Bon plan ajouté !", Toast.LENGTH_SHORT);
			tip.setId(id);
			tips.add(tip);
			((Activity)context).finish();
		}
		catch(NumberFormatException e) {
			toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

}
