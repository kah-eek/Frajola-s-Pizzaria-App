package br.com.creativesoftwares.frajolaspizzaria.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.creativesoftwares.frajolaspizzaria.app.R;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Http;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Produto;
import br.com.creativesoftwares.frajolaspizzaria.app.model.ProdutoAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView listViewProdutos;
    private ProdutoAdapter produtoAdapter;

    private final Activity CONTEXT = this;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    startActivity(new Intent(CONTEXT, MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_maps:
//                    mTextMessage.setText(R.string.title_categories);
                    startActivity(new Intent(CONTEXT, MapsActivity.class));
                    return true;
            }
            return false;
        }

    };

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GETTING COMPONENTS' REFERENCES
        listViewProdutos = (ListView) findViewById(R.id.list_view_produtos);
        // = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // ARRAY LIST TO KEEP PRODUCTS LIST
        final ArrayList<Produto> produtosList = new ArrayList<>();

        /**
         * CONNECTING TO API - GETTING ACTIVE PRODUCTS
         */
        new AsyncTask<Void, Void, Void>(){

            // CONNECTING IT
            @Override
            protected Void doInBackground(Void... voids) {

                // URL TO ACCESS API WITH YOURS PARAMETERS
//                String jsonReturn = Http.get("http://10.0.2.2/inf3m/turmaA/Frajolas%20Pizzaria/api/products.php?action=getActiveProducts");
//                String jsonReturn = Http.get("http://10.0.2.2/site/Frajolas%20Pizzaria/api/products.php?action=getActiveProducts");
                String jsonReturn = Http.get("http://www.frajolaspizzaria.com.br/api/products.php?action=getActiveProducts");

                // LOG TO DEBUG
//                Log.d("TAG",jsonReturn);


                try{

                    JSONArray jsonArray = new JSONArray(jsonReturn);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Produto produto = new Produto(
                                getSharedPreferences(String.valueOf(jsonObject.getInt("idProduto")),MODE_PRIVATE),

                                jsonObject.getInt("idProduto"),
                                jsonObject.getString("imagemProduto"),
                                jsonObject.getString("descricao"),
                                jsonObject.getString("titulo"),
                                jsonObject.getString("categoria"),
                                jsonObject.getString("subcategoria"),
                                jsonObject.getDouble("preco"),
                                jsonObject.getDouble("avaliacao"));

                        // ADD NEW PRODUCT INTO ARRAY LIST
                        produtosList.add(produto);
                    }

                }catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // CREATE ADAPTER AND SET ARRAY LIST INTO ADAPTER
                produtoAdapter = new ProdutoAdapter(CONTEXT,produtosList);
                listViewProdutos.setAdapter(produtoAdapter);
            }
        }.execute();

        // CREATE ADAPTER AND SET ARRAY LIST INTO ADAPTER
//        produtoAdapter = new ProdutoAdapter(CONTEXT,produtosList);
//        listViewProdutos.setAdapter(produtoAdapter);


        // SHOW DETAILS ABOUT PRODUCT WAS CLICKED
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detalhesPizza = new Intent(CONTEXT, DetalhesPizzaActivity.class);

                // FORWARD OBJECT SELECTED TO NEXT ACTIVITY
                detalhesPizza.putExtra("itemId", produtoAdapter.getItem(i).getId());
                detalhesPizza.putExtra("itemCategoria", produtoAdapter.getItem(i).getCategoria());
                detalhesPizza.putExtra("itemImagem", produtoAdapter.getItem(i).getImagem());
                detalhesPizza.putExtra("itemPreco", produtoAdapter.getItem(i).getPreco());
                detalhesPizza.putExtra("itemDescricao", produtoAdapter.getItem(i).getDescricao());
                detalhesPizza.putExtra("itemTitulo", produtoAdapter.getItem(i).getTitulo());
                detalhesPizza.putExtra("itemSubcategoria", produtoAdapter.getItem(i).getSubcategoria());
                detalhesPizza.putExtra("itemAvaliacao", produtoAdapter.getItem(i).getAvaliacao());
                detalhesPizza.putExtra("itemAvaliado", produtoAdapter.getItem(i).getAvaliado());

                startActivity(detalhesPizza);
            }
        });



    }

}
