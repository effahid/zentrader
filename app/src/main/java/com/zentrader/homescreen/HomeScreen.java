package com.zentrader.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zentrader.instrumentdetail.InstrumentDetailActivity;
import com.zentrader.R;
import com.zentrader.addinstrument.Stock;
import com.zentrader.addinstrument.AddInstrumentActivity;

import java.util.ArrayList;
import java.util.Random;

public class HomeScreen extends AppCompatActivity {
    private static final int ADD_INSTRUMENT_ACTIVITY_RESULT_CODE = 1;
    final Handler handler = new Handler();
    private ArrayList<Stock> stockPortfolio = new ArrayList<>();
    InstrumentRowAdapter listAdapter;
    Runnable runnable;
    ListView listView;
    boolean beganUpdating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //ButterKnife.bind(this);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);

        listAdapter = new InstrumentRowAdapter(this, stockPortfolio);

        listView = (ListView) findViewById(R.id.MyList);
        listView.setAdapter(listAdapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                UpdateAdapter(listAdapter);
                handler.postDelayed(this, 3000);
            }
        };

       // handler.postDelayed(runnable, 1000);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent openInstrumentDetail = new Intent(getBaseContext(), InstrumentDetailActivity.class);

                startActivity(openInstrumentDetail);
            }
        });

    }


    public void UpdateAdapter(InstrumentRowAdapter listAdapter) {
        Random random = new Random();
        for(Stock stock:stockPortfolio )
        {
            stock.Buy= random.nextFloat()+1200;
            stock.Sell= random.nextFloat()+1190;
            stock.Movement="U";
        }
        listAdapter.stockData = stockPortfolio;
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    public void ShowAddInstrument(MenuItem item) {
        Intent addInstrumentIntent = new Intent(getBaseContext(), AddInstrumentActivity.class);
        ArrayList<Stock> selectedStocks = new ArrayList<>();
        for(Stock stock:stockPortfolio)
        {
            selectedStocks.add(stock);
        }
        addInstrumentIntent.putExtra("SelectedStocks", selectedStocks);

       startActivityForResult(addInstrumentIntent, ADD_INSTRUMENT_ACTIVITY_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_INSTRUMENT_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {



                // get String data from Intent
                ArrayList<String> addedStocks = data.getStringArrayListExtra("SelectedStocks");
                for(String addedStock:addedStocks)
                {
                    stockPortfolio.add(new Stock(addedStock,addedStock));
                }
                listAdapter.notifyDataSetChanged();

                if(!beganUpdating){
                    beganUpdating=true;
                    handler.postDelayed(runnable, 2000);
                }
            }
        }
    }
}

