package com.zentrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AvailableInstrumentsAdapter extends ArrayAdapter<Stock> {

    Stock[] stockData;
    public AvailableInstrumentsAdapter(Context context, Stock[] values) {
        super(context, -1, values);
        stockData=values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.available_instruments, parent, false);
        TextView instrumentSymbol = (TextView) rowView.findViewById(R.id.instrument_symbol);
        TextView instrumentName = (TextView) rowView.findViewById(R.id.instrument_name);
        instrumentSymbol.setText(stockData[position].Symbol);
        instrumentName.setText(stockData[position].Name);
        return rowView;
    }
}
