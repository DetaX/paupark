package fr.univpau.paupark.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ListView;
import fr.univpau.paupark.listener.NoDataDialogListener;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.presenter.TipAdapter;
import fr.univpau.paupark.screen.TipsFragment;

public class TipsTask extends AsyncTask<Void, Void, ArrayList<Tip>> {
	private ProgressDialog progress;
	private Context context;
	private ListView list;
	private String result="Erreur.";
	private TipAdapter adapter;
	private ArrayList<Tip> tips;
	private final static String url="http://detax.eu/paupark/";
	
	public TipsTask (Context context, TipAdapter adapter, ArrayList<Tip> tips) {
		this.context=context;
		this.adapter=adapter;
		this.tips=tips;
	}
	
	@Override	
	 protected void onPreExecute() {
		progress = new ProgressDialog(context);
        progress.setMessage("Téléchargement des données");
        progress.setCancelable(false);
        progress.show();
         
     }

	
	@Override
	protected ArrayList<Tip> doInBackground(Void... params) {
		BufferedReader inStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(url);
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
		try {
			JSONArray jArr = new JSONArray(result);
			for (int i = 0; i<jArr.length();i++) {
				JSONObject obj = jArr.getJSONObject(i);
				String titre = obj.getString("titre");
				String adresse = obj.getString("adresse");
				String commune = obj.getString("commune");
				int capacite = obj.getInt("capacite");
				String commentaire = obj.getString("commentaire");
				String pseudo = obj.getString("pseudo");
				double fiabilite = obj.getDouble("fiabilite");
				int id = obj.getInt("id");
				Tip tip = new Tip(titre, adresse, commune, capacite, commentaire, pseudo, fiabilite, id);
				tips.add(tip);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tips;
	}
	
	protected void onPostExecute(ArrayList<Tip>tips) {
		if (tips.size() > 0) {
			TipsFragment.firstTime = true;
			adapter.notifyDataSetChanged();
		}
		else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder
					.setMessage("Impossible de télécharger la liste de tips. \r\nCliquez sur options pour vérifier votre connexion à Internet ")
					.setCancelable(false)
					.setNeutralButton("Options", new NoDataDialogListener(context))
					.setPositiveButton("Ok",new NoDataDialogListener(context));
				
	 
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					alertDialog.show();
		}
        if (progress.isShowing()) 
        	progress.dismiss();
       
     }
}
