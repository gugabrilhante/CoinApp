package gustavo.brilhante.coinapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import gustavo.brilhante.coinapp.R;

/**
 * Created by Gustavo on 22/09/2016.
 */
public class Utils {

    public static Drawable getFlagByKey(Context context,String key){
        Drawable drawable;
        if(key.equals("USD")) {
            drawable = context.getResources().getDrawable(R.drawable.united_states);

        }else if (key.equals("EUR")){
            drawable = context.getResources().getDrawable(R.drawable.european_union);

        }else if (key.equals("ARS")){
            drawable = context.getResources().getDrawable(R.drawable.argentina);

        }else if (key.equals("GBP")){
            drawable = context.getResources().getDrawable(R.drawable.united_kingdom);

        }else{
            drawable = context.getResources().getDrawable(R.drawable.bitcoin);

        }
        return drawable;
    }

}
