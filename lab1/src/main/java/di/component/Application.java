package di.component;

import di.annotation.Component;
import di.model.Fruit;
import di.model.FruitType;
import di.model.Store;

@Component
public class Application {

    //@Inject
    private StoreService service;

    public Application(StoreService service) {
        this.service = service;
    }

    public void run() {

        service.add("I");
        service.add("II");

        Store storeI = service.findByName("I");
        service.addFruit(storeI, new Fruit("Яблоко", FruitType.APPLE), 1000);
        service.addFruit(storeI, new Fruit("Бананы", FruitType.BANANA), 2000);

        Store storeII = service.findByName("II");
        service.addFruit(storeII, new Fruit("Яблоко", FruitType.APPLE), 3000);
        service.addFruit(storeII, new Fruit("Апельсины", FruitType.ORANGE), 2000);

//        service.getAll().forEach(System.out::println);
    }

}