package sewisc.classroomfinder;

/**
 * Created by Matthew on 3/14/2017.
 */
public class Location {
    //table variables
    int id;
    String name;
    int building_id;
    int floor_number;

    public Location(){
        //empty constructor
    }

    public Location(int id, String name, int building_id, int floor_number){
        this.id = id;
        this.name = name;
        this.building_id = building_id;
        this.floor_number = floor_number;
    }

    //the following are getter methods for a record's fields
    public int getID() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public int getBuildingID(){
        return this.building_id;
    }
    public int getFloorNumber(){
        return this.floor_number;
    }

    // the following are setter methods for a record's fields

    public void setID(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBuildingID(int building_id){
        this.building_id = building_id;
    }

    public void setFloorNumber(int floor_number){
        this.floor_number = floor_number;
    }
}
