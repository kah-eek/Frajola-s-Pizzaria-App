package br.com.creativesoftwares.frajolaspizzaria.app.dao;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import br.com.creativesoftwares.frajolaspizzaria.app.model.Http;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Produto;

/**
 * Created by Caique M. Oliveira on 12/9/2017.
 */

public class ProdutoDAO {

    private static ProdutoDAO produtoDAO;

    /**
     * SINGLETON
     * @return AN ProdutoDAO OBJECT
     */
    public static ProdutoDAO getInstance(){
        if (produtoDAO == null){
            return produtoDAO = new ProdutoDAO();
        }else return produtoDAO;
    }

    @SuppressLint("StaticFieldLeak")
    public void setEvaluate(final Produto produtoObj){


        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

//                String evaluationResponse = Http.get("http://10.0.2.2/site/Frajolas%20Pizzaria/api/products.php?action=evaluate&id="+produtoObj.getId()+"&evaluation="+produtoObj.getAvaliacao());
                String evaluationResponse = Http.get("http://www.frajolaspizzaria.com.br/api/products.php?action=evaluate&id="+produtoObj.getId()+"&evaluation="+produtoObj.getAvaliacao());
                Log.d("web response", evaluationResponse);
                return null;
            }
        }.execute();

    }

}
