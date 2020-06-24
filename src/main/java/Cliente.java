abstract class Componente{
    abstract public void operacion();
}



class ComponenteConcreto extends Componente{
    public void operacion(){
        System.out.println("ComponenteConcreto.operacion()");
    }
}

abstract class Decorador extends Componente{
    private Componente componente;

    public Decorador(Componente componente){
        this.componente = componente;
    }

    public void operacion(){
        componente.operacion();
    }
}

class DecoradorConcretoA extends Decorador{
    private String propiedadAñadida;

    public DecoradorConcretoA(Componente componente){
        super(componente);
    }

    public void operacion(){
        super.operacion();
        this.propiedadAñadida = "Nueva propiedad";
        System.out.println("DecoradorConcretoA.operacion()");
    }
}

class DecoradorConcretoB extends Decorador{
    public DecoradorConcretoB(Componente componente){
        super(componente);
    }

    public void operacion(){
        super.operacion();
        comportamientoAñadido();
        System.out.println("DecoradorConcretoB.operacion()");
    }

    public void comportamientoAñadido(){
        System.out.println("Comportamiento B añadido");
    }
}

public class Cliente {
    public static void main(String[] args){
        ComponenteConcreto c = new ComponenteConcreto();
        DecoradorConcretoA d1 = new DecoradorConcretoA(c);
        DecoradorConcretoB d2 = new DecoradorConcretoB(d1);
        d2.operacion();//output: "ComponenteConcreto.operacion()\n DecoradorConcretoA.operacion()\n Comportamiento B añadido\n DecoradorConcretoB.operacion()"
    }
}
