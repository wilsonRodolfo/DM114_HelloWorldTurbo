package br.com.wilson.helloworldturbo.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.wilson.helloworldturbo.R;
import br.com.wilson.helloworldturbo.models.Pedido;

public class PedidoAdapter extends BaseAdapter {

    private final Activity activity;
    List<Pedido> pedidos;

    public PedidoAdapter(Activity activity, List<Pedido> pedidos) {
        this.activity = activity;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }
    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getOrderId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.pedido_list_item, null);

        Pedido pedido = pedidos.get(position);

        TextView pedidoListItemNumber = (TextView) view.findViewById(R.id.pedidoListItemNumber);
        pedidoListItemNumber.setText(Integer.toString(position + 1));

        TextView pedidoListItemId = (TextView) view.findViewById(R.id.pedidoListItemId);
        pedidoListItemId.setText(Integer.toString(pedido.getOrderId()));

        TextView pedidoListItemDate = (TextView) view.findViewById(R.id.pedidoListItemDate);
        pedidoListItemDate.setText(pedido.getDataPedido());

        return view;
    }
}