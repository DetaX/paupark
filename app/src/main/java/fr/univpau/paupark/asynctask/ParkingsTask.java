package fr.univpau.paupark.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ListView;

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
import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.screen.ParkingsFragment;

public class ParkingsTask extends AsyncTask<Void, Void, ArrayList<Parking>> {
	private ProgressDialog progress;
	private Context context;
	ListView list;
	String result="Erreur.";

	ArrayList<Parking> parkings;
    private ParkingsFragment fragment;
	private final static String url="http://opendata.agglo-pau.fr/sc/webserv.php?serv=getSj&ui=542293D8B5&did=18&proj=WGS84";
	
	public ParkingsTask (Context context, ArrayList<Parking> parkings, ParkingsFragment fragment) {
		this.context=context;

		this.parkings=parkings;
        this.fragment = fragment;
	}
	
	@Override	
	 protected void onPreExecute() {
		progress = new ProgressDialog(context);
        progress.setMessage("Téléchargement des données");
        progress.setCancelable(false);
        progress.show();
         
     }

	
	@Override
	protected ArrayList<Parking> doInBackground(Void... params) {
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
			JSONObject jObj = new JSONObject(result);
			JSONArray jArr = jObj.getJSONArray("features");
			for (int i = 0; i<jArr.length();i++) {
				JSONObject obj = jArr.getJSONObject(i);
				Double y = (Double) obj.getJSONObject("geometry").getJSONArray("coordinates").get(0);
				Double x = (Double) obj.getJSONObject("geometry").getJSONArray("coordinates").get(1);
				boolean souterrain = obj.getJSONObject("properties").getString("Ouvrage").equals("Souterrain");
				boolean payant = obj.getJSONObject("properties").getString("Pay_grat").equals("Payant");
				String commune = obj.getJSONObject("properties").getString("COMMUNE");
				String nom = obj.getJSONObject("properties").getString("NOM");
				int places = obj.getJSONObject("properties").getInt("Places");
				Parking parking = new Parking(souterrain,payant,commune,nom,places,new Double[]{x,y});
				parkings.add(parking);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return parkings;
	}
	
	protected void onPostExecute(ArrayList<Parking>parkings) {
		if (parkings.size() > 0) {
			ParkingsFragment.firstTime = false;
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            fragment.makePages();
			/*if (prefs.getBoolean("gps", false))
	    		adapter.getFilter().filter(String.valueOf(prefs.getInt("range", 2500)+100));*/

		}
		else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder
					.setMessage("Impossible de télécharger la liste de parkings. \r\nCliquez sur options pour vérifier votre connexion à Internet ")
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
