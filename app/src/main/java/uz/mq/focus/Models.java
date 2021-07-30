package uz.mq.focus;

public class Models {

    public static class TaskItem{
        String key;
        String title;
        String date;
        int priority;
        int category;
        KeyValueItem project;

        public TaskItem(){

        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public KeyValueItem getProject() {
            return project;
        }

        public void setProject(KeyValueItem project) {
            this.project = project;
        }

        public TaskItem(String key, String title, String date, int priority, int category, KeyValueItem project) {
            this.key = key;
            this.title = title;
            this.date = date;
            this.priority = priority;
            this.category = category;
            this.project = project;
        }
    }

    public static class KeyValueItem{
        String key;
        Object value;


        public KeyValueItem(){

        }
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public KeyValueItem(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class ProjectsItem{
        String key;
        String name;
        String description;
        String logo;
        int potentialIncome;
        int hours;

        public ProjectsItem(){

        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setPotentialIncome(int potentialIncome) {
            this.potentialIncome = potentialIncome;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getLogo() {
            return logo;
        }

        public int getPotentialIncome() {
            return potentialIncome;
        }

        public int getHours() {
            return hours;
        }

        public ProjectsItem(String key, String name, String description, String logo, int potentialIncome, int hours) {
            this.key = key;
            this.name = name;
            this.description = description;
            this.logo = logo;
            this.potentialIncome = potentialIncome;
            this.hours = hours;
        }
    }

}
