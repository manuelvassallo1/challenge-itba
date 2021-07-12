package reminders;

import exceptions.CategoryNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static enums.UtilsEnum.*;

public class Reminders {

    Map<String, Map<String, String>> taskMap = new TreeMap<>();

    public void addTask(String category, String task) {

        if (category != null && task != null) {
            taskMap.computeIfAbsent(category, v -> new TreeMap<>()).putIfAbsent(task, PENDING_TASK.getName());
        } else if (category != null) {
            taskMap.computeIfAbsent(category, v -> new TreeMap<>());
        }
    }

    public List<String> tasks(String category) throws CategoryNotFoundException {
        if (category != null && taskMap.containsKey(category)) {
            return new ArrayList<>(taskMap.get(category).keySet());
        } else {
            throw new CategoryNotFoundException(CATEGORY_NOT_EXISTS.getName());
        }
    }

    public List<String> tasks() {
        List<String> l = new ArrayList<>();
        taskMap.keySet().forEach(s -> {
            try {
                l.addAll(tasks(s));
            } catch (CategoryNotFoundException e) {
                e.printStackTrace();
            }
        });

        return l;
    }

    public void done(String category, String task) throws CategoryNotFoundException {
        if (category != null && taskMap.containsKey(category)) {
            if (task != null && taskMap.get(category).containsKey(task)
                    && !COMPLETED_TASK.getName().equals(taskMap.get(category).get(task))) {
                taskMap.get(category).put(task, COMPLETED_TASK.getName());
            }
        } else {
            throw new CategoryNotFoundException(CATEGORY_NOT_EXISTS.getName());
        }
    }

    public List<String> pendingTasks(String category) throws CategoryNotFoundException {
        if (category != null && taskMap.containsKey(category)) {
            return taskMap.get(category)
                    .entrySet()
                    .stream()
                    .filter(v -> v.getValue().equals(PENDING_TASK.getName()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } else {
            throw new CategoryNotFoundException(CATEGORY_NOT_EXISTS.getName());
        }
    }

    public void done(String category) throws CategoryNotFoundException {
        if (category != null && taskMap.containsKey(category)) {
            taskMap.get(category).replaceAll((task, status) -> COMPLETED_TASK.getName());
        } else {
            throw new CategoryNotFoundException(CATEGORY_NOT_EXISTS.getName());
        }
    }
}
