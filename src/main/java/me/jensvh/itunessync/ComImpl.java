package me.jensvh.itunessync;

public class ComImpl {
    
    public static void initialize() {
        System.out.println(System.getProperty("user.dir"));
        System.setProperty("jacob.dll.path", System.getProperty("user.dir") + "/lib/jacob-1.20-x64.dll");
    }

}
