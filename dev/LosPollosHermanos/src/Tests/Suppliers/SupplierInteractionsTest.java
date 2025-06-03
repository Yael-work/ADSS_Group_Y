import Auxiliary.Enums.Models.Suppliers.*;
import DB.DAO.Suppliers.*;
import DB.base.DatabaseSQL;
import DTO.Suppliers.*;
import Domain.suppliers.ContractRepository;
import Domain.suppliers.IContractRepository;
import Domain.suppliers.IOrdersRepository;
import Domain.suppliers.OrderRepository;
import Models.DBModels.suppliers.*;
import Models.DBModels.suppliers.Order;
import Services.SuppliersServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SupplierInteractionsTest {
    private static String databaseName = "jdbc:sqlite:C:\\Users\\moham\\IdeaProjects\\untitled5\\src\\main\\resources\\testDB.db";
    static SupplierDAO supplierDAO = new SupplierDAO_SQL();
    static ContactPersonDAO contactPersonDAO = new ContactPersonDAO_SQL() ;
    static ContractTermDAO contractTermDAO = new ContractTermDAO_SQL() ;
    static ContractDAO contractDAO = new ContractDAO_SQL() ;
    static IContractRepository contractRepository = ContractRepository.getInstance() ;
    static IOrdersRepository ordersRepository = OrderRepository.getInstance() ;


    @BeforeEach
    void setUp() {
        DatabaseSQL.DB_URL = databaseName ;
        DatabaseSQL.cleanTables();
        DatabaseSQL.createTables();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testSupplierInsertionAndRetrieval() {
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        ArrayList<DeliveryDays> days = new ArrayList<>() ;
        days.add(DeliveryDays.sunday) ;
        days.add(DeliveryDays.friday) ;
        supplierDAO.addSupplier(new SupplierDTO("supplier1" , "address1" , "0581" , "1" , paymentTerms1 , SupplierType.nonConstant , days)) ;
        SupplierDTO supplier = supplierDAO.getSupplier("1") ;
        assertEquals("supplier1", supplier.getName(), "assert1 failed" ) ;
        assertEquals("address1", supplier.getAddress(), "assert2 failed" ) ;
        assertEquals("0581", supplier.getPhone(), "assert3 failed" ) ;
        assertEquals("1" , supplier.getNumber() , "assert4 failed" ) ;
        assertEquals(paymentTerms1.getPaymentMethod() , supplier.getPaymentTerms().getPaymentMethod(), "assert5 failed" ) ;
        assertEquals(paymentTerms1.getPaymentDeliveryTime() , supplier.getPaymentTerms().getPaymentDeliveryTime(), "assert6 failed" ) ;
        assertEquals(paymentTerms1.getBankDetails() , supplier.getPaymentTerms().getBankDetails(), "assert7 failed" ) ;
        assertEquals(SupplierType.nonConstant , supplier.getType(), "assert8 failed" ) ;
        assertEquals(days.toString() , supplier.getDeliveryDays().toString(), "assert9 failed" ) ;
        assertEquals(2 , supplier.getDeliveryDays().size(), "assert10 failed" ) ;
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testContactPeopleInsertionAndRetrievalAndDelete() {
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        ArrayList<DeliveryDays> days = new ArrayList<>() ;
        supplierDAO.addSupplier(new SupplierDTO("supplier2" , "address2" , "0582" , "2" , paymentTerms1 , SupplierType.nonConstant , days)) ;
        supplierDAO.addSupplier(new SupplierDTO("supplier3" , "address3" , "0583" , "3" , paymentTerms1 , SupplierType.nonConstant , days)) ;
        for(int i = 0 ; i < 10 ; i ++){
            ContactPersonDTO c = new ContactPersonDTO("name" + (i+1) , "phone" + (i+1) , "2") ;
            contactPersonDAO.addContactPerson(c) ;
            c.setSuppNumber("3");
            contactPersonDAO.addContactPerson(c) ;
        }
        contactPersonDAO.deleteContactPerson(new ContactPersonDTO("name3" , "phone3" , "2")) ;
        contactPersonDAO.deleteContactPerson(new ContactPersonDTO("name6" , "phone6" , "2")) ;
        ArrayList<ContactPersonDTO> result1 = contactPersonDAO.getContacts("3") ;
        result1.sort(new Comparator<ContactPersonDTO>() {
            @Override
            public int compare(ContactPersonDTO o1, ContactPersonDTO o2) {
                int r1 = 0 ;
                int r2 = 0 ;
                for(int i = 0 ; i < o1.getName().length() ; i ++){
                    r1 += o1.getName().charAt(i) ;
                }
                for(int i = 0 ; i < o2.getName().length() ; i ++){
                    r2 += o2.getName().charAt(i) ;
                }
                return r1 - r2;
            }
        });
        assertEquals(10 , result1.size(), "no contacts deleted for this supplier" ) ;
        for(int i = 0 ; i < 10 ; i ++){
            assertEquals("name" + (i+1) , result1.get(i).getName(), "not expected name" ) ;
            assertEquals("phone" + (i+1) , result1.get(i).getPhone(), "not expected phone" ) ;
            assertEquals("3" , result1.get(i).getSuppNumber(), "not expected suppNumber" ) ;
        }

        ArrayList<ContactPersonDTO> result2 = contactPersonDAO.getContacts("2") ;
        result2.sort(new Comparator<ContactPersonDTO>() {
            @Override
            public int compare(ContactPersonDTO o1, ContactPersonDTO o2) {
                int r1 = 0 ;
                int r2 = 0 ;
                for(int i = 0 ; i < o1.getName().length() ; i ++){
                    r1 += o1.getName().charAt(i) ;
                }
                for(int i = 0 ; i < o2.getName().length() ; i ++){
                    r2 += o2.getName().charAt(i) ;
                }
                return r1 - r2;
            }
        });
        assertEquals(8 , result2.size() , "2 contacts deleted");
        int count = 0 ;
        for(int i = 0 ; i < 10 ; i ++) {
            if (i == 2 || i == 5)
                count++;
            else {
                assertEquals("name" + (i + 1), result2.get(i - count).getName(), "not expected name");
                assertEquals("phone" + (i + 1), result2.get(i - count).getPhone(), "not expected phone");
                assertEquals("2", result2.get(i - count).getSuppNumber(), "not expected suppNumber");
            }
        }
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testContractsInsertionAndRetrievalAndDelete() {
        Contract contract = new Contract((Supplier) null) ;
        ContractDTO result = contractDAO.addNewContract("2") ;
        contract.setId(result.getId());
        contract.setSupplierNumber(result.getSupplierNumber());

        ArrayList<String> toCompareWith = new ArrayList<>() ;
        for(int i = 0 ; i < 10 ; i ++){
            Item item = new Item("code" + (i + 1), "description" + (i + 1)) ;
            toCompareWith.add(item.getCode()) ;
            AmountSpecifier amountSpecifier = new AmountSpecifier(10*(i+1) , 0.1*i) ;
            contract.addTerm(new ContractTerm(item , (i+1)*10 , amountSpecifier)) ;
        }
        contractRepository.deleteContractTerm(contract.getId() , "code3") ;
        contractRepository.deleteContractTerm(contract.getId() , "code6") ;

        assertEquals(8 , contract.getContractTerms().size() , "size should be 10");
        int count = 0 ;
        for(int i = 0 ; i < 10 ; i ++){
            if(i == 2 || i == 5)
                count ++ ;
            else {
                ContractTerm term = contract.getContractTerms().get(i - count);
                assertTrue(toCompareWith.contains(term.getItem().getCode()));
            }
        }
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testOrdersRetrievalAndInsert() {
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        ArrayList<DeliveryDays> days = new ArrayList<>() ;
        supplierDAO.addSupplier(new SupplierDTO("supplier2" , "address2" , "0582" , "2" , paymentTerms1 , SupplierType.nonConstant , days)) ;

        Contract contract = new Contract((Supplier) null) ;
        ContractDTO result = contractDAO.addNewContract("2") ;
        contract.setId(result.getId());
        contract.setSupplierNumber(result.getSupplierNumber());

        for(int i = 0 ; i < 10 ; i ++){
            Item item = new Item("code" + (i + 1), "description" + (i + 1)) ;
            AmountSpecifier amountSpecifier = new AmountSpecifier(0 , 0.1) ;
            contract.addTerm(new ContractTerm(item , (i+1)*10 , amountSpecifier)) ;
        }

        Date date = new Date(2025 , 6 , 10) ;
        OrderDTO orderDTO = new OrderDTO("2" , "address" , date , "phone" , OrderStatus.submitted) ;
        Order order = ordersRepository.createOrder(orderDTO) ;

        ArrayList<String> toCheck = new ArrayList<>() ;
        for(int i = 0 ; i < 10 ; i ++){
            OrderItemDTO orderItemDTO = new OrderItemDTO("code" + (i + 1) , (i+1) * 10 , -1 , order.getId()) ;
            toCheck.add("code" + (i + 1)) ;
            ordersRepository.addOrderItem(orderItemDTO) ;
        }

        for(OrderItem orderItem : order.getItems()){
            assertTrue(toCheck.contains(orderItem.getItemCode()));
            assertNotEquals(-1 , orderItem.getTotalPrice());
        }
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testOrdersDeletionAndInsert() {
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        ArrayList<DeliveryDays> days = new ArrayList<>() ;
        supplierDAO.addSupplier(new SupplierDTO("supplier2" , "address2" , "0582" , "2" , paymentTerms1 , SupplierType.nonConstant , days)) ;

        Contract contract = new Contract((Supplier) null) ;
        ContractDTO result = contractDAO.addNewContract("2") ;
        contract.setId(result.getId());
        contract.setSupplierNumber(result.getSupplierNumber());

        for(int i = 0 ; i < 10 ; i ++){
            Item item = new Item("code" + (i + 1), "description" + (i + 1)) ;
            AmountSpecifier amountSpecifier = new AmountSpecifier(0 , 0.1) ;
            contract.addTerm(new ContractTerm(item , (i+1)*10 , amountSpecifier)) ;
        }

        Date date = new Date(2025 , 6 , 10) ;
        OrderDTO orderDTO = new OrderDTO("2" , "address" , date , "phone" , OrderStatus.submitted) ;
        Order order = ordersRepository.createOrder(orderDTO) ;

        ordersRepository.deleteOrder(order.getId()) ;
        Order r = ordersRepository.getOrderById(order.getId()) ;
        assertNull(r);
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void testContractPersistance(){
        PaymentTermsDTO paymentTerms1 = new PaymentTermsDTO(PaymentMethod.cash , PaymentDeliveryTime.afterOrder , "bank1") ;
        ArrayList<DeliveryDays> days = new ArrayList<>() ;
        supplierDAO.addSupplier(new SupplierDTO("supplier2" , "address2" , "0582" , "2" , paymentTerms1 , SupplierType.nonConstant , days)) ;

        Contract contract = new Contract((Supplier) null) ;
        ContractDTO result = contractDAO.addNewContract("2") ;
        contract.setId(result.getId());
        contract.setSupplierNumber(result.getSupplierNumber());

        for(int i = 0 ; i < 10 ; i ++){
            Item item = new Item("code" + (i + 1), "description" + (i + 1)) ;
            AmountSpecifier amountSpecifier = new AmountSpecifier(0 , 0.1) ;
            contract.addTerm(new ContractTerm(item , (i+1)*10 , amountSpecifier)) ;
        }
        contractRepository.deleteContractTerm(contract.getId() , "code3") ;
        contractRepository.deleteContractTerm(contract.getId() , "code6") ;

        assertNotEquals(1000 , contract.getTotalPriceOfItems("code1" , 1)) ;

        ItemDTO item = new ItemDTO("code1", "description1") ;
        AmountSpecifierDTO amountSpecifier = new AmountSpecifierDTO(100 , 0.5) ;
        contractTermDAO.updateContractTerm(new ContractTermDTO(item , amountSpecifier , 1000 , contract.getId())) ;
        assertEquals(1000 , contract.getTotalPriceOfItems("code1" , 1)) ;
    }


}
