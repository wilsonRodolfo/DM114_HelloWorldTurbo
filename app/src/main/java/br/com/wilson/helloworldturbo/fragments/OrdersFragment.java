package br.com.wilson.helloworldturbo.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.wilson.helloworldturbo.R;
import br.com.wilson.helloworldturbo.adapters.OrderAdapter;
import br.com.wilson.helloworldturbo.models.Order;
import br.com.wilson.helloworldturbo.tasks.OrderEvents;
import br.com.wilson.helloworldturbo.tasks.OrderTasks;
import br.com.wilson.helloworldturbo.util.CheckNetworkConnection;
import br.com.wilson.helloworldturbo.webservice.WebServiceResponse;

public class OrdersFragment extends Fragment implements OrderEvents {
    private static String STATE_LIST_ORDERS_TEXT = "list_orders";

    private ListView listViewOrders;
    private List<Order> orders;

    public OrdersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders_list, container, false);

        setHasOptionsMenu(true);

        getActivity().setTitle("Orders");
        listViewOrders = (ListView) rootView.findViewById(R.id.orders_list);

        listViewOrders.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startOrderDetail(id);
                    }
                });

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST_ORDERS_TEXT)) {
            Gson gson = new Gson();

            orders = gson.fromJson(savedInstanceState.getString(STATE_LIST_ORDERS_TEXT), new TypeToken<List<Order>>() {}.getType());
            loadListOrders();
        } else if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            orderTasks.getOrders();
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.orders_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload_orders:
                if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
                    orders.clear();
                    loadListOrders();
                    OrderTasks orderTasks = new OrderTasks(getActivity(), this);
                    orderTasks.getOrders();
                    Toast.makeText(getActivity(), "Realoading...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Sem conexão, impossivel recarregar...", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startOrderDetail (long orderId) {
        Class fragmentClass;
        Fragment fragment = null;
        fragmentClass = OrderDetailFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();

            if (orderId >= 0) {
                Bundle args = new Bundle();
                args.putLong("order_id", orderId);
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment,
                    OrderDetailFragment.class.getCanonicalName());

            transaction.addToBackStack(OrdersFragment.class.getCanonicalName());
            transaction.commit();

        } catch (Exception e) {
            try {
                Toast.makeText(getActivity(),
                        "Erro ao tentar abrir detalhes do pedido",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e1) {}
        }
    }

    private void loadListOrders() {
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), orders);
        listViewOrders.setAdapter(orderAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();

        outState.putString(STATE_LIST_ORDERS_TEXT, gson.toJson(orders));
        super.onSaveInstanceState(outState);
    }


    @Override
    public void getOrdersFinished(List<Order> orders) {
        this.orders = orders;
        loadListOrders();
    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de pedidos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOrderByIdFinished(Order order) { }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) { }
}
