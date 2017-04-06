package sewisc.classroomfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 3/14/2017.
 */
public class DataBaseHandler extends SQLiteOpenHelper{
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "classroomFinder";

    //Table Names
    private static final String TABLE_FAVORITE = "favorites";
    private static final String TABLE_BUILDING = "buildings";
    private static final String TABLE_LOCATION = "locations";

    //Favorites Table Column names
    private static final String KEY_INDX = "indx";
    private static final String KEY_BUILDING_NAME = "building_name";
    private static final String KEY_START_LOCATION = "start_location";
    private static final String KEY_DESTINATION = "destination";

    //Buildings Table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    //Locations Table Column names
    //these are both common names so just reuse the Buildings table ones
    //private static final String KEY_ID = "id";
    //private static final String KEY_NAME = "name";
    private static final String KEY_BUILDING_ID = "building_id";
    private static final String KEY_FLOOR_NUMBER = "floor_number";


    //sql commands for creating the tables
    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "("
            + KEY_INDX  + " INTEGER PRIMARY KEY,"
            + KEY_BUILDING_NAME + " TEXT,"
            + KEY_START_LOCATION + " TEXT,"
            + KEY_DESTINATION + " TEXT" + ")";

    private static final String CREATE_TABLE_BUILDING = "CREATE TABLE " + TABLE_BUILDING + "("
            + KEY_ID  + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION+ "("
            + KEY_ID  + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_BUILDING_ID + " INTEGER,"
            + KEY_FLOOR_NUMBER + " INTEGER" + ")";




