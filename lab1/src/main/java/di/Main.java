package di;

import di.component.Application;
import di.config.Context;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        Application application = (Application) context.getComponent(Application.class);
        application.run();
    }
}