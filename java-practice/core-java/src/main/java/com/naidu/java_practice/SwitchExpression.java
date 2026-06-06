package com.naidu.java_practice;
enum PaymentStatus {
    UNPAID, PARTPAID, PAID, DISPUTED, UNKNOWN;
}

public class SwitchExpression {
    public static void main(String[] args) {
        PaymentStatus paymentStatus = PaymentStatus.PARTPAID;

        String message = switch (paymentStatus) {
        case UNPAID -> "The order has not been paid yet. Please make the minimum/full amount to procced.";
        case PARTPAID -> "The order is partially paid. Some features will not be available. Please check the brochure for details.";
        case PAID -> "The order is fully paid. Please choose the desired items from the menu.";
        default -> throw new IllegalStateException("Invalid payment status: " + paymentStatus);
        };

        System.out.println(message);
    }
}