package sewisc.classroomfinder;

/**
 * Created by Matthew on 3/14/2017.
 */
public class Favorite {

    //table variables
    String building_name;
    String start_location;
    String destination;
    int indx;

    public Favorite(){
        //empty constructor
    }

    public Favorite(int indx, String building_name, String start_location, String destination){
        this.indx = indx;
        this.building_name = building_name;
        this.start_location = start_location;
        this.destination = destination;
    }

    //the following are getter methods for a record's fields
    public int getIndx() {
        return this.indx;
    }
    public String getBuildingName(){
        return this.building_name;
    }

    public String getStartLocation(){
        return this.start_location;
    }
    public String getDestination(){
        return this.destination;
    }

    // the following are setter methods for a record's fields

    public void setIndx(int indx){
        this.indx = indx;
    }

    public void setBuildingName(String buildingName){
        this.building_name = buildingName;
    }

    public void setStartLocation(String startLocation){
        this.start_location= startLocation;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }


}

