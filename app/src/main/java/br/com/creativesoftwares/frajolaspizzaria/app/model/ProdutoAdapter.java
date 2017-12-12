package br.com.creativesoftwares.frajolaspizzaria.app.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.creativesoftwares.frajolaspizzaria.app.R;

/**
 * Created by 16254840 on 28/11/2017.
 */

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    public ProdutoAdapter(Context context, List<Produto> itens){
        super(context, 0, itens);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        // INFLATE VIEW CASE IT NOT EXISTS
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_view_produtos_item, null);
        }

        // GET OBJECT FROM LIST
        Produto item = getItem(position);

        // GETTING FIELDS
        TextView txtTituloProduto = view.findViewById(R.id.txt_titulo_produto);
        ImageView imgProduto = view.findViewById(R.id.image_view_produto);
        TextView txtPrecoProduto = view.findViewById(R.id.txt_preco_produto);
        TextView txtDescricaoProduto = view.findViewById(R.id.txt_descricao_produto);
        RatingBar ratingBarProduto = view.findViewById(R.id.rating_bat_produto);

        // DEBUG
        //Log.d("PATH PICTURE:", "http://10.0.2.2/inf3m/turmaA/Frajolas%20Pizzaria/"+item.getImagem().substring(6));

        // SETTING DATA INTO TEXT FIELDS
        txtTituloProduto.setText(item.getTitulo());
        txtDescricaoProduto.setText(item.getDescricao());
//        Picasso.with(getContext()).load("http://10.0.2.2/inf3m/turmaA/Frajolas%20Pizzaria/"+item.getImagem().substring(6)).into(imgProduto);
//        Picasso.with(getContext()).load("http://10.0.2.2/site/Frajolas%20Pizzaria/"+item.getImagem().substring(6)).into(imgProduto);
        Picasso.with(getContext()).load("http://www.frajolaspizzaria.com.br/"+item.getImagem().substring(6)).into(imgProduto);
        txtPrecoProduto.setText(String.valueOf(item.getPreco()));
        ratingBarProduto.setRating((float)item.getAvaliacao());


        return view;
    }


}
