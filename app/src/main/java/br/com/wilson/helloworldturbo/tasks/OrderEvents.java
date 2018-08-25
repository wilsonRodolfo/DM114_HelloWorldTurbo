package br.com.wilson.helloworldturbo.tasks;

import java.util.List;

import br.com.wilson.helloworldturbo.models.Order;
import br.com.wilson.helloworldturbo.webservice.WebServiceResponse;

public interface OrderEvents {
    void getOrdersFinished(List<Order> orders);
    void getOrdersFailed(WebServiceResponse webServiceResponse);

    void getOrderByIdFinished(Order order);
    void getOrderByIdFailed(WebServiceResponse webServiceResponse);
}
