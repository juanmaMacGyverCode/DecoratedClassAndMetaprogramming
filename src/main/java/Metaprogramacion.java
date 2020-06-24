import java.lang.reflect.*;
import java.util.jar.Attributes;


enum Role {
    admin,
    user,
    anonymous
}

class User {

    private Role role;

    public User(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

class Service {

    public void Execute(String someArgument, User user) throws Exception {
        System.out.println("------------ EXECUTE 1");
        // Alguna lógica
    }

    public void Execute(String someArgument) {
        System.out.println("------------ EXECUTE 2");
        // Alguna lógica
    }
}

class AuthDecorator extends Service {

    private Service decorated;

    public AuthDecorator(Service decorated) {
        this.decorated = decorated;
    }

    @Override
    public void Execute(String someArgument) {
        System.out.println("AUTH");
        decorated.Execute(someArgument);
        System.out.println("END");
    }

    @Override
    public void Execute(String someArgument, User user) throws Exception {
        if (user.getRole() == Role.admin) {
            System.out.println("Checking Auth for role: " + user.getRole());
            decorated.Execute(someArgument, user);
            System.out.println("Final Auth");
        } else {
            throw new Exception();
        }
    }
}

class LoggingDecorator extends Service {

    private Service decorated;

    public LoggingDecorator(Service decorated) {
        this.decorated = decorated;
    }

    @Override
    public void Execute(String someArgument){
        System.out.println("Logging call to Execute with: " + someArgument);
        decorated.Execute(someArgument);
        System.out.println("Call finished");
    }

    @Override
    public void Execute(String someArgument, User user) throws Exception {
        if (user.getRole() == Role.admin) {
            System.out.println("Logging call to Execute with: " + someArgument);
            decorated.Execute(someArgument, user);
            System.out.println("Call finished");
        } else {
            throw new Exception();
        }
    }
}

// AOP Aspect Oriented Programming
class AnotherService {

    public void Execute(String arg1) {
        System.out.println("--EXECUTE AnotherService");
    }

    public void Execute(String arg1, User user) {
        System.out.println("--EXECUTE AnotherService with user");
    }

}

class WriteActionAttribute extends Attributes {
}

class AnotherServiceProxy<T> {
    private T target;

    public AnotherServiceProxy(T target) {
        this.target = target;
    }

    public void execute(Object ... args) throws Exception {

        String type = this.target.getClass().getTypeName();
        Method[] methods = this.target.getClass().getMethods();
        for (Method method: methods) {
            Parameter[] parameters = method.getParameters();
            if (args.length == parameters.length && method.getName().equalsIgnoreCase(this.getClass().getMethod("execute", Object[].class).getName())) {
                System.out.println("------------");
                for (Parameter parameter : parameters) {
                    if ((parameter.getType() + "").equals("class java.lang.String")) {
                        System.out.println("LOGGING: " + args[0]);
                    }

                    if ((parameter.getType() + "").equals("class User")) {
                        System.out.println("CHECKING AUTH " + args[1]);

                        if (args.length == 2 && (args[1].getClass().toString()).equals("class User")) {
                            System.out.println("COMPROBANDO EL ROL DEL USUARIO");
                            if (((User) args[1]).getRole() !=  Role.admin){
                                System.out.println("Si no eres admin, eres feo");
                                throw new Exception();
                            }
                        }

                    }
                }
                System.out.println("------------");
                method.invoke(this.target, args);
            }
        }
    }

    public Role getRole() {
        return Role.admin;
    }
}

/*class String extends Attributes {
}*/

class Factory {
    public static <T> AnotherServiceProxy CreateAnotherServiceProxy(AnotherService anotherService) {
        return new AnotherServiceProxy<AnotherService>(anotherService);
    }
}


