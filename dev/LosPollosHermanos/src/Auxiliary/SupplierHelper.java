package Auxiliary;

import Auxiliary.Enums.Models.Suppliers.DeliveryDays;
import Auxiliary.Enums.Models.Suppliers.PaymentDeliveryTime;
import Auxiliary.Enums.Models.Suppliers.PaymentMethod;
import DTO.Suppliers.ContactPersonDTO;
import DTO.Suppliers.ContractDTO;
import DTO.Suppliers.ContractTermDTO;
import DTO.Suppliers.OrderItemDTO;
import Models.DBModels.suppliers.ContactPerson;
import Models.DBModels.suppliers.Supplier;
import Services.ContractsServices;

import java.util.ArrayList;
import java.util.Scanner;

public class SupplierHelper {

    public static int getIntegerFromUser(String message) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            System.out.println();
            int x ;
            try {
                x = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                continue;
            }
            return x ;
        }
    }

    public static int getIntegerFromUser(String message , int min) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            System.out.println();
            int x ;
            try {
                x = Integer.parseInt(input);
                if (x < min) {
                    System.out.println("please enter a number greater than " + min);
                    continue;
                }
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                continue;
            }
            return x ;
        }
    }

    public static int getIntegerFromUser(String message , int min , int max) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            int x ;
            try {
                x = Integer.parseInt(input);
                if (x > max || x < min) {
                    System.out.println("you entered an out of range number , enter between " + min + " and " + max);
                    continue;
                }
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                continue;
            }
            System.out.println();
            return x ;
        }
    }

    public static double getDoubleFromUser(String message , double min , double max) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            double x ;
            try {
                x = Double.parseDouble(input);
                if (x > max || x < min) {
                    System.out.println("you entered an out of range number , enter between " + min + " and " + max);
                    continue;
                }
            } catch (Exception e) {
                System.out.println("you didn't enter a number");
                continue;
            }
            System.out.println();
            return x ;
        }
    }

    public static String getNonEmptyStringFromUser(String message) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("")){
                System.out.println("please enter a non-empty string");
                continue;
            }
            System.out.println();
            return input;
        }
    }

    public static PaymentMethod getPaymentMethodFromUser(){
        System.out.println("Pick Payment Method : ");
        EnumConvertor.printPaymentMethodMenu();
        int x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3);
        return EnumConvertor.integerToPaymentMethod(x);
    }

    public static PaymentDeliveryTime getPaymentDeliveryTimeFromUser(){
        System.out.println("Pick Payment delivery time : ");
        EnumConvertor.printPaymentDeliveryTimeMenu();
        int x = SupplierHelper.getIntegerFromUser("Enter your choice : " , 1 , 3);
        return EnumConvertor.integerToPaymentDeliveryTime(x);
    }

    public static ArrayList<DeliveryDays> getDeliveryDaysFromUser(){
        ArrayList<DeliveryDays> deliveryDays = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (true) {
            deliveryDays = new ArrayList<>();
            System.out.println("Please Enter Delivery days seperated by ',' : ");
            String input = in.nextLine();
            if(input.equalsIgnoreCase("")) {
                System.out.println("a constant supplier cannot have an empty list of days");
                continue;
            }
            String[] parts = input.split(",");
            boolean success = true;
            for (String part : parts) {
                DeliveryDays res = EnumConvertor.stringToDeliveryDays(part);
                if (res == DeliveryDays.unDefined) {
                    System.out.println("Delivery Day is illegal");
                    success = false;
                    break;
                }
                deliveryDays.add(res);
            }
            if(success)
                break;
        }
        return deliveryDays;
    }

    public static String contactPeopleToString(ArrayList<ContactPersonDTO> contactPeople){
        String result = "" ;
        for(int i = 0 ; i < contactPeople.size() ; i++){
            result += i + 1 + ") " + contactPeople.get(i).toString() + "\n";
        }
        // if the result is empty, return a default message
        if(result.equalsIgnoreCase(""))
            return "no contact people" ;
        return result;
    }

    public static <T> ArrayList<T> removeIndexes(ArrayList<T> choices, int[] indexes){
        if(indexes == null)
            return choices ;
        ArrayList<T> result = new ArrayList<>();
        for(int i = 0 ; i < choices.size() ; i++){
            boolean found = false;
            for (int index : indexes) {
                if (index == i) {
                    found = true;
                    break;
                }
            }
            if(!found)
                result.add(choices.get(i));
        }
        return result;
    }

    public static String contactTermsArrayToString(ArrayList<ContractTermDTO> arr){
        String result = "" ;
        for(int i = 0 ; i < arr.size() ; i++){
            result += i + 1 + ") " + arr.get(i).toString() + "\n";
        }
        // if the result is empty, return a default message
        if(result.equalsIgnoreCase(""))
            return "no contract terms" ;
        return result;
    }

    public static void printContractList(ArrayList<ContractDTO> contractList){
        for (int i = 0; i < contractList.size(); i++) {
            System.out.print("contractDetails : ");
            System.out.println(contractList.get(i));
            ArrayList<ContractTermDTO> terms = ContractsServices.getContractTerms(contractList.get(i).getId()) ;
            if(terms == null) {
                System.out.println("something went wrong retrieving contracts terms");
            }else{
                System.out.println(SupplierHelper.contactTermsArrayToString(terms));
            }
            System.out.println("--------------------------------------------------");
        }
    }

    public static void printOrderItemList(ArrayList<OrderItemDTO> orderItemList){
        if(orderItemList == null)
            return;
        for (int i = 0; i < orderItemList.size(); i++) {
            System.out.println(i + 1 + ") " + orderItemList.get(i).toString());
        }
    }

}
