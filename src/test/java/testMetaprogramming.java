import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class testMetaprogramming {

    @Test
    public void probandoAuthDecorator1(){
        Service service = new Service();
        AuthDecorator decorator = new AuthDecorator(service);
        decorator.Execute("Hola");
    }

    @Test
    public void probandoAuthDecorator2() throws Exception {
        Service service = new Service();
        AuthDecorator decorator = new AuthDecorator(service);
        Role role = Role.admin;
        User user = new User(role);
        decorator.Execute("Hola", user);
    }

    @Test
    public void probandoAuthDecorator3() throws Exception {
        Service service = new Service();
        AuthDecorator decorator = new AuthDecorator(service);
        Role role = Role.anonymous;
        User user = new User(role);
        decorator.Execute("Hola", user);
    }

    @Test
    public void probandoLoggingDecorator1(){
        Service service = new Service();
        LoggingDecorator decorator = new LoggingDecorator(service);
        decorator.Execute("Hola soy Logging Decorator");
    }

    @Test
    public void probandoLoggingDecorator2() throws Exception {
        Service service = new Service();
        LoggingDecorator decorator = new LoggingDecorator(service);
        Role role = Role.admin;
        User user = new User(role);
        decorator.Execute("Hola soy Logging Decorator", user);
    }

    @Test
    public void probandoLoggingDecorator3() throws Exception {
        Service service = new Service();
        LoggingDecorator decorator = new LoggingDecorator(service);
        Role role = Role.anonymous;
        User user = new User(role);
        decorator.Execute("Hola soy Logging Decorator", user);
    }

    @Test
    public void metaprogramacion() throws Exception {
        AnotherService anotherService = new AnotherService();
        Role role = Role.admin;
        User user = new User(role);
        AnotherServiceProxy service = Factory.CreateAnotherServiceProxy(anotherService);
        service.execute("Hello user", user);
    }

    @Test
    public void metaprogramacion2() throws Exception {
        AnotherService anotherService = new AnotherService();
        AnotherServiceProxy service = Factory.CreateAnotherServiceProxy(anotherService);
        service.execute("Hello user");
    }
}
