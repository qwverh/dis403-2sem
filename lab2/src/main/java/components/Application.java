package components;


import model.Category;
import model.Order;
import model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("App")
public class Application {
    private final MarketService service;


    public Application(MarketService service) {
        this.service = service;
    }


    public void run() {
        try {
            service.doOrder(new Order(new Product("Компьютер", "0001", Category.PC, BigDecimal.valueOf(50000)),
                    10, "CLient 1"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
