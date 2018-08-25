package br.com.wilson.helloworldturbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.wilson.helloworldturbo.R;
import br.com.wilson.helloworldturbo.models.Order;
import br.com.wilson.helloworldturbo.tasks.OrderEvents;
import br.com.wilson.helloworldturbo.tasks.OrderTasks;
import br.com.wilson.helloworldturbo.util.CheckNetworkConnection;
import br.com.wilson.helloworldturbo.webservice.WebServiceResponse;

public class OrderDetailFragment extends Fragment implements OrderEvents {
    private TextView txtId;
    private TextView txtEmail;
    private TextView txtFrete;
    private TextView txtQuantidadeItens;

    public OrderDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);

        getActivity().setTitle("Order Details");

        txtId = rootView.findViewById(R.id.txtId);
        txtEmail = rootView.findViewById(R.id.txtEmail);
        txtFrete = rootView.findViewById(R.id.txtFrete);
        txtQuantidadeItens = rootView.findViewById(R.id.txtQuantidade);

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);

            Bundle bundle = this.getArguments();
            if ((bundle != null) && (bundle.containsKey("order_id"))) {
                long orderId = bundle.getLong("order_id");
                orderTasks.getOrderById(orderId);
            }
        }

        return rootView;
    }

    @Override
    public void getOrderByIdFinished(Order order) {
        txtId.setText(String.valueOf(order.getId()));
        txtEmail.setText((order.getEmail()));
        txtFrete.setText(String.valueOf(order.getFreightPrice()));
        txtQuantidadeItens.setText(String.valueOf(order.getOrderItems().size()));
    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta do pedido" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {
    }
    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
    }
}
