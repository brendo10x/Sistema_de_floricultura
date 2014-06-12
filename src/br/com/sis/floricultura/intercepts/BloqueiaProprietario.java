package br.com.sis.floricultura.intercepts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//a anota��o vai ficar dispon�vel em tempo de execucao
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // anota��o para m�todos
public @interface BloqueiaProprietario {

	 
}
