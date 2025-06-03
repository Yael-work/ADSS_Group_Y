package Auxiliary;

import Auxiliary.Enums.Models.Suppliers.*;

import java.util.ArrayList;

public class EnumConvertor {

    public static String enumToString(DeliveryDays deliveryDay) {
        return switch(deliveryDay){
            case sunday -> "Sunday";
            case monday -> "Monday";
            case tuesday -> "Tuesday";
            case wednesday -> "Wednesday";
            case thursday -> "Thursday";
            case friday -> "Friday";
            case saturday -> "Saturday";
            default -> "Undefined";
        };
    }

    public static String enumToString(SupplierType supplierType) {
        return switch(supplierType){
            case SupplierType.constant -> "constant";
            case SupplierType.nonConstant -> "nonConstant";
            default -> {throw new RuntimeException("a non-handled supplier type");}
        };
    }
    public static String enumToString(PaymentMethod paymentMethod){
        return switch(paymentMethod){
            case PaymentMethod.creditCard -> "CreditCard";
            case PaymentMethod.cash -> "Cash";
            case PaymentMethod.bankTransfer -> "Bank Transfer";
            case PaymentMethod.unDefined -> "Undefined";
            default -> {throw new RuntimeException("a non-handled payment delivery time");}
        };
    }

    public static String enumToString(PaymentDeliveryTime paymentDeliveryTime){
        switch(paymentDeliveryTime){
            case PaymentDeliveryTime.afterOrder -> {return "After Order";}
            case PaymentDeliveryTime.monthly -> {return "Monthly";}
            case PaymentDeliveryTime.onOrderReceived -> {return "On Order Received";}
            case PaymentDeliveryTime.unDefined -> {return "Undefined";}
            default -> {throw new RuntimeException("a non-handled payment delivery time");}
        }
    }

    public static String enumToString(OrderStatus orderStatus){
        switch(orderStatus){
            case OrderStatus.onTheWay -> {return "On Way";}
            case OrderStatus.received -> {return "Received";}
            case OrderStatus.submitted -> {return "Submitted";}
            case OrderStatus.unDefined -> {return "Undefined";}
            default -> {throw new RuntimeException("a non-handled order status");}
        }
    }

    public static OrderStatus stringToOrderStatus(String orderStatus){
        switch(orderStatus){
            case "On Way" -> {return OrderStatus.onTheWay;}
            case "Received" -> {return OrderStatus.received;}
            case "Submitted" -> {return OrderStatus.submitted;}
            case "Undefined" -> {return OrderStatus.unDefined;}
            default -> {throw new RuntimeException("a non-handled order status");}
        }
    }

    public static DeliveryDays stringToDeliveryDays(String day) {
        switch(day.toLowerCase()){
            case "sunday" -> {return DeliveryDays.sunday ;}
            case "monday" -> {return DeliveryDays.monday ;}
            case "tuesday" -> {return DeliveryDays.tuesday ;}
            case "wednesday" -> {return DeliveryDays.wednesday ;}
            case "thursday" -> {return DeliveryDays.thursday ;}
            case "friday" -> {return DeliveryDays.friday ;}
            case "saturday" -> {return DeliveryDays.saturday ;}
            default -> {return DeliveryDays.unDefined ;}
        }
    }

    public static PaymentMethod stringToPaymentMethod(String str) {
        switch(str){
            case "Bank Transfer" -> {return PaymentMethod.bankTransfer ;}
            case "CreditCard" -> {return PaymentMethod.creditCard ;}
            case "Cash" -> {return PaymentMethod.cash ;}
            default -> {throw new RuntimeException("not a legal payment method");}
        }
    }

    public static PaymentDeliveryTime stringToPaymentDeliveryTime(String str) {
        switch(str){
            case "After Order" -> {return PaymentDeliveryTime.afterOrder ;}
            case "Monthly" -> {return PaymentDeliveryTime.monthly ;}
            case "On Order Received" -> {return PaymentDeliveryTime.onOrderReceived ;}
            default -> {throw new RuntimeException("not a legal payment delivery time");}
        }
    }

    public static PaymentMethod integerToPaymentMethod(int x) {
        switch(x){
            case 1 -> {return PaymentMethod.creditCard;}
            case 2 -> {return PaymentMethod.bankTransfer;}
            case 3 -> {return PaymentMethod.cash;}
            default -> {return PaymentMethod.unDefined;}
        }
    }

    public static PaymentDeliveryTime integerToPaymentDeliveryTime(int x) {
        switch(x){
            case 1 -> {return PaymentDeliveryTime.monthly;}
            case 2 -> {return PaymentDeliveryTime.afterOrder;}
            case 3 -> {return PaymentDeliveryTime.onOrderReceived;}
            default -> {return PaymentDeliveryTime.unDefined;}
        }
    }

    public static OrderStatus integerToOrderStatus(int x) {
        switch (x){
            case 1 -> {return OrderStatus.submitted;}
            case 2 -> {return OrderStatus.onTheWay;}
            case 3 -> {return OrderStatus.received;}
            default -> {return OrderStatus.unDefined;}
        }
    }

    public static void printPaymentDeliveryTimeMenu(){
        System.out.println("1. monthly payment");
        System.out.println("2. after order is reachieved");
        System.out.println("3. when ordering");
    }

    public static void printOrderStatusMenu(){
        System.out.println("1. submitted");
        System.out.println("2. on the way");
        System.out.println("3. received");
    }

    public static void printPaymentMethodMenu(){
        System.out.println("1. credit card");
        System.out.println("2. bank transfer");
        System.out.println("3. cash");
    }

    public static ArrayList<DeliveryDays> stringToDeliveryDaysArray(String str){
        if(str == null || str.length() == 0)
            return new ArrayList<>();

        String[] days = str.split(",");
        ArrayList<DeliveryDays> daysList = new ArrayList<>();
        for(String day : days){
            daysList.add(stringToDeliveryDays(day));
        }
        return daysList;
    }
    public static String deliveryDaysToString(ArrayList<DeliveryDays> daysList){
        String result = "" ;
        for(int i = 0 ;i < daysList.size(); i++){
            DeliveryDays day = daysList.get(i);
            if(i == daysList.size()-1)
                result += enumToString(day) ;
            else
                result += enumToString(day) + ",";
        }
        return result ;
    }

    public static SupplierType stringToSupplierType(String str){
        switch(str){
            case "constant" -> {return SupplierType.constant ;}
            case "nonConstant" -> {return SupplierType.nonConstant ;}
            default -> {throw new RuntimeException("not a legal supplier type");}
        }
    }
}
