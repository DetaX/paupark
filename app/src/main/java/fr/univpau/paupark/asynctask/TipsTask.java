package fr.univpau.paupark.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.univpau.paupark.listener.NoDataDialogListener;
import fr.univpau.paupark.pojo.Tip;
import fr.univpau.paupark.screen.TipsFragment;
import fr.univpau.paupark.util.DBHandler;

public class TipsTask extends AsyncTask<Void, Void, ArrayList<Tip>> {
	private ProgressDialog progress;
	private String result="";
    private final TipsFragment fragment;
	private final static String url="http://detax.eu/paupark/";
	
	public TipsTask (TipsFragment fragment) {
		this.fragment=fragment;
	}
	
	@Override	
	 protected void onPreExecute() {
		progress = new ProgressDialog(fragment.getActivity());
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

			StringBuilder buffer = new StringBuilder("");
			String line;
			String NL = System.getProperty("line.separator");
			while ((line = inStream.readLine()) != null) {
				buffer.append(line).append(NL);
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
                fragment.getTips().add(tip);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return fragment.getTips();
	}
	
	protected void onPostExecute(ArrayList<Tip>tips) {
        DBHandler db = new DBHandler(fragment.getActivity());
		if (tips.size() > 0) {
			TipsFragment.firstTime = false;
            db.addAllTips(tips);
            fragment.makePages(tips);
		}
		else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(fragment.getActivity());
				alertDialogBuilder
					.setMessage("Impossible de télécharger la liste de tips. \r\nCliquez sur options pour vérifier votre connexion à Internet\n\nCliquez sur OK pour chager la liste de votre dernière connexion")
					.setCancelable(false)
					.setNeutralButton("Options", new NoDataDialogListener(fragment.getActivity(),db,fragment))
					.setPositiveButton("Ok", new NoDataDialogListener(fragment.getActivity(), db, fragment));

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
		}
        if (progress.isShowing()) 
        	progress.dismiss();
       
     }
}
