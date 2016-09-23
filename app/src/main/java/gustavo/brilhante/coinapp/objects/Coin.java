package gustavo.brilhante.coinapp.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import gustavo.brilhante.coinapp.json.ObjectJson;

/**
 * Created by Gustavo on 22/09/2016.
 */
public class Coin extends ObjectJson{
    String key;
    String nome;
    double valor;
    int ultima_consulta;
    String fonte;

    public Coin() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getUltima_consulta() {
        return ultima_consulta;
    }

    public void setUltima_consulta(int ultima_consulta) {
        this.ultima_consulta = ultima_consulta;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getDateText(){
        String dateAsText = new SimpleDateFormat("dd/MM/yyyy").format(new Date(ultima_consulta * 1000L)) + " as "+
                new SimpleDateFormat("HH:mm:ss").format(new Date(ultima_consulta * 1000L));

        return dateAsText;
    }

    public String getFonteName(){
        String result = fonte;

        if(result.contains("-")){
            result = fonte.split("-")[0];
        }

        return result;
    }

    public String getFonteSite(){
        String result = fonte;

        if(result.contains("-")){
            result = fonte.split("-")[1];
        }

        return result;
    }
}