    public DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create tables
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_BUILDING);
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //what to do when updating the version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        //recreate tables that were dropped
        onCreate(db);
    }


    public void clearAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        onCreate(db);
    }
    //returns current version
    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }


    //INSERTS A FAVORITE RECORD
    public void addFavorite(Favorite favorite){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUILDING_NAME, favorite.getBuildingName());
        values.put(KEY_START_LOCATION, favorite.getStartLocation());
        values.put(KEY_DESTINATION, favorite.getDestination());
        values.put(KEY_INDX, favorite.getIndx());

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
    }

    //INSERTS A BUILDING RECORD
    public void addBuilding(BuildingDB building){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, building.getID());
        values.put(KEY_NAME, building.getName());

        db.insert(TABLE_BUILDING, null, values);
        db.close();
    }

    //INSERTS A LOCATION RECORD
    public void addLocation(Location location){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, location.getID());
        values.put(KEY_NAME, location.getName());
        values.put(KEY_BUILDING_ID, location.getBuildingID());
        values.put(KEY_FLOOR_NUMBER, location.getFloorNumber());

        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }


    //RETRIEVES FAVORITE RECORD
    public Favorite getFavorite(String building_name){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_FAVORITE + " WHERE "
                + KEY_BUILDING_NAME + " = " + building_name;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Favorite favorite = new Favorite();
        favorite.setIndx(c.getInt(c.getColumnIndex(KEY_INDX)));
        favorite.setBuildingName(c.getString(c.getColumnIndex(KEY_BUILDING_NAME)));
        favorite.setStartLocation(c.getString(c.getColumnIndex(KEY_START_LOCATION)));
        favorite.setDestination(c.getString(c.getColumnIndex(KEY_DESTINATION)));

        return favorite;
    }

    //RETRIEVES BUILDING RECORD
    public BuildingDB getBuilding(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_BUILDING + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        BuildingDB building = new BuildingDB();
        building.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        building.setName(c.getString(c.getColumnIndex(KEY_NAME)));

        return building;
    }

    //RETRIEVES LOCATION RECORD
    public Location getLocation(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Location location = new Location();
        location.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        location.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        location.setBuildingID(c.getInt(c.getColumnIndex(KEY_BUILDING_ID)));
        location.setFloorNumber(c.getInt(c.getColumnIndex(KEY_FLOOR_NUMBER)));


        return location;
    }

    //returns a list of all favorites
    public List<Favorite> getAllFavorites(){
        List<Favorite> favorites = new ArrayList<Favorite>();
        String selectQuery = "SELECT * FROM "  + TABLE_FAVORITE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loops through rows and adds each to the list
        if (c.moveToFirst()){
            do {
                Favorite favorite = new Favorite();
                favorite.setIndx(c.getInt(c.getColumnIndex(KEY_INDX)));
                favorite.setBuildingName(c.getString(c.getColumnIndex(KEY_BUILDING_NAME)));
                favorite.setStartLocation(c.getString(c.getColumnIndex(KEY_START_LOCATION)));
                favorite.setDestination(c.getString(c.getColumnIndex(KEY_DESTINATION)));

                favorites.add(favorite);
            } while (c.moveToNext());
        }

        return favorites;
    }
    //returns a list of all buildings
    public List<BuildingDB> getAllBuildings(){
        List<BuildingDB> buildings = new ArrayList<BuildingDB>();
        String selectQuery = "SELECT * FROM "  + TABLE_BUILDING;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loops through rows and adds each to the list
        if (c.moveToFirst()){
            do {
                BuildingDB building = new BuildingDB();
                building.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                building.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                buildings.add(building);
            } while (c.moveToNext());
        }

        return buildings;
    }
    //returns a list of all locations
    public List<Location> getAllLocations(){
        List<Location> locations = new ArrayList<Location>();
        String selectQuery = "SELECT * FROM "  + TABLE_LOCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loops through rows and adds each to the list
        if (c.moveToFirst()){
            do {
                Location location = new Location();
                location.setID(c.getInt(c.getColumnIndex(KEY_ID)));
                location.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                location.setBuildingID(c.getInt(c.getColumnIndex(KEY_BUILDING_ID)));
                location.setFloorNumber(c.getInt(c.getColumnIndex(KEY_FLOOR_NUMBER)));

                locations.add(location);
            } while (c.moveToNext());
        }

        return locations;
    }

    //returns favorite record count
    public int getFavoritesCount(){
        String selectQuery = "SELECT * FROM "  + TABLE_FAVORITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    //returns buildings record count
    public int getBuildingsCount(){
        String selectQuery = "SELECT * FROM "  + TABLE_BUILDING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    //returns locations record count
    public int getLocationsCount(){
        String selectQuery = "SELECT * FROM "  + TABLE_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    //updates favorite record
    public int updateFavorite(Favorite favorite){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUILDING_NAME, favorite.getBuildingName());
        values.put(KEY_START_LOCATION, favorite.getStartLocation());
        values.put(KEY_DESTINATION, favorite.getDestination());
        values.put(KEY_INDX, favorite.getIndx());

        return db.update(TABLE_FAVORITE, values, KEY_BUILDING_NAME + " = ?",
                new String[]{String.valueOf(favorite.getBuildingName())});
    }

    //updates building record
    public int updateBuilding(BuildingDB building){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, building.getID());
        values.put(KEY_NAME, building.getName());

        return db.update(TABLE_BUILDING, values, KEY_ID + " = ?",
                new String[] {String.valueOf(building.getID())});
    }
    //updates location record
    public int updateLocation(Location location){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, location.getID());
        values.put(KEY_NAME, location.getName());
        values.put(KEY_BUILDING_ID, location.getBuildingID());
        values.put(KEY_FLOOR_NUMBER, location.getFloorNumber());

        return db.update(TABLE_LOCATION, values, KEY_ID + " = ?",
                new String[] {String.valueOf(location.getID())});
    }
    //deletes favorite record
    public void deleteFavorite(Favorite favorite){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_FAVORITE, KEY_INDX + " = ?", //KEY_BUILDING_NAME + "=? and " + KEY_START_LOCATION + "=? and " + KEY_DESTINATION + "=?",
                new String[]{String.valueOf(favorite.getIndx())});
                //new String[]{String.valueOf(favorite.getBuildingName()), String.valueOf(favorite.getStartLocation()), String.valueOf(favorite.getDestination())});
        db.close();
    }
    //deletes building record
    public void deleteBuilding(BuildingDB building){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_BUILDING, KEY_ID + " = ?",
                new String[]{String.valueOf(building.getID())});
        db.close();
    }
    //deletes location record
    public void deleteLocation(Location location){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_LOCATION, KEY_ID + " = ?",
                new String[] {String.valueOf(location.getID())});
        db.close();
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
