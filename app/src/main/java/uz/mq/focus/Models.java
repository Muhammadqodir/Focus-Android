package uz.mq.focus;

public class Models {

    public static class TaskItem{
        String key;
        String title;
        String date;
        int priority;
        int category;
        String project;

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

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public TaskItem(String key, String title, String date, int priority, int category, String project) {
            this.key = key;
            this.title = title;
            this.date = date;
            this.priority = priority;
            this.category = category;
            this.project = project;
        }
    }

}
