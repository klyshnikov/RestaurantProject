package ru.hse.restaurant.project.command;

import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class Kitchen implements OrderReceiver {
    static List<Thread> kitchenWorkersThreads=new ArrayList<Thread>();

    public Kitchen() {}

    @Override
    public synchronized void makeDish(Dish dish, OrderDecorator orderDecorator) throws InterruptedException {
        kitchenWorkersThreads.add(new Thread(new KitchenWorker(dish, orderDecorator)));
        kitchenWorkersThreads.get(kitchenWorkersThreads.size()-1).start();
    }

    @Override
    public void stopCooking() {
        for (Thread kitchenWorkersThread : kitchenWorkersThreads) {
            kitchenWorkersThread.interrupt();
        }
    }
}
