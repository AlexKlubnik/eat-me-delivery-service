package by.klubnikov.eatmedelivery.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

    RECEIVED("Your order has been received and sent to processing"),
    COOKING("Your order is cooking now"),
    INDELIVERY("The courier is on his way to you"),
    DELIVERED("Your order was delivered successfully"),
    CANCELED("Order was canceled");

    private String orderTitle;

    @Override
    public String toString() {
        return orderTitle;
    }
}
