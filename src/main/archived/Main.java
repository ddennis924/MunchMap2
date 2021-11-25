package archived;

import archived.MunchMapApp;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new MunchMapApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load MunchApp, save file not found");
        }
    }
}
