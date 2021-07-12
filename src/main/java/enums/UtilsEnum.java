package enums;

public enum UtilsEnum {

    PENDING_TASK("Pendiente"),
    COMPLETED_TASK("Completada"),
    CATEGORY_NOT_EXISTS("La categoria no existe");

    private final String name;

    UtilsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
