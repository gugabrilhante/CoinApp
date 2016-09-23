package gustavo.brilhante.coinapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gustavo.brilhante.coinapp.R;
import gustavo.brilhante.coinapp.interfaces.ItemClickListener;
import gustavo.brilhante.coinapp.objects.Coin;
import gustavo.brilhante.coinapp.utils.Utils;

/**
 * Created by Gustavo on 22/09/2016.
 */
public class CoinAdapter extends RecyclerView.Adapter {
    ArrayList<Coin> coinList;
    Context context;
    ItemClickListener listener;

    public CoinAdapter(ArrayList<Coin> coinList, Context context) {
        this.coinList = coinList;
        this.context = context;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Coin coin;

        ItemClickListener listener;

        final LinearLayout layout;
        final ImageView flagImageView;
        final TextView nomeTextView;
        final TextView valorTextView;
        final TextView ultimaConsultaTextView;
        final TextView fonteTextView;

        public CoinViewHolder(View view, ItemClickListener listener) {
            super(view);
            this.listener = listener;
            layout = (LinearLayout) view.findViewById(R.id.recycler_layout);
            layout.setOnClickListener(this);
            flagImageView = (ImageView) view.findViewById(R.id.recycler_cell_imageview);
            flagImageView.setOnClickListener(this);
            nomeTextView = (TextView) view.findViewById(R.id.recycler_cell_nome_textview);
            nomeTextView.setOnClickListener(this);
            valorTextView = (TextView) view.findViewById(R.id.recycler_cell_valor_textview);
            valorTextView.setOnClickListener(this);
            ultimaConsultaTextView = (TextView) view.findViewById(R.id.recycler_cell_ultima_consulta_textview);
            ultimaConsultaTextView.setOnClickListener(this);
            fonteTextView = (TextView) view.findViewById(R.id.recycler_cell_fonte_textview);
            fonteTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.coin = coinList.get(getLayoutPosition());
            listener.itemClicked(coin);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.coin_cell_recyclerview, parent, false);

        Coin coin = coinList.get(position);

        CoinViewHolder holder = new CoinViewHolder(view, this.listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CoinViewHolder viewHolder = (CoinViewHolder) holder;

        Coin coin = coinList.get(position);

        viewHolder.flagImageView.setImageDrawable(Utils.getFlagByKey(context, coin.getKey()));
        viewHolder.nomeTextView.setText(coin.getNome() + " - " + coin.getKey());
        viewHolder.valorTextView.setText("Cotação: " + coin.getValor());
        viewHolder.ultimaConsultaTextView.setText(""+ coin.getDateText());
        viewHolder.fonteTextView.setText(""+ coin.getFonteName());

    }



    @Override
    public int getItemCount() {
        return coinList.size();
    }



}
