package sewisc.classroomfinder;

import android.os.Build;

/**
 * Created by Matthew on 3/14/2017.
 */
public class BuildingDB {

    //table variables
    int id;
    String name;

    public BuildingDB(){
        //empty constructor
    }

    public BuildingDB(int id, String name){
        this.id = id;
        this.name = name;
    }

    //the following are getter methods for a record's fields
    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    //the following are setter methods for a record's fields
    public void setID(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }


}

