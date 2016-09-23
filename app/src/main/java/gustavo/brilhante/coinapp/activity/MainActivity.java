package gustavo.brilhante.coinapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import gustavo.brilhante.coinapp.R;
import gustavo.brilhante.coinapp.adapter.CoinAdapter;
import gustavo.brilhante.coinapp.database.controller.CoinController;
import gustavo.brilhante.coinapp.interfaces.ItemClickListener;
import gustavo.brilhante.coinapp.interfaces.JsonCallback;
import gustavo.brilhante.coinapp.json.JsonConversor;
import gustavo.brilhante.coinapp.objects.Coin;
import gustavo.brilhante.coinapp.service.GenericGetAsyncRequest;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    public static final String MAIN_URL = "http://api.promasters.net.br/cotacao/v1/valores";


    RecyclerView recyclerView;

    ArrayList<Coin> coinList = new ArrayList<Coin>();

    SwipeRefreshLayout swipeRefreshLayout;

    CoinController database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.mainactivity_coins_recyclerview);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainactivity_swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        database = new CoinController(MainActivity.this);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        coinList = database.getListaCompleta();
        if(coinList.isEmpty()) {
            refreshItems();
        }else{
            setRecyclerViewAdapter();
        }
    }

    private void refreshItems() {
        JsonCallback callback = new JsonCallback() {
            @Override
            public void callback(ArrayList<String> dataDownloaded) {
                if(dataDownloaded.size()>0){
                    coinList = JsonConversor.getCoins(dataDownloaded.get(0));
                    if(!coinList.isEmpty()){
                        database.clearTable();
                        insertCoinList();
                        setRecyclerViewAdapter();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        GenericGetAsyncRequest service = new GenericGetAsyncRequest(MainActivity.this, callback);
        service.setShowDialog(false);
        service.execute(MAIN_URL);
    }

    void insertCoinList(){
        for(int i=0; i<coinList.size(); i++){
            database.insertCoin(coinList.get(i));
        }
    }

    void setRecyclerViewAdapter(){
        CoinAdapter adapter = new CoinAdapter(coinList, MainActivity.this);
        adapter.setListener(MainActivity.this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void itemClicked(Coin coin) {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("COIN", coin.toString());
        startActivity(intent);
    }
}
