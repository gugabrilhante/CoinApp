package gustavo.brilhante.coinapp.database.create;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gustavo on 22/09/2016.
 */
public class CoinDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "coinsDatabase.db";
    public static final String TABLE_NAME = "coin_table";

    public static final String ID_KEY = "id";
    public static final String CHAVE_KEY = "chave";
    public static final String NOME_KEY = "nome";
    public static final String VALOR_KEY = "valor";
    public static final String CONSULTA_KEY = "consulta";
    public static final String FONTE_KEY = "fonte";

    public static final int DATABASE_VERSION = 1;

    public CoinDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();

        sql.append("create table "+TABLE_NAME+" (");
        sql.append(ID_KEY + " integer primary key autoincrement,");
        sql.append(CHAVE_KEY+" text,");
        sql.append(NOME_KEY+" text,");
        sql.append(VALOR_KEY+" double,");
        sql.append(CONSULTA_KEY+" integer,");
        sql.append(FONTE_KEY+" text)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(db);
    }
}
