package gustavo.brilhante.coinapp.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import gustavo.brilhante.coinapp.objects.Coin;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class JsonConversor {

    public static void setObjectFromJson(Object object, String json){
        StringBuilder sb = new StringBuilder();
        JSONObject jsonObj;
        Class<?> thisClass = null;
        try {
            thisClass = Class.forName(object.getClass().getName());// pega o nome da classe(nao usa)

            Field[] aClassFields = thisClass.getDeclaredFields(); // pega as variaveis da classe
            int i=0;
            jsonObj = new JSONObject(json);
            for(Field f : aClassFields){
                //filtra: apenas String, int, Integer, float e Double
                String aux = f.getName();
                if (jsonObj.has(f.getName())){
                    if(f.getType().isAssignableFrom(String.class)){
                        f.set(object, jsonObj.getString(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(Float.class)){
                        f.set(object, jsonObj.get(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(Double.class)){
                        f.setDouble(object, jsonObj.getDouble(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(double.class)){
                        f.setDouble(object, jsonObj.getDouble(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(int.class)){
                        String fieldName = f.getName();
                        int jsonValue = jsonObj.getInt(f.getName());
                        f.setInt(object, jsonObj.getInt(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(Integer.class)){
                        f.setInt(object, jsonObj.getInt(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(Boolean.class)){
                        f.setBoolean(object, jsonObj.getBoolean(f.getName()));
                        i++;
                    }else if(f.getType().isAssignableFrom(Date.class)){
                        f.set(object, jsonObj.getString(f.getName()));
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Coin> getCoins(String json){
        ArrayList<Coin> coinList = new ArrayList<Coin>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject valores;
            if(obj.has("valores")) {
                valores = obj.getJSONObject("valores");
                Iterator<?> keys = valores.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (valores.get(key) instanceof JSONObject) {
                        Coin coin = new Coin();
                        setObjectFromJson(coin, valores.getString(key));
                        coin.setKey(key);
                        coinList.add(coin);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return coinList;
    }

}
