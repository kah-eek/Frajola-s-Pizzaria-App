package br.com.creativesoftwares.frajolaspizzaria.app.dao;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.creativesoftwares.frajolaspizzaria.app.model.Http;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Pizzaria;

/**
 * Created by Caique M. Oliveira on 12/10/2017.
 */

public class PizzariaDAO {

    private static PizzariaDAO pizzariaDAO;

    /***
     * SINGLETON
     * @return AN PizzariaDAO OBJECT
     */
    public static PizzariaDAO getInstance(){
        if (pizzariaDAO == null) return pizzariaDAO = new PizzariaDAO();
        else return pizzariaDAO;
    }


    @SuppressLint("StaticFieldLeak")
    public ArrayList<Pizzaria> getTelefones(){

        final ArrayList<Pizzaria> pizzarias = new ArrayList<>();

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

//                String jsonReturn = Http.get("http://10.0.2.2/site/Frajolas%20Pizzaria/api/pizzeria.php?action=getPizzeriaTelephones");
                String jsonReturn = Http.get("http://www.frajolaspizzaria.com.br/api/pizzeria.php?action=getPizzeriaTelephones");

                Log.d("json", jsonReturn);

                try{

                    JSONArray jsonArray = new JSONArray(jsonReturn);

                    for (int i = 0; i< jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Pizzaria pizzaria = new Pizzaria();

                        pizzaria.setTelefone(jsonObject.getString("telefone"));

                        pizzarias.add(pizzaria);
                    }


                }catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }

                return null;
            }


        }.execute();

        return pizzarias;

    }



}
