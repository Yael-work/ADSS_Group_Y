package Tests;

import Models.DBModels.transport.*;
import Models.DTOs.StockItem;
import Auxiliary.Enums.Models.TransportStatusEnum;
import Auxiliary.Enums.Models.SiteTypeEnum;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Test {

    @org.junit.jupiter.api.Test
    void testSiteConstructorAndGetters() {
        Site site = new Site(1, SiteTypeEnum.BRANCH, 1,"123 Main St", "+123456789", "John Doe",
                0.0);
        assertEquals(1, site.getId());
        assertEquals("123 Main St", site.getSiteAddress());
        assertEquals("John Doe", site.getContactName());
        assertEquals("+123456789", site.getContactPhone());
    }

    @org.junit.jupiter.api.Test
    void testSiteSetters() {
        Site site = new Site(2, SiteTypeEnum.SUPPLIER, 1, "456 Elm St", "+987654321", "Jane Doe", 0.0);
        site.setId(2);
        site.setSiteAddress("456 Elm St");
        site.setContactName("Jane Doe");
        site.setContactPhone("+987654321");
        assertEquals(2, site.getId());
        assertEquals("456 Elm St", site.getSiteAddress());
        assertEquals("Jane Doe", site.getContactName());
        assertEquals("+987654321", site.getContactPhone());
    }

    @org.junit.jupiter.api.Test
    void testTransportConstructorAndGetters() {
        Transport transport = new Transport(1, LocalDateTime.of(2023, 12, 25, 10, 0),TransportStatusEnum.PENDING,1,1,
                List.of(1, 2), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        assertEquals(1, transport.getId());
        assertEquals(LocalDateTime.of(2023, 12, 25, 10, 0), transport.getPlannedDateTime());
        assertEquals(List.of(1, 2), transport.getTransportRouteIds());
    }

    @org.junit.jupiter.api.Test
    void testTransportSetters() {
          Transport transport = new Transport(1, LocalDateTime.of(2023, 12, 25, 10, 0),TransportStatusEnum.PENDING,1,1,
                List.of(1, 2), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        transport.setId(2);
        transport.setPlannedDateTime(LocalDateTime.of(2023, 12, 31, 15, 0));
        transport.setTransportRouteIds(List.of(3, 4));
        assertEquals(2, transport.getId());
        assertEquals(LocalDateTime.of(2023, 12, 31, 15, 0), transport.getPlannedDateTime());
        assertEquals(List.of(3, 4), transport.getTransportRouteIds());
    }

    @org.junit.jupiter.api.Test
    void testBranchConstructorAndGetters() {
        Branch branch = new Branch(1, "Branch A", "123 Main St", "+123456789");
        assertEquals(1, branch.getId());
        assertEquals("Branch A", branch.getSiteName());
    }

    @org.junit.jupiter.api.Test
    void testBranchSetters() {
        Branch branch = new Branch(1, "Branch B", "123 Main St", "+123456789");
        branch.setId(2);
        branch.setName("Branch B");
        assertEquals(2, branch.getId());
        assertEquals("Branch B", branch.getSiteName());
    }

    @org.junit.jupiter.api.Test
    void testStockItemConstructorAndGetters() {
        StockItem item = new StockItem(1, "Item A",1,10, "");
        assertEquals(1, item.getBranchId());
        assertEquals("Item A", item.getItemName());
        assertEquals(10.0, item.getAmount());
    }

    @org.junit.jupiter.api.Test
    void testStockItemSetters() {
        StockItem item = new StockItem(1, "Item A",1,10, "");
        item.setBranchId(2);
        item.setItemName("Item B");
        item.setAmount(5.0);
        assertEquals(2, item.getBranchId());
        assertEquals("Item B", item.getItemName());
        assertEquals(5.0, item.getAmount());
    }

    @org.junit.jupiter.api.Test
    void testDriverConstructorAndGetters() {
        Driver driver = new Driver(1, "Driver", "A", "1", "+123456789", "D");
        assertEquals(1, driver.getId());
        assertEquals("Driver", driver.getFirstName());
        assertEquals("D", driver.getLicenseType());
    }

    @org.junit.jupiter.api.Test
    void testDriverSetters() {
        Driver driver = new Driver(1, "Driver", "B", "1", "+123456789", "C");
        driver.setId(2);
        driver.setName("Driver B");
        driver.setLicenseType("License B");
        assertEquals(2, driver.getId());
        assertEquals("B", driver.getLastName());
        assertEquals("License B", driver.getLicenseType());
    }
}