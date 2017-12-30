package in.techfantasy.elauncher;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by campusiq on 30/12/17.
 */

public class SerializationTools {
    public static void serializeData(AppSerializableData obj){
        FileOutputStream fos;
        try{
            fos=MainActivity.activity.openFileOutput("data", Context.MODE_PRIVATE);
            ObjectOutputStream os=new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
        }
        catch (Exception e){

        }

    }

    public static AppSerializableData loadSerializableData(){
        ObjectInputStream inputStream=null;
        try{
            inputStream=new ObjectInputStream(MainActivity.activity.openFileInput("data"));
            Object obj=inputStream.readObject();

            if(obj instanceof AppSerializableData) {
                return (AppSerializableData) obj;
            }else{
                return null;
            }


        }catch (Exception e){
            Log.e("From SerializationTools",e.getMessage());
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }catch (Exception e){
                Log.e("From SerializationTools",e.getMessage());
            }
        }
        return null;
    }
}
