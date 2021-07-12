package reminderstest;

import exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.*;
import reminders.Reminders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemindersTest {

    Reminders remindersTestService = new Reminders();
    List<String> tasks = new ArrayList<>();
    List<String> expected = new ArrayList<>();

    @Test
    @DisplayName("Obtener tareas, categoria sin tareas")
    void noTasks() {
        remindersTestService.addTask("Casa", null);

        tasks = remindersTestService.tasks();
        assertLinesMatch(expected, tasks);
    }

    @Nested
    @DisplayName("Tests con llenado de tareas previo")
    class withTasksFill {

        @BeforeEach
        void fillTasks() {
            remindersTestService.addTask("ITBA", "Consultar Intercambio");
            remindersTestService.addTask("ITBA", "Entregar el TPE");
            remindersTestService.addTask("Casa", "Llamar Electricista");
            remindersTestService.addTask("Casa", "Cambiar filtro");
            remindersTestService.addTask("Casa", "Pagar Expensas");
        }

        @AfterEach
        void clearLists() {
            tasks.clear();
            expected.clear();
        }

        @Test
        @DisplayName("Obtener tareas ordenadas segun categoria existente")
        void categoryExists() throws CategoryNotFoundException {

            tasks = remindersTestService.tasks("Casa");

            expected.add("Cambiar filtro");
            expected.add("Llamar Electricista");
            expected.add("Pagar Expensas");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Obtener tareas, categoria inexistente")
        void categoryNotExists() {

            Throwable exception = assertThrows(CategoryNotFoundException.class, () -> {
                tasks = remindersTestService.tasks("Edificio");
            });
            assertEquals(exception.getClass(), CategoryNotFoundException.class);
        }

        @Test
        @DisplayName("Obtener tareas, categoria sin tareas")
        void categoryWithoutTasks() throws CategoryNotFoundException {

            remindersTestService.addTask("Edificio", null);
            tasks = remindersTestService.tasks("Edificio");
            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Obtener todas las tareas ordenadas por categoria y tarea")
        void allTasks() {

            tasks = remindersTestService.tasks();

            expected.add("Cambiar filtro");
            expected.add("Llamar Electricista");
            expected.add("Pagar Expensas");
            expected.add("Consultar Intercambio");
            expected.add("Entregar el TPE");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Completar tarea")
        void completeTask() throws CategoryNotFoundException {

            remindersTestService.done("Casa", "Pagar Expensas");
            tasks = remindersTestService.pendingTasks("Casa");

            expected.add("Cambiar filtro");
            expected.add("Llamar Electricista");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Completar tarea, categoria inexistente")
        void taskDoneCategoryNotExist() {

            Throwable exception = assertThrows(CategoryNotFoundException.class, () -> {
                remindersTestService.done("Oficina", "Limpiar");
            });
            assertEquals(exception.getClass(), CategoryNotFoundException.class);
        }

        @Test
        @DisplayName("Completar tarea, tarea no pertenece a categoria")
        void taskNotBelongsCategory() throws CategoryNotFoundException {

            remindersTestService.done("Casa", "Limpiar");

            tasks = remindersTestService.pendingTasks("Casa");

            expected.add("Cambiar filtro");
            expected.add("Llamar Electricista");
            expected.add("Pagar Expensas");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Sin cambios al intentar completar tarea ya completada")
        void taskAlreadyDone() throws CategoryNotFoundException {

            remindersTestService.done("Casa", "Cambiar filtro");

            tasks = remindersTestService.pendingTasks("Casa");

            expected.add("Llamar Electricista");
            expected.add("Pagar Expensas");

            assertLinesMatch(expected, tasks);

            remindersTestService.done("Casa", "Cambiar filtro");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Obtener tareas pendientes, categoria existente")
        void getAllPendingTasksByCategory() throws CategoryNotFoundException {

            tasks = remindersTestService.pendingTasks("Casa");

            expected.add("Cambiar filtro");
            expected.add("Llamar Electricista");
            expected.add("Pagar Expensas");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Completa todas las tareas pendientes de una categoria")
        void completeAllPendingTasksByCategory() throws CategoryNotFoundException {

            remindersTestService.done("Casa");
            tasks = remindersTestService.pendingTasks("Casa");

            assertLinesMatch(expected, tasks);
        }

        @Test
        @DisplayName("Completar tareas, categoria inexistente")
        void completeTasksCategoryNotExists() {

            Throwable exception = assertThrows(CategoryNotFoundException.class, () -> {
                remindersTestService.done("Universidad");
            });
            assertEquals(exception.getClass(), CategoryNotFoundException.class);
        }
    }
}
