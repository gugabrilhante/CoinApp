package gustavo.brilhante.coinapp.database.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import gustavo.brilhante.coinapp.database.create.CoinDatabase;
import gustavo.brilhante.coinapp.objects.Coin;

/**
 * Created by Gustavo on 22/09/2016.
 */
public class CoinController {
    CoinDatabase coinDatabase;
    Context context;

    public CoinController(Context context) {
        this.context = context;
        this.coinDatabase = new CoinDatabase(context);
    }

    public void insertCoin (Coin coin){
        SQLiteDatabase db = coinDatabase.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CoinDatabase.CHAVE_KEY, coin.getKey());
        contentValues.put(CoinDatabase.NOME_KEY, coin.getNome());
        contentValues.put(CoinDatabase.VALOR_KEY, coin.getValor());
        contentValues.put(CoinDatabase.CONSULTA_KEY, coin.getUltima_consulta());
        contentValues.put(CoinDatabase.FONTE_KEY, coin.getFonte());

        db.insert(CoinDatabase.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Coin> getListaCompleta(){
        ArrayList<Coin> lista = new ArrayList<Coin>();

        String selectQuery = "SELECT  * FROM " + CoinDatabase.TABLE_NAME;
        SQLiteDatabase db = coinDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Coin coin = new Coin();
                coin.setKey(cursor.getString(1));
                coin.setNome(cursor.getString(2));
                coin.setValor(cursor.getDouble(3));
                coin.setUltima_consulta(cursor.getInt(4));
                coin.setFonte(cursor.getString(5));

                lista.add(coin);

            } while (cursor.moveToNext());
        }
        db.close();

        return lista;
    }

    public void clearTable(){
        SQLiteDatabase db = coinDatabase.getReadableDatabase();
        db.delete(CoinDatabase.TABLE_NAME, null, null);
        db.close();
    }
}
