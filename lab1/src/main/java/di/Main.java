package di;

import di.component.Application;
import di.config.Context;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        Application application = (Application) context.getBean(Application.class);
        application.run();
    }
}