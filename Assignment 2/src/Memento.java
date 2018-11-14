public class Memento {

    // String Array to store the information of each entity.
    private String [] characterInfo = new String[4];

    // Makes a deep copy of the string elements in the String Array.
    public void setState(String [] _charInfo){
        for(int i = 0; i <= _charInfo.length - 1; i++){
            characterInfo[i] = _charInfo[i];
        }
    }

    // returns the stored String Array.
    public String [] getState(){
        return this.characterInfo;
    }
}
