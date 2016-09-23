package gustavo.brilhante.coinapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gustavo.brilhante.coinapp.R;
import gustavo.brilhante.coinapp.json.JsonConversor;
import gustavo.brilhante.coinapp.objects.Coin;
import gustavo.brilhante.coinapp.utils.Utils;

public class InfoActivity extends AppCompatActivity {

    ImageView flagImageview;

    TextView nameTextview, valorTextView, ultimaConsultaTextview, fonteTextView;

    int displayWidth;

    String coinStr;
    Coin coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        flagImageview = (ImageView) findViewById(R.id.infoactivity_imageview);

        nameTextview = (TextView) findViewById(R.id.infoactivity_nome_textview);
        valorTextView = (TextView) findViewById(R.id.infoactivity_valor_textview);
        ultimaConsultaTextview = (TextView) findViewById(R.id.infoactivity_ultima_consulta_textview);
        fonteTextView = (TextView) findViewById(R.id.infoactivity_fonte_textview) ;

        coinStr = getIntent().getExtras().getString("COIN");
        coin = new Coin();
        JsonConversor.setObjectFromJson(coin, coinStr);

        Display display = getWindowManager().getDefaultDisplay();
        displayWidth = display.getWidth();

        flagImageview.setImageDrawable(Utils.getFlagByKey(InfoActivity.this,coin.getKey()));
        flagImageview.getLayoutParams().width = displayWidth*5/7;
        flagImageview.getLayoutParams().height = displayWidth*5/7;

        nameTextview.setText(coin.getNome() + " - " + coin.getKey());
        valorTextView.setText("Cotação: " + coin.getValor());
        ultimaConsultaTextview.setText(""+ coin.getDateText());
        fonteTextView.setText(""+ coin.getFonteSite());
        fonteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(coin.getFonteSite());
            }
        });

    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
