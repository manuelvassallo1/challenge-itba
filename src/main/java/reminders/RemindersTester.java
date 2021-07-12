package reminders;

import exceptions.CategoryNotFoundException;

public class RemindersTester {
    public static void main(String[] args) throws CategoryNotFoundException {
        Reminders reminders = new Reminders();
        reminders.addTask("ITBA", "Consultar Intercambio");
        reminders.addTask("ITBA", "Entregar el TPE");
        reminders.addTask("Casa", "Llamar Electricista");
        reminders.addTask("Casa", "Cambiar filtro");
        reminders.addTask("Casa", "Pagar Expensas");
        System.out.println("----------");
/**
 * Obtiene todas las tareas (tanto pendientes como completadas)
 * de la categoría Casa ordenadas alfabéticamente
 * por nombre de la tarea.
 * Si la categoría no existe se lanza la excepción CategoryNotFoundException.
 * Si no hay tareas pertenecientes a la categoría devuelve una colección
 vacía.
 */
        for (String task : reminders.tasks("Casa")) {
            System.out.println(task);
        }
        System.out.println("----------");
///**
// * Obtiene todas las tareas (tanto pendientes como completadas)
// * ordenadas alfabéticamente por categoría y luego alfabéticamente
// * por nombre de la tarea.
// * Si no hay tareas devuelve una colección vacía.
// */
        for (String task : reminders.tasks()) {
            System.out.println(task);
        }
        System.out.println("----------");
///**
// * Marca como completada una tarea pendiente.
// * Si la categoría no existe se lanza la excepción CategoryNotFoundException.
// * Si la tarea no pertenece a la categoría no hace nada.
// * Si la tarea pertenece a la categoría y ya está completada no hace nada.
// */
        reminders.done("Casa", "Pagar Expensas");
///**
// * Obtiene todas las tareas pendientes de una categoría
// * ordenadas alfabéticamente por nombre de la tarea.
// */
        for (String task : reminders.pendingTasks("Casa")) {
            System.out.println(task);
        }
        System.out.println("----------");
///**
// * Marca como completadas a todas las tareas pendientes de una categoría.
// * Si la categoría no existe se lanza la excepción CategoryNotFoundException.
// */
        reminders.done("ITBA");
        System.out.println(reminders.pendingTasks("ITBA").isEmpty()); // true
        System.out.println("----------");
        try {
            reminders.tasks("Trabajo");
        } catch (CategoryNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("----------");
    }
}
