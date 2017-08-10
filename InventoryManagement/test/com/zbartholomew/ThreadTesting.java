package com.zbartholomew;

/**
 * <h1> Thread testing {@link ProductInventoryManager} class. </h1>
 * <p> Run this test multiple times and verify outputs. </p>
 * <p> This test should demonstrate that no picker can pick an
 * out of stock product</p>
 *
 * @author Zach Bartholomew
 */
public class ThreadTesting {

    public static void main(String[] args) {

        Location location1 = new Location("First location");
        new Product("123", 12, location1);
        new Product("432", 100, location1);
        new Product("567", 200, location1);
        new Product("789", 300, location1);
        new Product("987", 1000, location1);
        new Product("765", 3100, location1);

        Thread t1 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t1PickingResult = manager.pickProduct("123", 13);
            System.out.println("t1 message " + "\n" + t1PickingResult.getDetailMessage() + "\n");
        });

        Thread t2 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t2PickingResult = manager.pickProduct("123", 7);
            System.out.println("t2 message " + "\n" + t2PickingResult.getDetailMessage() + "\n");
        });

        Thread t3 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            RestockingResult t3RestockingResult = manager.restockProduct("123", 5);
            System.out.println("t3 message " + "\n" + t3RestockingResult.getDetailMessage() + "\n");
        });

        Thread t4 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t4PickingResult = manager.pickProduct("123", 6);
            System.out.println("t4 message " + "\n" + t4PickingResult.getDetailMessage() + "\n");
        });

        Thread t5 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            RestockingResult t5RestockingResult = manager.restockProduct("123", Integer.MAX_VALUE);
            System.out.println("t5 message " + "\n" + t5RestockingResult.getDetailMessage() + "\n");
        });

        Thread t5a = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            RestockingResult t5aRestockingResult = manager.restockProduct("123", -1);
            System.out.println("t5a message " + "\n" + t5aRestockingResult.getDetailMessage() + "\n");
        });

        Thread t6 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t6PickingResult = manager.pickProduct("432", 8);
            System.out.println("t6 message " + "\n" + t6PickingResult.getDetailMessage() + "\n");
        });

        Thread t7 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t7PickingResult = manager.pickProduct("567", 18);
            System.out.println("t7 message " + "\n" + t7PickingResult.getDetailMessage() + "\n");
        });

        Thread t8 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t8PickingResult = manager.pickProduct("789", 45);
            System.out.println("t8 message " + "\n" + t8PickingResult.getDetailMessage() + "\n");
        });

        Thread t9 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t9PickingResult = manager.pickProduct("987", 15);
            System.out.println("t9 message " + "\n" + t9PickingResult.getDetailMessage() + "\n");
        });

        Thread t10 = new Thread(() -> {
            ProductInventoryManager manager = new ProductInventoryManager();
            PickingResult t10PickingResult = manager.pickProduct("765", 23);
            System.out.println("t10 message " + "\n" + t10PickingResult.getDetailMessage() + "\n");
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t5a.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
    }
}

