package br.com.creativesoftwares.frajolaspizzaria.app.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import br.com.creativesoftwares.frajolaspizzaria.app.R;
import br.com.creativesoftwares.frajolaspizzaria.app.dao.PizzariaDAO;
import br.com.creativesoftwares.frajolaspizzaria.app.dao.ProdutoDAO;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Http;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Pizzaria;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Produto;

public class DetalhesPizzaActivity extends AppCompatActivity {

    private final Activity CONTEXT = this;

    private TextView precoProduto;
    private TextView descricaoProduto;
    private RatingBar ratingBarProduto;
    private RatingBar ratingBarProdutoClassificacao;
    private CollapsingToolbarLayout detalhesActivity;
    private Button btnAvaliar;
    private static int permission = 0;

    private Produto item;
    private String itemCategoria;
    private int itemId;
    private String itemImagem;
    private double itemPreco;
    private String itemDescricao;
    private String itemTitulo;
    private String itemSubcategoria;
    private boolean itemAvaliado;
    private double itemAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pizza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // GETTING OBEJECT FROM PREVIOUS ACTIVITY
        Intent dataFromAnotherActivity = getIntent();

        itemCategoria = dataFromAnotherActivity.getStringExtra("itemCategoria");
        itemId = dataFromAnotherActivity.getIntExtra("itemId", -1);
        itemImagem = dataFromAnotherActivity.getStringExtra("itemImagem");
        itemPreco = dataFromAnotherActivity.getDoubleExtra("itemPreco", 0.0);
        itemDescricao = dataFromAnotherActivity.getStringExtra("itemDescricao");
        itemTitulo = dataFromAnotherActivity.getStringExtra("itemTitulo");
        itemSubcategoria = dataFromAnotherActivity.getStringExtra("itemSubcategoria");
        itemAvaliado = dataFromAnotherActivity.getBooleanExtra("itemAvaliado",false);
        itemAvaliacao = dataFromAnotherActivity.getDoubleExtra("itemAvaliacao", 0.0);

        // RECREATE PRODUCT OBJECT
        item = new Produto(getSharedPreferences(String.valueOf(itemId), MODE_PRIVATE), itemId, itemImagem, itemDescricao, itemTitulo, itemCategoria, itemSubcategoria, itemPreco, itemAvaliacao);
        // ****************************************************************************

        // GETTING FIELD REFERENCES
        precoProduto = (TextView) findViewById(R.id.txt_preco_produto);
        ratingBarProduto = (RatingBar) findViewById(R.id.rating_bar_detalhes_produto);
        ratingBarProdutoClassificacao = (RatingBar) findViewById(R.id.rating_bar_classificar_produto);
        descricaoProduto = (TextView) findViewById(R.id.txt_descricao_produto);
        detalhesActivity = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_detalhes);
        btnAvaliar = (Button) findViewById(R.id.btnSendDataAboutProduct);

        // CHECK IF ITEM WAS EVALUATED SOMETIME
        if (item.getAvaliado()){// DISABLE EVALUATE BUTTON AND RATING BAR
            ratingBarProdutoClassificacao.setEnabled(false);
            btnAvaliar.setEnabled(false);
        }



        // FILLING DATA INTO RESPECTIVE FIELDS
        precoProduto.setText(String.valueOf(item.getPreco()));
        ratingBarProduto.setRating((float) item.getAvaliacao());
        descricaoProduto.setText(item.getDescricao());
        detalhesActivity.setTitle(item.getTitulo());
//        setBackgroundPicture("http://10.0.2.2/inf3m/turmaA/Frajolas%20Pizzaria/"+item.getImagem().substring(6));
//        setBackgroundPicture("http://10.0.2.2/site/Frajolas%20Pizzaria/" + item.getImagem().substring(6));
        setBackgroundPicture("http://www.frajolaspizzaria.com.br/" + item.getImagem().substring(6));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                final Intent call = new Intent(Intent.ACTION_CALL);

                final ArrayList<Pizzaria> pizzarias = new ArrayList<>();
                final Random random = new Random();

                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {

//                        String jsonReturn = Http.get("http://10.0.2.2/site/Frajolas%20Pizzaria/api/pizzeria.php?action=getPizzeriaTelephones");
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

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        call.setData(Uri.parse("tel: "+pizzarias.get(random.nextInt(pizzarias.size())).getTelefone()));

                        Log.d("phone", String.valueOf(pizzarias.get(random.nextInt(pizzarias.size())).getTelefone()));

                        // CHECK PERMISSION
                        if (ActivityCompat.checkSelfPermission(CONTEXT, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                            // REQUEST PERMISSION
                            ActivityCompat.requestPermissions(CONTEXT, new String[]{Manifest.permission.CALL_PHONE}, permission);

                        }else {
                            startActivity(call);
                        }
                    }
                }.execute();

            }
        });


    }

    private void setBackgroundPicture(String picturePath){
        Picasso.with(CONTEXT).load(picturePath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                detalhesActivity.setBackground(new BitmapDrawable(CONTEXT.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {

            }

            @Override
            public void onPrepareLoad(Drawable drawable) {

            }
        });
    }

    public void evaluate(View view) {

        // GET INSTANCE FROM OBJECT THAT HAS ACCESS TO API
        ProdutoDAO produtoDAO = ProdutoDAO.getInstance();

        // SET EVALUATION INTO produto OBJECT
        item.setAvaliacao(ratingBarProdutoClassificacao.getRating());

        // DEBUG
        // Toast.makeText(CONTEXT, "--> avaliação = "+item.getAvaliacao()+", id = "+item.getId(), Toast.LENGTH_LONG).show();

        // SET EVALUATION INTO DATABASE
        produtoDAO.setEvaluate(item);

        // SHOW THANKS FOR FEEDBACK
        Toast.makeText(CONTEXT, "Obrigado por sua avaliação :)", Toast.LENGTH_LONG).show();

        Intent mainActivityIntent = new Intent(CONTEXT, MainActivity.class);

        mainActivityIntent.putExtra("itemId", item.getId());
        mainActivityIntent.putExtra("itemCategoria", item.getCategoria());
        mainActivityIntent.putExtra("itemImagem", item.getImagem());
        mainActivityIntent.putExtra("itemPreco", item.getPreco());
        mainActivityIntent.putExtra("itemDescricao", item.getDescricao());
        mainActivityIntent.putExtra("itemTitulo", item.getTitulo());
        mainActivityIntent.putExtra("itemSubcategoria", item.getSubcategoria());
        mainActivityIntent.putExtra("itemAvaliacao", item.getAvaliacao());
        mainActivityIntent.putExtra("itemAvaliado", item.getAvaliado());

        startActivity(mainActivityIntent);

    }
}
