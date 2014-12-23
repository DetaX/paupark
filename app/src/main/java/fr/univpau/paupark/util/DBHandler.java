package fr.univpau.paupark.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fr.univpau.paupark.pojo.Parking;
import fr.univpau.paupark.pojo.Tip;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "PauparkDB";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE Parkings(" +
                "nom TEXT," +
                "commune TEXT," +
                "places INTEGER," +
                "payant INTEGER," +
                "souterrain INTEGER," +
                "longitude REAL," +
                "latitude REAL)";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE Tips(" +
                "id INTEGER PRIMARY KEY," +
                "titre TEXT," +
                "adresse TEXT," +
                "commune TEXT," +
                "capacite INTEGER," +
                "commentaire TEXT," +
                "pseudo TEXT," +
                "fiabilite REAL)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Parkings");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Tips");
        this.onCreate(sqLiteDatabase);
    }

    public void addAllTips(ArrayList<Tip> tips) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Tips");
        ContentValues values = new ContentValues();
        for (Tip t : tips) {
            values.put("id", t.getId());
            values.put("titre", t.getTitre());
            values.put("adresse", t.getAdresse());
            values.put("commune", t.getCommune());
            values.put("capacite", t.getCapacite());
            values.put("commentaire", t.getCommentaire());
            values.put("pseudo", t.getPseudo());
            values.put("fiabilite", t.getFiabilite());
            db.insert("Tips", null, values);
        }
        db.close();
    }

    public void addAllParkings(ArrayList<Parking> parkings) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Parkings");
        ContentValues values = new ContentValues();
        for (Parking p : parkings) {
            values.put("nom", p.getNom());
            values.put("commune", p.getCommune());
            values.put("places", p.getPlaces());
            values.put("souterrain", (p.isSouterrain() ? 1 : 0));
            values.put("payant", (p.isPayant() ? 1 : 0));
            values.put("longitude", p.getCoord()[0]);
            values.put("latitude", p.getCoord()[1]);
            db.insert("Parkings", null, values);
        }
        db.close();
    }
    public ArrayList<Tip> getAllTips() {
        ArrayList<Tip> tips = new ArrayList<Tip>();
        String query = "SELECT * FROM Tips";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titre = cursor.getString(1);
                String adresse = cursor.getString(2);
                String commune = cursor.getString(3);
                int capacite = cursor.getInt(4);
                String commentaire = cursor.getString(5);
                String pseudo = cursor.getString(6);
                float fiabilite = cursor.getFloat(7);
                Tip t  = new Tip(titre,adresse,commune,capacite,commentaire,pseudo,fiabilite,id);
                tips.add(t);
            } while (cursor.moveToNext());
        }
        return tips;
    }
    public ArrayList<Parking> getAllParkings() {
        ArrayList<Parking> parkings = new ArrayList<Parking>();
        String query = "SELECT * FROM Parkings";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                boolean souterrain = cursor.getInt(3) == 1;
                boolean payant = cursor.getInt(4) == 1;
                String commune = cursor.getString(1);
                String nom = cursor.getString(0);
                int places = cursor.getInt(2);
                double longitude = cursor.getFloat(5);
                double latitude = cursor.getFloat(6);
                Parking p  = new Parking(souterrain,payant,commune,nom,places,new Double[]{longitude, latitude});
                parkings.add(p);
            } while (cursor.moveToNext());
        }
        return parkings;
    }
}
