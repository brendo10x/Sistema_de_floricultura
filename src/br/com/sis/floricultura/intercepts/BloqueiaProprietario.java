package br.com.sis.floricultura.intercepts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//a anotação vai ficar disponível em tempo de execucao
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // anotação para métodos
public @interface BloqueiaProprietario {

	 
}
