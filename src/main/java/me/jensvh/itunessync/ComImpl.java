package me.jensvh.itunessync;

public class ComImpl {
    
    public static void initialize() {
        System.setProperty("jacob.dll.path", System.getProperty("user.dir") + "/lib/jacob-1.20-x86.dll");
    }

}
