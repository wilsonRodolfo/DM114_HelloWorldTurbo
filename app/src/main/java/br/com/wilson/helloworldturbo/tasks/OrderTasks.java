package br.com.wilson.helloworldturbo.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.wilson.helloworldturbo.models.Order;
import br.com.wilson.helloworldturbo.util.WSUtil;
import br.com.wilson.helloworldturbo.webservice.WebServiceClient;
import br.com.wilson.helloworldturbo.webservice.WebServiceResponse;

public class OrderTasks {
    private static final String GET_ORDERS = "/api/orders";
    private static final String GET_ORDER_BY_ID = "/api/orders";

    private OrderEvents orderEvents;
    private Context context;
    private String baseAddress;

    public OrderTasks(Context context, OrderEvents orderEvents) {
        String host;
        int port;

        this.context = context;
        this.orderEvents = orderEvents;
        baseAddress = WSUtil.getHostAddress(context);
    }

    @SuppressLint("StaticFieldLeak")
    public void getOrders() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceClient.get(context, baseAddress + GET_ORDERS);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<Order> orders = gson.fromJson(webServiceResponse.getResultMessage(), new TypeToken<List<Order>>() {}.getType());
                        orderEvents.getOrdersFinished(orders);
                    } catch (Exception e) {
                        orderEvents.getOrdersFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrdersFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void getOrderById(long id) {
        new AsyncTask<Long, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Long... id) {
                return WebServiceClient.get(context, baseAddress + GET_ORDER_BY_ID + "/" + Long.toString(id[0]));
            }
            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Order order = gson.fromJson(webServiceResponse.getResultMessage(), Order.class);
                        orderEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        orderEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrderByIdFailed(webServiceResponse);
                }
            }
        }.execute(id, null, null);
    }
}
